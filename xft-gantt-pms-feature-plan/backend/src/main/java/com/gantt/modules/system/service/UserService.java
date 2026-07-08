package com.gantt.modules.system.service;

import com.gantt.modules.system.dto.UserLoginDTO;
import com.gantt.modules.system.dto.UserRegisterDTO;
import com.gantt.modules.system.entity.User;

public interface UserService {

    User register(UserRegisterDTO dto);

    String login(UserLoginDTO dto);

    User getCurrentUser();
}
