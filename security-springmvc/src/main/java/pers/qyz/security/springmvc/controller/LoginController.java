package pers.qyz.security.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.qyz.security.springmvc.model.AuthenticationRequest;
import pers.qyz.security.springmvc.model.UserDto;
import pers.qyz.security.springmvc.service.AuthenticationService;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 登录功能
     * @param authenticationRequest
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",produces = {"text/html;charset=UTF-8"})
    public String login(AuthenticationRequest authenticationRequest, HttpSession session){
        UserDto userDto = authenticationService.authentication(authenticationRequest);
        //存入session
        session.setAttribute(UserDto.SESSION_USER_KEY,userDto);
        return userDto.getUsername()+"登录成功";
    }

    /**
     * 测试会话功能
     * @param session
     */
    @RequestMapping(value = "/test",produces = {"text/html;charset=UTF-8"})
    public String testSession(HttpSession session){
        String visitName = null;
        Object attribute = session.getAttribute(UserDto.SESSION_USER_KEY);
        if(attribute == null){
           visitName = "匿名";
        }else {
            UserDto userDto = (UserDto) attribute;
            visitName = userDto.getUsername();
        }
        return visitName + "访问资源test";
    }

    @RequestMapping(value = "/logout",produces = {"text/html;charset=UTF-8"})
    public String logout(HttpSession session){
        session.invalidate();
        return "退出成功";
    }
}
