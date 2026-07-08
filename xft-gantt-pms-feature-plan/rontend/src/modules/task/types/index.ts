export interface Task {
  id: number
  planId: number
  name: string
  description: string
  startDate: string
  endDate: string
  assignee: string
  priority: TaskPriority
  status: TaskStatus
  progress: number
  createTime: string
}

export enum TaskPriority {
  LOW = 0,
  MEDIUM = 1,
  HIGH = 2,
  URGENT = 3,
}

export enum TaskStatus {
  TODO = 0,
  IN_PROGRESS = 1,
  DONE = 2,
}

export interface TaskQuery {
  planId?: number
  name?: string
  status?: TaskStatus
  pageNum?: number
  pageSize?: number
}
