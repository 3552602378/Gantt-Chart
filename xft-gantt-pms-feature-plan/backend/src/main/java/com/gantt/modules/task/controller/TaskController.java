package com.gantt.modules.task.controller;

import com.gantt.common.result.Result;
import com.gantt.modules.task.entity.Task;
import com.gantt.modules.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/list")
    public Result<List<Task>> list() {
        return Result.ok(taskService.list());
    }

    @GetMapping("/{id}")
    public Result<Task> getById(@PathVariable Long id) {
        return Result.ok(taskService.getById(id));
    }
}
