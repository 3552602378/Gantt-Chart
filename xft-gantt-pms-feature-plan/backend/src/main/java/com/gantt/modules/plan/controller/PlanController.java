package com.gantt.modules.plan.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gantt.common.result.Result;
import com.gantt.modules.plan.dto.PlanCreateDTO;
import com.gantt.modules.plan.dto.PlanQueryDTO;
import com.gantt.modules.plan.dto.PlanUpdateDTO;
import com.gantt.modules.plan.entity.Plan;
import com.gantt.modules.plan.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @SaCheckPermission("plan:list")
    @GetMapping("/list")
    public Result<IPage<Plan>> list(PlanQueryDTO query) {
        return Result.ok(planService.listPlans(query));
    }

    @SaCheckPermission("plan:list")
    @GetMapping("/{id}")
    public Result<Plan> getById(@PathVariable Long id) {
        return Result.ok(planService.getById(id));
    }

    @SaCheckPermission("plan:create")
    @PostMapping
    public Result<Plan> create(@Valid @RequestBody PlanCreateDTO dto) {
        return Result.ok(planService.createPlan(dto));
    }

    @SaCheckPermission("plan:update")
    @PutMapping
    public Result<Plan> update(@Valid @RequestBody PlanUpdateDTO dto) {
        return Result.ok(planService.updatePlan(dto));
    }

    @SaCheckPermission("plan:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        planService.deletePlan(id);
        return Result.ok();
    }
}
