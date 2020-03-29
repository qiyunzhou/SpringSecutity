package pers.qyz.security.distributed.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.qyz.security.distributed.order.model.UserDTO;

@RestController
public class OrderController {

    @GetMapping("/r1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1(){
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDTO.getUsername()+"访问资源r1";
    }
}
