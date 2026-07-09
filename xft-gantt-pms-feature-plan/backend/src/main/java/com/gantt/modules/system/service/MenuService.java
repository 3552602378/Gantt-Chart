package com.gantt.modules.system.service;

import com.gantt.modules.system.vo.MenuVO;

import java.util.List;

public interface MenuService {

    List<MenuVO> listRoutesByUserId(Long userId);
}
