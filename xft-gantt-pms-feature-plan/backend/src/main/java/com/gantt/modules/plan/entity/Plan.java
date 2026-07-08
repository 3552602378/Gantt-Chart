package com.gantt.modules.plan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gantt.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_plan")
public class Plan extends BaseEntity {

    private String title;

    private String type;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer progress;

    private Long userId;
}
