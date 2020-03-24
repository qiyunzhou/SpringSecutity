package pers.qyz.security.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //配置用户信息服务
   /* @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("zhangsan").password("$2a$10$2ZYY9ZN6SmprKBdVFbdJ9u0f5zQWdBxq4mT7nibUvmmDIi.iovrSC").authorities("p1").build());
        userDetailsManager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
        return userDetailsManager;
    }*/

    //密码编码器
  /*  @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //配置安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/r/r1").hasAuthority("p1")
//                .antMatchers("/r/r2").hasAuthority("p2")
                .antMatchers("/r/**").authenticated()   //所有/r/**的认证请求必须认证通过
                .anyRequest().permitAll()   // 其它请求可以访问
                .and()
                .formLogin()  //允许表单登录
                .loginPage("/login-view")   //指定我们自己的登录页
                .loginProcessingUrl("/login")  //指定登录处理的URL，也就是用户名、密码表单提交的目的路径
                .successForwardUrl("/login-success")    // 自定义登录成功页面
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); //设置会话
//                .and()
//                .logout()   //自定义退出
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login‐view?logout");

    }
}
