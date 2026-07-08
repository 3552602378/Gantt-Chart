import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Task, TaskQuery } from '@/modules/task/types'
import { getTaskListApi, createTaskApi, updateTaskApi, deleteTaskApi } from '@/modules/task/api'
import { ElMessage } from 'element-plus'

export const useTaskStore = defineStore('task', () => {
  const taskList = ref<Task[]>([])
  const loading = ref(false)

  async function fetchTaskList(query: TaskQuery = {}) {
    loading.value = true
    try {
      const res = await getTaskListApi(query)
      taskList.value = res.data
    } finally {
      loading.value = false
    }
  }

  async function createTask(data: Partial<Task>) {
    const res = await createTaskApi(data)
    ElMessage.success('创建成功')
    taskList.value.unshift(res.data)
    return res.data
  }

  async function updateTask(id: number, data: Partial<Task>) {
    const res = await updateTaskApi(id, data)
    ElMessage.success('更新成功')
    const idx = taskList.value.findIndex((t) => t.id === id)
    if (idx !== -1) taskList.value[idx] = res.data
    return res.data
  }

  async function deleteTask(id: number) {
    await deleteTaskApi(id)
    ElMessage.success('删除成功')
    taskList.value = taskList.value.filter((t) => t.id !== id)
  }

  return { taskList, loading, fetchTaskList, createTask, updateTask, deleteTask }
})
