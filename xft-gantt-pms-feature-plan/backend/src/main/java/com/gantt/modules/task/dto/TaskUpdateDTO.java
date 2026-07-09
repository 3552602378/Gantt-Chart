package com.gantt.modules.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskUpdateDTO {

    @NotNull(message = "ID不能为空")
    private Long id;

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
