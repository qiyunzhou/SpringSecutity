package pers.qyz.security.distributed.order.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.qyz.security.distributed.order.common.EncryptUtil;
import pers.qyz.security.distributed.order.model.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("json-token");
        if (token != null) {
            String json = EncryptUtil.decodeUTF8StringBase64(token);
            //将token转化为json对象
            JSONObject jsonObject = JSON.parseObject(json);
            //用户身份信息
            UserDTO user = new UserDTO();
            user.setUsername(jsonObject.getString("principal"));
            //用户权限
            JSONArray authoritiesArray = jsonObject.getJSONArray("authorities");
            String[] authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);
            //将用户信息和用户权限填充到用户身份token对象中
            //2.新建并填充authentication
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList(authorities));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //3.将authentication保存进安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
        //将authenticationToken填充到安全上下文中
    }
}