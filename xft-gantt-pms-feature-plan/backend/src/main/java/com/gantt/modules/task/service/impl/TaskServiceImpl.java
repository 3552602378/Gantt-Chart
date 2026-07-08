package com.gantt.modules.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gantt.modules.task.entity.Task;
import com.gantt.modules.task.mapper.TaskMapper;
import com.gantt.modules.task.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
}
