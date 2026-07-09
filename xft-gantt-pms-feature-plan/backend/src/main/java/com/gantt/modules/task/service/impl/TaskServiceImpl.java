package com.gantt.modules.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gantt.common.exception.BusinessException;
import com.gantt.modules.task.dto.TaskCreateDTO;
import com.gantt.modules.task.dto.TaskQueryDTO;
import com.gantt.modules.task.dto.TaskUpdateDTO;
import com.gantt.modules.task.entity.Task;
import com.gantt.modules.task.mapper.TaskMapper;
import com.gantt.modules.task.service.TaskService;
import com.gantt.modules.task.vo.TaskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public IPage<Task> listTasks(TaskQueryDTO query) {
        Page<Task> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<Task>()
                .eq(query.getPlanId() != null, Task::getProjectId, query.getPlanId())
                .like(query.getName() != null && !query.getName().isBlank(), Task::getName, query.getName())
                .eq(query.getStatus() != null, Task::getStatus, query.getStatus())
                .orderByAsc(Task::getSortOrder);
        return this.page(page, wrapper);
    }

    @Override
    public Task createTask(TaskCreateDTO dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new BusinessException("结束日期不能早于开始日期");
        }
        Task task = new Task();
        BeanUtils.copyProperties(dto, task);
        if (task.getStatus() == null) task.setStatus(0);
        if (task.getProgress() == null) task.setProgress(0);
        if (task.getSortOrder() == null) task.setSortOrder(0);
        if (task.getParentId() == null) task.setParentId(0L);
        this.save(task);
        return task;
    }

    @Override
    public Task updateTask(TaskUpdateDTO dto) {
        Task task = this.getById(dto.getId());
        if (task == null) {
            throw new BusinessException("任务不存在");
        }
        if (dto.getStartDate() != null && dto.getEndDate() != null
                && dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new BusinessException("结束日期不能早于开始日期");
        }
        if (dto.getParentId() != null) task.setParentId(dto.getParentId());
        if (dto.getName() != null) task.setName(dto.getName());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        if (dto.getAssignee() != null) task.setAssignee(dto.getAssignee());
        if (dto.getStartDate() != null) task.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) task.setEndDate(dto.getEndDate());
        if (dto.getPriority() != null) task.setPriority(dto.getPriority());
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());
        if (dto.getProgress() != null) task.setProgress(dto.getProgress());
        if (dto.getSortOrder() != null) task.setSortOrder(dto.getSortOrder());
        this.updateById(task);
        return task;
    }

    @Override
    public void deleteTask(Long id) {
        if (!this.removeById(id)) {
            throw new BusinessException("任务不存在");
        }
    }

    @Override
    public List<TaskVO> taskTree(Long planId) {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<Task>()
                .eq(planId != null, Task::getProjectId, planId)
                .orderByAsc(Task::getSortOrder);
        List<Task> tasks = this.list(wrapper);
        return buildTaskTree(tasks);
    }

    private List<TaskVO> buildTaskTree(List<Task> tasks) {
        List<TaskVO> voList = new ArrayList<>();
        for (Task t : tasks) {
            TaskVO vo = new TaskVO();
            BeanUtils.copyProperties(t, vo);
            vo.setChildren(new ArrayList<>());
            voList.add(vo);
        }
        Map<Long, TaskVO> idMap = voList.stream().collect(Collectors.toMap(TaskVO::getId, v -> v));
        List<TaskVO> roots = new ArrayList<>();
        for (TaskVO vo : voList) {
            Long pid = vo.getParentId();
            if (pid == null || pid == 0L || !idMap.containsKey(pid)) {
                roots.add(vo);
            } else {
                idMap.get(pid).getChildren().add(vo);
            }
        }
        return roots;
    }
}
