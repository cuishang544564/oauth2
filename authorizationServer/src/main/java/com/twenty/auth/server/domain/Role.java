package com.twenty.auth.server.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @author cuishang
 * @since 2020/3/1
 */
@Data
@TableName("role")
public class Role {
    @TableId
    private String roleId;
    @NotNull
    private String roleName;
    @NotNull
    private String roleCode;
    private String category;

    @TableField(exist = false)
    private String categoryName;

    private Integer status;

    private boolean sys;

    private Integer level;

    private Integer seq;
    /**
     * 所属组织id
     */
    private String orgId;
    @TableField(exist = false)
    private String orgName;
    /**
     * 拥有全部功能菜单
     */
    private Boolean checkAll;

    private String remark;
    private String extra;

    private String pinyin;
    @TableField(exist = false)
    private Set<String> menuIds;
    @TableField(exist = false)
    private Set<String> userIds;
    @TableField(exist = false)
    private List<Menu> menus;
//    @TableField(exist = false)
//    private List<SysSystem> systemList;

    @TableField(exist = false)
    private Boolean referenced;

    @TableField(exist = false)
    private Integer menuCount;

    @TableField(exist = false)
    private Integer userCount;

    @TableField(exist = false)
    private String userId;

}

