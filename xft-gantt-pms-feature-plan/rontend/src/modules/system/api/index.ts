import { get } from '@/common/utils/request'
import type { User } from '@/modules/system/types'

export function getUserListApi(params?: object) {
  return get<User[]>('/system/user/list', params)
}

export function getUserByIdApi(id: number) {
  return get<User>(`/system/user/${id}`)
}
