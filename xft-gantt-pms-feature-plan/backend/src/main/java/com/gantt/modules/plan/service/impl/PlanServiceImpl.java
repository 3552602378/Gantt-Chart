package com.gantt.modules.plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gantt.modules.plan.entity.Plan;
import com.gantt.modules.plan.mapper.PlanMapper;
import com.gantt.modules.plan.service.PlanService;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {
}
