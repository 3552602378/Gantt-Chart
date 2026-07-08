package com.gantt.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gantt.modules.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
