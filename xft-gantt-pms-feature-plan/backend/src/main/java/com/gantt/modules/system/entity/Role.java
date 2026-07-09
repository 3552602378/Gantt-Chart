package com.gantt.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gantt.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class Role extends BaseEntity {

    private String roleKey;

    private String roleName;

    private String description;

    private Integer status;
}
