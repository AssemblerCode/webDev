package com.itheima.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.entity.UserEntity;
import com.itheima.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@Data
public class DBUserDetailsManagerConfig implements UserDetailsManager, UserDetailsPasswordService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        System.out.println("createUser");

        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setEnabled(true);
        if (!userService.save(entity)) {
            return;
        }

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER").build());

//        InMemoryUserDetailsManager manager =  (InMemoryUserDetailsManager  ) user;
//        manager.createUser(User.withDefaultPasswordEncoder().username(user.getUsername()).password(user.getPassword()).roles("USER").build() );
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        LambdaQueryWrapper<UserEntity> queryWrap = new LambdaQueryWrapper<>();
//        queryWrap.first(" SELECT 1 ");
        queryWrap.eq(UserEntity::getUsername, username);
        queryWrap.last(" LIMIT 1 ");
        return userService.exists(queryWrap);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean b = userExists(username);
        LambdaQueryWrapper<UserEntity> queryWrap = new LambdaQueryWrapper<>();
        queryWrap.eq(UserEntity::getUsername, username);
        UserEntity userEntity = userService.getOne(queryWrap);
        Assert.notNull(userEntity, "用户不存在");

        // 设置权限列表
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        return new User(username, userEntity.getPassword(), userEntity.getEnabled()
                , true //  表示用户账号是否过期
                , true //  表示用户凭证是否过期
                , true //  表示用户是否未被锁定
                , authorities);//  用户权限列表
    }

}
