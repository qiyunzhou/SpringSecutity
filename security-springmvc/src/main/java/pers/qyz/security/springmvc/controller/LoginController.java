package pers.qyz.security.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.qyz.security.springmvc.model.AuthenticationRequest;
import pers.qyz.security.springmvc.model.UserDto;
import pers.qyz.security.springmvc.service.AuthenticationService;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/login",produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String login(AuthenticationRequest authenticationRequest){
        UserDto userDto = authenticationService.authentication(authenticationRequest);
        return userDto.getUsername()+"登录成功";
    }
}
