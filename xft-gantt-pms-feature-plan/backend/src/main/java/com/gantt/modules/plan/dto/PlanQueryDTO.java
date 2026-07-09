package com.gantt.modules.plan.dto;

import lombok.Data;

@Data
public class PlanQueryDTO {

    private String name;

    private Integer status;

    private Integer page = 1;

    private Integer size = 10;
}
