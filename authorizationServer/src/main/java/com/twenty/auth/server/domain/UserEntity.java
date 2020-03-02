package com.twenty.auth.server.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.List;

/**
 * 
 *
 * @author cuishang
 * @since 2020/2/29
 */
@TableName(value="user")
@Data
public class UserEntity {
    @TableId
    private String userId;
    private String username;
    private String password;
    private Integer age;
    private String mark;

    private List<Role> roles;
}
