package pers.qyz.security.springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AuthenticationRequest {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
