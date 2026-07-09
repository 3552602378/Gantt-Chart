import { get, post, put, del } from '@/common/utils/request'
import type { User, Page, UserQuery, UserCreate, UserUpdate, Role, MenuRoute } from '@/modules/system/types'

export function getUserListApi(params?: UserQuery) {
  return get<Page<User>>('/system/user/list', params)
}

export function createUserApi(data: UserCreate) {
  return post<User>('/system/user', data)
}

export function updateUserApi(data: UserUpdate) {
  return put<User>('/system/user', data)
}

export function deleteUserApi(id: number) {
  return del<void>(`/system/user/${id}`)
}

export function updateUserStatusApi(id: number, status: number) {
  return put<void>(`/system/user/${id}/status`, { status })
}

export function resetUserRolesApi(id: number, roleIds: number[]) {
  return put<void>(`/system/user/${id}/roles`, roleIds)
}

export function getMenuRoutesApi() {
  return get<MenuRoute[]>('/system/menu/routes')
}

export function getAllRolesApi() {
  return get<Role[]>('/system/role/list')
}
