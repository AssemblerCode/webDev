package com.itheima.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义添加用户参数类
 */
@Data
public class UserAddParam implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Boolean enabled;

}
