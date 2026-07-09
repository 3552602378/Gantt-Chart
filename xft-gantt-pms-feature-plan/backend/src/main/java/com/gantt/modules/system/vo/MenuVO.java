package com.gantt.modules.system.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class MenuVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private String component;
    private String icon;
    private Integer sort;
    private List<MenuVO> children;
}
