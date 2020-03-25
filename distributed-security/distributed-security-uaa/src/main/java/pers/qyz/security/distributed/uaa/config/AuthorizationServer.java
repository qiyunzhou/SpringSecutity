package pers.qyz.security.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *  1.客户端详情相关配置
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource){
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //使用内存存储
        /*clients.inMemory()
                .withClient("c1")
                .secret(new BCryptPasswordEncoder().encode("secret"))   //客户端密钥
                .resourceIds("res1")    //资源列表
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                // 该client允许的授权类型 authorization_code, password, refresh_token, implicit, client_credentials
                .scopes("all")// 允许的授权范围
                .autoApprove(false) //false 跳转到授权页面
                //加上验证回调地址
                .redirectUris("http://www.baidu.com");*/

        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 2.配置令牌服务(token services)
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenService(){
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);  // 客户端信息
        service.setSupportRefreshToken(true);   //是否刷新令牌
        service.setTokenStore(tokenStore);      //令牌存储策略

        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

    /**
     * 3.配置令牌（token）的访问端点
     */
   /* @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        //设置授权码模式的授权码如何存取，暂时采用内存方式
        return new InMemoryAuthorizationCodeServices();
    }*/
   @Bean
   public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
       //设置授权码模式的授权码如何存取
       return new JdbcAuthorizationCodeServices(dataSource);
   }

    //令牌访问端点
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)   //密码模式需要
                .authorizationCodeServices(authorizationCodeServices)   //授权码模式需要
                .tokenServices(tokenService())  //令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);   //允许post提交
    }

    /**
     * 4.配置令牌端点(Token Endpoint)的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")      //对应/oauth/token_key
                .checkTokenAccess("permitAll()")    //对应/oauth/check_token
                .allowFormAuthenticationForClients();   //表单认证，申请令牌
    }


}
