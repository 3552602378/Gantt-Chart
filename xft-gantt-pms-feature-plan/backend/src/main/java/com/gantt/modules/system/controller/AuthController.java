package com.gantt.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gantt.common.result.Result;
import com.gantt.modules.system.dto.UserLoginDTO;
import com.gantt.modules.system.dto.UserRegisterDTO;
import com.gantt.modules.system.entity.User;
import com.gantt.modules.system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody UserRegisterDTO dto) {
        return Result.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody UserLoginDTO dto) {
        return Result.ok(userService.login(dto));
    }

    @SaCheckPermission("user.info")
    @GetMapping("/me")
    public Result<User> me() {
        return Result.ok(userService.getCurrentUser());
    }
}
