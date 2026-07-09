package com.gantt.modules.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDTO {

    @NotNull(message = "id不能为空")
    private Long id;

    private String nickname;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(max = 20, message = "手机号长度不超过20")
    private String phone;

    private Integer status;

    private List<Long> roleIds;
}
