package com.twenty.auth.server.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @author cuishang
 * @since 2020/3/2
 */
@Data
@TableName("perm")
public class Perm {
    private String permId;
    private String permCode;
    private String permName;
    private String menuId;
}
