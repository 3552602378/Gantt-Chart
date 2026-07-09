package com.gantt.modules.plan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gantt.common.exception.BusinessException;
import com.gantt.modules.plan.dto.PlanCreateDTO;
import com.gantt.modules.plan.dto.PlanQueryDTO;
import com.gantt.modules.plan.dto.PlanUpdateDTO;
import com.gantt.modules.plan.entity.Plan;
import com.gantt.modules.plan.mapper.PlanMapper;
import com.gantt.modules.plan.service.PlanService;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {

    @Override
    public IPage<Plan> listPlans(PlanQueryDTO query) {
        Page<Plan> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Plan> wrapper = new LambdaQueryWrapper<Plan>()
                .like(query.getName() != null && !query.getName().isBlank(), Plan::getName, query.getName())
                .eq(query.getStatus() != null, Plan::getStatus, query.getStatus())
                .orderByDesc(Plan::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public Plan createPlan(PlanCreateDTO dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new BusinessException("结束日期不能早于开始日期");
        }
        Plan plan = new Plan();
        plan.setName(dto.getName());
        plan.setDescription(dto.getDescription());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());
        plan.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        plan.setProgress(dto.getProgress() != null ? dto.getProgress() : 0);
        this.save(plan);
        return plan;
    }

    @Override
    public Plan updatePlan(PlanUpdateDTO dto) {
        Plan plan = this.getById(dto.getId());
        if (plan == null) {
            throw new BusinessException("计划不存在");
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null
                && dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new BusinessException("结束日期不能早于开始日期");
        }
        if (dto.getName() != null) plan.setName(dto.getName());
        if (dto.getDescription() != null) plan.setDescription(dto.getDescription());
        if (dto.getStartDate() != null) plan.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) plan.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) plan.setStatus(dto.getStatus());
        if (dto.getProgress() != null) plan.setProgress(dto.getProgress());
        this.updateById(plan);
        return plan;
    }

    @Override
    public void deletePlan(Long id) {
        if (!this.removeById(id)) {
            throw new BusinessException("计划不存在");
        }
    }
}
