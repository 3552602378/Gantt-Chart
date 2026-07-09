package com.gantt.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gantt.common.result.Result;
import com.gantt.modules.system.dto.ResetPasswordDTO;
import com.gantt.modules.system.dto.UserCreateDTO;
import com.gantt.modules.system.dto.UserQueryDTO;
import com.gantt.modules.system.dto.UserUpdateDTO;
import com.gantt.modules.system.entity.User;
import com.gantt.modules.system.service.UserService;
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

import java.util.List;

@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @SaCheckPermission("user:list")
    @GetMapping("/list")
    public Result<IPage<User>> list(UserQueryDTO query) {
        return Result.ok(userService.listUsers(query));
    }

    @SaCheckPermission("user:create")
    @PostMapping
    public Result<User> create(@Valid @RequestBody UserCreateDTO dto) {
        return Result.ok(userService.createUser(dto));
    }

    @SaCheckPermission("user:update")
    @PutMapping
    public Result<User> update(@Valid @RequestBody UserUpdateDTO dto) {
        return Result.ok(userService.updateUser(dto));
    }

    @SaCheckPermission("user:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.ok();
    }

    @SaCheckPermission("user:update")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody java.util.Map<String, Integer> body) {
        userService.updateStatus(id, body.get("status"));
        return Result.ok();
    }

    @SaCheckPermission("user:role")
    @PutMapping("/{id}/roles")
    public Result<Void> resetRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        userService.resetRoles(id, roleIds);
        return Result.ok();
    }

    @SaCheckPermission("user:update")
    @PutMapping("/reset-password")
    public Result<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        userService.resetPassword(dto);
        return Result.ok();
    }
}
