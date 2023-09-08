package com.rbac.springsecurity.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("permission")
@Data
public class Permission implements Serializable {
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "`desc`")
    private String desc;

    @TableField(value = "`url`")
    private String url;

    @TableField(value = "`method`")
    private String method;
}
