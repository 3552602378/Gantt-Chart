package com.gantt.modules.plan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanCreateDTO {

    @NotBlank(message = "计划名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;

    private Integer status;

    private Integer progress;
}
