package com.gantt.modules.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gantt.modules.plan.entity.Plan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanMapper extends BaseMapper<Plan> {
}
