package pers.qyz.security.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;
import pers.qyz.security.springboot.dao.UserDao;
import pers.qyz.security.springboot.model.UserDto;
import pers.qyz.security.springboot.service.SpringDataUserDetailsService;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBCrypt {

    @Autowired
    UserDao userDao;

    @Autowired
    SpringDataUserDetailsService service;

    @Test
    public void test1() {
        //对原始密码加密
        String hashpw = BCrypt.hashpw("456", BCrypt.gensalt());
        System.out.println("hashpw = " + hashpw);

        //校验原始密码和BCrypt密码是否一致
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$k/xIRYHYm5ky6KnhxsDifu9YA.wlIe1mNw5BpiH8fOgBch1J2aAWa");
        boolean checkpw2 = BCrypt.checkpw("123", "$2a$10$2ZYY9ZN6SmprKBdVFbdJ9u0f5zQWdBxq4mT7nibUvmmDIi.iovrSC");
        System.out.println("checkpw = " + checkpw);
        System.out.println("checkpw2 = " + checkpw2);
    }

    @Test
    public void testDao() {
        UserDto user = userDao.getUserByUsername("zhangsan");
        boolean checkpw = BCrypt.checkpw("123", user.getPassword());
        System.out.println("zhangsan = " + user);
        System.out.println("checkpw = " + checkpw);

        List<String> list = userDao.findPermissionsByUserId("1");
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void testService(){
        UserDetails userDetails = service.loadUserByUsername("zhangsan");
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        System.out.println("username:"+username+",password:"+password);
    }
}
