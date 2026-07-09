package com.gantt.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gantt.modules.system.dto.ResetPasswordDTO;
import com.gantt.modules.system.dto.UserCreateDTO;
import com.gantt.modules.system.dto.UserLoginDTO;
import com.gantt.modules.system.dto.UserQueryDTO;
import com.gantt.modules.system.dto.UserRegisterDTO;
import com.gantt.modules.system.dto.UserUpdateDTO;
import com.gantt.modules.system.entity.User;

import java.util.List;

public interface UserService {

    User register(UserRegisterDTO dto);

    String login(UserLoginDTO dto);

    User getCurrentUser();

    IPage<User> listUsers(UserQueryDTO query);

    User createUser(UserCreateDTO dto);

    User updateUser(UserUpdateDTO dto);

    void deleteUser(Long id);

    void updateStatus(Long id, Integer status);

    void resetRoles(Long id, List<Long> roleIds);

    void resetPassword(ResetPasswordDTO dto);
}
