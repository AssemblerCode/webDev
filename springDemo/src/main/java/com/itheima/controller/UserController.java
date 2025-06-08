package com.itheima.controller;

import com.itheima.config.DBUserDetailsManagerConfig;
import com.itheima.entity.UserAddParam;
import com.itheima.entity.UserEntity;
import com.itheima.entity.vo.UserVo;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DBUserDetailsManagerConfig userDetailsManagerConfig;

    @Autowired
    private UserService userService;

    @GetMapping("/getUserList")
    public List<UserVo> getUserList() {
        List<UserEntity> list = userService.list();
        List<UserVo> vos = new ArrayList<>();
        for (UserEntity item : list) {
            UserVo vo = new UserVo();
            vo.setId(item.getId());
            vo.setUsername(item.getUsername());
            vo.setPassword(item.getPassword());
            vo.setEnabled(item.getEnabled());
            vos.add(vo);
        }
        return vos;
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserAddParam param) {
        userDetailsManagerConfig.createUser(User.builder()
                .username(param.getUsername())
                .password(param.getPassword())
                .build());
        return "{\"succ\": \"ok\"}";
    }
}
