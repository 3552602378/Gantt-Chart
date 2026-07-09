package com.gantt.modules.task.vo;

import com.gantt.modules.task.entity.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskVO extends Task {

    private List<TaskVO> children;
}
