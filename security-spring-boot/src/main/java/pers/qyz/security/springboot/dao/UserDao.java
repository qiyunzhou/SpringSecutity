package pers.qyz.security.springboot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pers.qyz.security.springboot.model.UserDto;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDto getUserByUsername(String username){
        String sql = "select * from t_user";
        List<UserDto> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserDto.class));
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }
}
