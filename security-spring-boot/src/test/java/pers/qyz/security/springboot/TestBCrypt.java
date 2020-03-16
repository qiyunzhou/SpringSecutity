package pers.qyz.security.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestBCrypt {

    @Test
    public void test1(){
        //对原始密码加密
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println("hashpw = " + hashpw);

        //校验原始密码和BCrypt密码是否一致
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$k/xIRYHYm5ky6KnhxsDifu9YA.wlIe1mNw5BpiH8fOgBch1J2aAWa");
        boolean checkpw2 = BCrypt.checkpw("123", "$2a$10$2ZYY9ZN6SmprKBdVFbdJ9u0f5zQWdBxq4mT7nibUvmmDIi.iovrSC");
        System.out.println("checkpw = " + checkpw);
        System.out.println("checkpw2 = " + checkpw2);

    }
}
