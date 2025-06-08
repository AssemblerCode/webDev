package com.itheima.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义用户类
 */
@Data
public class UserVo implements Serializable {

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
