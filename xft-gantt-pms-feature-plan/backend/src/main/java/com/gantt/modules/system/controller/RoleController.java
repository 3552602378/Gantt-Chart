package com.gantt.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gantt.common.result.Result;
import com.gantt.modules.system.entity.Role;
import com.gantt.modules.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @SaCheckPermission("user:role")
    @GetMapping("/list")
    public Result<List<Role>> list() {
        return Result.ok(roleService.listAll());
    }
}
