package com.gantt.modules.plan.controller;

import com.gantt.common.result.Result;
import com.gantt.modules.plan.entity.Plan;
import com.gantt.modules.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping("/list")
    public Result<List<Plan>> list() {
        return Result.ok(planService.list());
    }

    @GetMapping("/{id}")
    public Result<Plan> getById(@PathVariable Long id) {
        return Result.ok(planService.getById(id));
    }
}
