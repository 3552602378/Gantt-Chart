import { get, post, put, del } from '@/common/utils/request'
import type { Plan, PlanQuery, PlanCreateDTO } from '@/modules/plan/types'

export function getPlanListApi(params: PlanQuery) {
  return get<Plan[]>('/plan/list', params)
}

export function getPlanByIdApi(id: number) {
  return get<Plan>(`/plan/${id}`)
}

export function createPlanApi(data: PlanCreateDTO) {
  return post<Plan>('/plan', data)
}

export function updatePlanApi(id: number, data: Partial<PlanCreateDTO>) {
  return put<Plan>(`/plan/${id}`, data)
}

export function deletePlanApi(id: number) {
  return del<void>(`/plan/${id}`)
}
