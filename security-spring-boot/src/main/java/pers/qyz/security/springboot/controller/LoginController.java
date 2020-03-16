package pers.qyz.security.springboot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/login-success", produces = {"text/plain;charset=utf-8"})
    public String loginSuccess() {
        return getUsername()+" login success";
    }

    /**
     * 测试资源1
     *
     * @return
     */
    @GetMapping(value = "/r/r1", produces = {"text/plain;charset=utf-8"})
    public String r1() {
        return getUsername()+" 访问资源1";
    }

    /**
     * 测试资源1
     *
     * @return
     */
    @GetMapping(value = "/r/r2", produces = {"text/plain;charset=utf-8"})
    public String r2() {
        return getUsername()+" 访问资源2";
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        String username = null;
        if(principal instanceof org.springframework.security.core.userdetails.UserDetails){
            username = ((UserDetails) principal).getUsername();
        }else{
            username.toString();
        }
        return username;
    }
}
