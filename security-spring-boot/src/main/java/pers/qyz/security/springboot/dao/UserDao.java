package pers.qyz.security.springboot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pers.qyz.security.springboot.model.PermissionDto;
import pers.qyz.security.springboot.model.UserDto;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDto getUserByUsername(String username){
        String sql = "select * from t_user where username = ?";
        List<UserDto> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserDto.class));
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    //根据用户id查询用户权限
    public List<String> findPermissionsByUserId(String userId){
        String sql = "select code from t_permission where id in (\n" +
                "\tselect permission_id from t_role_permission where role_id in (\n" +
                "\t\tselect id from t_role where id = ?\n" +
                "\t)\n" +
                ")";
        List<PermissionDto> permissionDtoList = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDto.class));
        List<String> permissions = new ArrayList<>();
        permissionDtoList.iterator().forEachRemaining(c->permissions.add(c.getCode()));
        return permissions;
    }
}
