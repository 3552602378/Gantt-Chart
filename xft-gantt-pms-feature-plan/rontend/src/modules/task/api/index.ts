import { get, post, put, del } from '@/common/utils/request'
import type { Task, TaskQuery } from '@/modules/task/types'

export function getTaskListApi(params: TaskQuery) {
  return get<Task[]>('/task/list', params)
}

export function getTaskByIdApi(id: number) {
  return get<Task>(`/task/${id}`)
}

export function createTaskApi(data: Partial<Task>) {
  return post<Task>('/task', data)
}

export function updateTaskApi(id: number, data: Partial<Task>) {
  return put<Task>(`/task/${id}`, data)
}

export function deleteTaskApi(id: number) {
  return del<void>(`/task/${id}`)
}
