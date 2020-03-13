package pers.qyz.security.springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
