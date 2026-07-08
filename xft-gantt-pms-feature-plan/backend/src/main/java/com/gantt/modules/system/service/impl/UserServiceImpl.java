package com.gantt.modules.system.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gantt.common.exception.BusinessException;
import com.gantt.modules.system.dto.UserLoginDTO;
import com.gantt.modules.system.dto.UserRegisterDTO;
import com.gantt.modules.system.entity.User;
import com.gantt.modules.system.mapper.UserMapper;
import com.gantt.modules.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, StpInterface {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegisterDTO dto) {
        long count = userMapper.selectCount(
                new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername())
        );
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setStatus(1);
        userMapper.insert(user);
        return user;
    }

    @Override
    public String login(UserLoginDTO dto) {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername())
        );
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    public User getCurrentUser() {
        long userId = StpUtil.getLoginIdAsLong();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.singletonList("*");
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Collections.singletonList("admin");
    }
}
