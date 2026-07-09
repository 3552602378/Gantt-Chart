package com.gantt.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gantt.modules.system.entity.Role;
import com.gantt.modules.system.mapper.RoleMapper;
import com.gantt.modules.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    @Override
    public List<Role> listAll() {
        return roleMapper.selectList(
                new LambdaQueryWrapper<Role>().orderByAsc(Role::getId)
        );
    }
}
