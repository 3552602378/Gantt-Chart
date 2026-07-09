package com.gantt.modules.system.service.impl;

import com.gantt.modules.system.entity.Menu;
import com.gantt.modules.system.mapper.MenuMapper;
import com.gantt.modules.system.service.MenuService;
import com.gantt.modules.system.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public List<MenuVO> listRoutesByUserId(Long userId) {
        List<Menu> menus = menuMapper.selectMenusByUserId(userId);
        return buildMenuTree(menus);
    }

    private List<MenuVO> buildMenuTree(List<Menu> menus) {
        Map<Long, List<Menu>> grouped = new LinkedHashMap<>();
        for (Menu m : menus) {
            grouped.computeIfAbsent(m.getParentId() == null ? 0L : m.getParentId(), k -> new ArrayList<>()).add(m);
        }
        return buildChildren(grouped, 0L);
    }

    private List<MenuVO> buildChildren(Map<Long, List<Menu>> grouped, Long parentId) {
        List<Menu> children = grouped.get(parentId);
        if (children == null) {
            return List.of();
        }
        children.sort(Comparator.comparingInt(m -> m.getSort() == null ? 0 : m.getSort()));
        List<MenuVO> result = new ArrayList<>(children.size());
        for (Menu m : children) {
            MenuVO vo = new MenuVO();
            vo.setId(m.getId());
            vo.setParentId(m.getParentId());
            vo.setName(m.getName());
            vo.setPath(m.getPath());
            vo.setComponent(m.getComponent());
            vo.setIcon(m.getIcon());
            vo.setSort(m.getSort());
            vo.setChildren(buildChildren(grouped, m.getId()));
            result.add(vo);
        }
        return result;
    }
}
