package com.gantt.modules.task.dto;

import lombok.Data;

@Data
public class TaskQueryDTO {

    private Long planId;

    private String name;

    private Integer status;

    private Integer page = 1;

    private Integer size = 10;
}
