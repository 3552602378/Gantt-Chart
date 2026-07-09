package com.gantt.modules.task.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gantt.common.result.Result;
import com.gantt.modules.task.dto.TaskCreateDTO;
import com.gantt.modules.task.dto.TaskQueryDTO;
import com.gantt.modules.task.dto.TaskUpdateDTO;
import com.gantt.modules.task.entity.Task;
import com.gantt.modules.task.service.TaskService;
import com.gantt.modules.task.vo.TaskVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @SaCheckPermission("task:list")
    @GetMapping("/list")
    public Result<IPage<Task>> list(TaskQueryDTO query) {
        return Result.ok(taskService.listTasks(query));
    }

    @SaCheckPermission("task:list")
    @GetMapping("/tree")
    public Result<List<TaskVO>> tree(@RequestParam(required = false) Long planId) {
        return Result.ok(taskService.taskTree(planId));
    }

    @SaCheckPermission("task:list")
    @GetMapping("/{id}")
    public Result<Task> getById(@PathVariable Long id) {
        return Result.ok(taskService.getById(id));
    }

    @SaCheckPermission("task:create")
    @PostMapping
    public Result<Task> create(@Valid @RequestBody TaskCreateDTO dto) {
        return Result.ok(taskService.createTask(dto));
    }

    @SaCheckPermission("task:update")
    @PutMapping
    public Result<Task> update(@Valid @RequestBody TaskUpdateDTO dto) {
        return Result.ok(taskService.updateTask(dto));
    }

    @SaCheckPermission("task:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return Result.ok();
    }
}
