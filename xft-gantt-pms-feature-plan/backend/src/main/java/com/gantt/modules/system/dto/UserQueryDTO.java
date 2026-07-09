package com.gantt.modules.system.dto;

import lombok.Data;

@Data
public class UserQueryDTO {

    private String username;

    private String nickname;

    private Integer status;

    private Integer page = 1;

    private Integer size = 10;
}
