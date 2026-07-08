<template>
  <div class="task-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>任务管理</span>
          <el-button type="primary" @click="handleCreate">新建任务</el-button>
        </div>
      </template>

      <el-table :data="taskStore.taskList" v-loading="taskStore.loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="任务名称" min-width="150" />
        <el-table-column prop="assignee" label="负责人" width="100" />
        <el-table-column prop="priority" label="优先级" width="90">
          <template #default="{ row }">
            <el-tag :type="priorityType(row.priority)">{{ priorityLabel(row.priority) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column prop="progress" label="进度" width="100">
          <template #default="{ row }">
            <el-progress :percentage="row.progress" :stroke-width="6" />
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ statusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="taskStore.deleteTask(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useTaskStore } from '@/modules/task/store/taskStore'
import { TaskPriority, TaskStatus } from '@/modules/task/types'

const taskStore = useTaskStore()

function priorityLabel(p: TaskPriority) {
  return { [TaskPriority.LOW]: '低', [TaskPriority.MEDIUM]: '中', [TaskPriority.HIGH]: '高', [TaskPriority.URGENT]: '紧急' }[p]
}
function priorityType(p: TaskPriority): 'info' | 'primary' | 'warning' | 'danger' {
  return { [TaskPriority.LOW]: 'info', [TaskPriority.MEDIUM]: 'primary', [TaskPriority.HIGH]: 'warning', [TaskPriority.URGENT]: 'danger' }[p] as 'info' | 'primary' | 'warning' | 'danger'
}
function statusLabel(s: TaskStatus) {
  return { [TaskStatus.TODO]: '待办', [TaskStatus.IN_PROGRESS]: '进行中', [TaskStatus.DONE]: '已完成' }[s]
}
function statusType(s: TaskStatus): 'info' | 'primary' | 'success' {
  return { [TaskStatus.TODO]: 'info', [TaskStatus.IN_PROGRESS]: 'primary', [TaskStatus.DONE]: 'success' }[s] as 'info' | 'primary' | 'success'
}

function handleCreate() { /* TODO */ }
function handleEdit(_row: any) { /* TODO */ }

onMounted(() => {
  taskStore.fetchTaskList()
})
</script>

<style scoped lang="scss">
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
