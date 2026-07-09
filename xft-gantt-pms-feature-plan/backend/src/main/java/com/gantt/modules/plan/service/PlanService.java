package com.gantt.modules.plan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gantt.modules.plan.dto.PlanCreateDTO;
import com.gantt.modules.plan.dto.PlanQueryDTO;
import com.gantt.modules.plan.dto.PlanUpdateDTO;
import com.gantt.modules.plan.entity.Plan;

public interface PlanService extends IService<Plan> {

    IPage<Plan> listPlans(PlanQueryDTO query);

    Plan createPlan(PlanCreateDTO dto);

    Plan updatePlan(PlanUpdateDTO dto);

    void deletePlan(Long id);
}
