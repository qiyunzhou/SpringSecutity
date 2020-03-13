package pers.qyz.security.springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 当前登录用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
