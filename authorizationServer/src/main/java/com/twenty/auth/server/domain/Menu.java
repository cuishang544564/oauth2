package com.twenty.auth.server.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@TableName("menu")
public class Menu {

    public Menu() {

    }
    @TableId
    private String menuId;
    private String pid;
    private String pids;
    @TableField(exist = false)
    private List<Perm> perms;
    private String menuName;
    private String icon;
    private String url;
    private String extra;

    private Boolean regUrl;
    private Boolean regExtra;
    private String sysId;
    /**
     * 1 菜单  2. 页面 3 按钮
     */
    private Integer type;
    private String tips;

    private Boolean hidden;

    private Integer status;
    @TableField(exist = false)
    private Boolean allMenu=false;
    @TableField(exist = false)
    private Boolean hasDataField;
    private Boolean opened;
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 所属应用名称
     */
    @TableField(exist = false)
    private String sysName;

    /**
     * 标记角色是否选中此菜单
     */
    @TableField(exist = false)
    private Boolean checked;

}
