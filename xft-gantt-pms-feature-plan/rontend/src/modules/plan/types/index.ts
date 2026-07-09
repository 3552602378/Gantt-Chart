export interface Plan {
  id: number
  name: string
  description: string
  startDate: string
  endDate: string
  status: PlanStatus
  progress: number
  createBy: string
  createTime: string
  updateTime: string
}

export enum PlanStatus {
  DRAFT = 0,
  IN_PROGRESS = 1,
  COMPLETED = 2,
  ARCHIVED = 3,
}

export interface PageResult<T> {
  records: T[]
  total: number
  size: number
  current: number
  pages: number
}

export interface PlanQuery {
  name?: string
  status?: PlanStatus
  page?: number
  size?: number
}

export interface PlanCreateDTO {
  name: string
  description?: string
  startDate: string
  endDate: string
  status?: PlanStatus
  progress?: number
}

export interface PlanUpdateDTO {
  id: number
  name?: string
  description?: string
  startDate?: string
  endDate?: string
  status?: PlanStatus
  progress?: number
}
