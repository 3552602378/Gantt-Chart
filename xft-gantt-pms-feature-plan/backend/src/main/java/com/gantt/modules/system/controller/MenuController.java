package com.gantt.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.gantt.common.result.Result;
import com.gantt.modules.system.service.MenuService;
import com.gantt.modules.system.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @SaCheckLogin
    @GetMapping("/routes")
    public Result<List<MenuVO>> routes() {
        Long userId = StpUtil.getLoginIdAsLong();
        return Result.ok(menuService.listRoutesByUserId(userId));
    }
}
