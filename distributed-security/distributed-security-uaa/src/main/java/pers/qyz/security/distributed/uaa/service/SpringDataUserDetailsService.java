package pers.qyz.security.distributed.uaa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.qyz.security.distributed.uaa.dao.UserDao;
import pers.qyz.security.distributed.uaa.model.UserDto;

import java.util.List;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);

        //根据账号去数据库查询...
        UserDto user = userDao.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        //查询用户权限
        List<String> permissions = userDao.findPermissionsByUserId(user.getId());
        String[] permissionsArray = new String[permissions.size()];
        permissions.toArray(permissionsArray);
        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).authorities(permissionsArray).build();
        return userDetails;
    }
}
