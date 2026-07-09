package com.gantt.modules.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateDTO {

    @NotNull(message = "计划ID不能为空")
    private Long projectId;

    private Long parentId;

    @NotBlank(message = "任务名称不能为空")
    private String name;

    private String description;

    private String assignee;

    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;

    private Integer priority;

    private Integer status;

    private Integer progress;

    private Integer sortOrder;
}
