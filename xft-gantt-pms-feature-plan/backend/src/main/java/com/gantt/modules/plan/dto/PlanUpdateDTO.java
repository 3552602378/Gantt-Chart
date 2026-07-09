package com.gantt.modules.plan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanUpdateDTO {

    @NotNull(message = "ID不能为空")
    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer status;

    private Integer progress;
}
