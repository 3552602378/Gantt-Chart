package com.gantt.modules.task.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gantt.modules.task.dto.TaskCreateDTO;
import com.gantt.modules.task.dto.TaskQueryDTO;
import com.gantt.modules.task.dto.TaskUpdateDTO;
import com.gantt.modules.task.entity.Task;
import com.gantt.modules.task.vo.TaskVO;

import java.util.List;

public interface TaskService extends IService<Task> {

    IPage<Task> listTasks(TaskQueryDTO query);

    Task createTask(TaskCreateDTO dto);

    Task updateTask(TaskUpdateDTO dto);

    void deleteTask(Long id);

    List<TaskVO> taskTree(Long planId);
}
