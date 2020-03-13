package pers.qyz.springboot_mybatis.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.qyz.springboot_mybatis.entity.User;
import pers.qyz.springboot_mybatis.mapper.UserMapper;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @RequestMapping("/query")
    @ResponseBody
    public List<User> queryUserList(){
        List<User> users = userMapper.queryUserList();
        return users;
    }
}
