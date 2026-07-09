package com.gantt.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gantt.modules.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<String> selectPermissionsByUserId(@Param("userId") Long userId);

    List<String> selectRoleKeysByUserId(@Param("userId") Long userId);
}
