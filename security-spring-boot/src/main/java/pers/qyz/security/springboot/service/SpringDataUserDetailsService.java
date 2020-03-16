package pers.qyz.security.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pers.qyz.security.springboot.dao.UserDao;
import pers.qyz.security.springboot.model.UserDto;

public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);

        //根据账号去数据库查询...
        UserDto user = userDao.getUserByUsername(username);
        if (user == null) {
            return null;
        }
        //这里暂时使用静态数据
        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).authorities("p1").build();
        return userDetails;
    }
}
