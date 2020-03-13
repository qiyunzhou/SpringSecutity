package pers.qyz.springboot_mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pers.qyz.springboot_mybatis.entity.User;
import pers.qyz.springboot_mybatis.utils.JSONChange;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMybatisApplication.class)
public class RedisTest {



    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test() throws Exception{
        User user = User.builder().name("zhangsan").password("123").name("张三").build();
        String objToJson = JSONChange.objToJson(user);

        redisTemplate.boundValueOps("user").set(objToJson);

        String userRedis = redisTemplate.boundValueOps("user").get();


        System.out.println("userRedis:"+userRedis);
    }
}
