package pers.qyz.springboot_mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.qyz.springboot_mybatis.entity.User;
import pers.qyz.springboot_mybatis.mapper.UserMapper;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMybatisApplication.class)
public class UserTest {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    public void test(){
        List<User> users = userMapper.queryUserList();
        System.out.println(users);
    }
}
