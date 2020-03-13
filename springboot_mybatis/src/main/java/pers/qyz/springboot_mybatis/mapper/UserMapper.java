package pers.qyz.springboot_mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.qyz.springboot_mybatis.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> queryUserList();
}
