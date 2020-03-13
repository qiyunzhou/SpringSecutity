package pers.qyz.security.springmvc.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.qyz.security.springmvc.model.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SimpleAuthenticationIntercepto implements HandlerInterceptor {

    //请求拦截方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //读取会话信息
        Object attribute = request.getSession().getAttribute(UserDto.SESSION_USER_KEY);
        //请求的url
        String requestURL = request.getRequestURI();
        if(attribute == null){
            writeContent(response,"请登录！");
        }
        UserDto userDto = (UserDto) attribute;
        if(userDto.getAuthorities().contains("p1") && requestURL.contains("/r/r1")){
            return true;
        }
        if(userDto.getAuthorities().contains("p2") && requestURL.contains("/r/r2")){
            return true;
        }
        writeContent(response,"权限不足，拒绝访问！");
        return false;
    }

    private void writeContent(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(msg);
        writer.close();
//        response.resetBuffer();
    }
}
