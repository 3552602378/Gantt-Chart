package com.gantt.modules.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gantt.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_task")
public class Task extends BaseEntity {

    private Long planId;

    private Long parentId;

    private String title;

    private String description;

    private String assignee;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer priority;

    private Integer status;

    private BigDecimal progress;

    private Integer sortOrder;
}
