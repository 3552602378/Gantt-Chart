package com.gantt.modules.plan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gantt.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("plan_project")
public class Plan extends BaseEntity {

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer status;

    private Integer progress;
}
