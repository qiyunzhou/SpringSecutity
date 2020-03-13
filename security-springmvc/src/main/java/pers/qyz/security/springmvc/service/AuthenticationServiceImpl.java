package pers.qyz.security.springmvc.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.qyz.security.springmvc.model.AuthenticationRequest;
import pers.qyz.security.springmvc.model.UserDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * 用户认证，检验用户身份信息是否合法
     * @param authenticationRequest 用户认证请求
     * @return 认证成功的用户信息
     */
    @Override
    public UserDto authentication(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        // 检验参数是否为空
        if(authenticationRequest == null
                || StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)){
            throw new RuntimeException("账号或密码为空");
        }
        UserDto userDto = getUserDto(username);
        if(userDto == null){
            throw new RuntimeException("查询不到该用户");
        }
        if(!password.equals(userDto.getPassword())){
            throw new RuntimeException("账号或密码错误");
        }
        // 根据账号去查询数据库，这里采用模拟数据


        return userDto;
    }

    //模拟用户查询
    public UserDto getUserDto(String username){
        return userMap.get(username);
    }

    //用户信息
    private static Map<String,UserDto> userMap = new HashMap<>();
    static{
        Set<String> authorities1 = new HashSet<>();
        authorities1.add("p1");
        Set<String> authorities2 = new HashSet<>();
        authorities2.add("p2");
        userMap.put("zhangsan",new UserDto("1010","zhangsan","123","张三","133443",authorities1));
        userMap.put("lisi",new UserDto("1011","lisi","456","李四","144553",authorities2));
    }
}
