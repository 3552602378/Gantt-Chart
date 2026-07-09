import { get, post, put, del } from '@/common/utils/request'
import type { Plan, PlanQuery, PlanCreateDTO, PlanUpdateDTO, PageResult } from '@/modules/plan/types'

export function getPlanListApi(params: PlanQuery) {
  return get<PageResult<Plan>>('/plan/list', params)
}

export function getPlanByIdApi(id: number) {
  return get<Plan>(`/plan/${id}`)
}

export function createPlanApi(data: PlanCreateDTO) {
  return post<Plan>('/plan', data)
}

export function updatePlanApi(data: PlanUpdateDTO) {
  return put<Plan>('/plan', data)
}

export function deletePlanApi(id: number) {
  return del<void>(`/plan/${id}`)
}
