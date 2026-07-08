package com.gantt.modules.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gantt.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("plan_task")
public class Task extends BaseEntity {

    private Long projectId;

    private Long parentId;

    private String name;

    private String description;

    private String assignee;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer priority;

    private Integer status;

    private Integer progress;

    private Integer sortOrder;
}
