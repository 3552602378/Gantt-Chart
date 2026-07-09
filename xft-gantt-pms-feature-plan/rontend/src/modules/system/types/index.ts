export interface User {
  id: number
  username: string
  nickname: string
  email: string | null
  phone: string | null
  status: number
  createTime: string
}

export interface Page<T> {
  records: T[]
  total: number
  size: number
  current: number
  pages: number
}

export interface UserQuery {
  username?: string
  nickname?: string
  status?: number
  page?: number
  size?: number
}

export interface UserCreate {
  username: string
  password: string
  nickname?: string
  email?: string
  phone?: string
  roleIds?: number[]
}

export interface UserUpdate {
  id: number
  nickname?: string
  email?: string
  phone?: string
  status?: number
  roleIds?: number[]
}

export interface Role {
  id: number
  roleKey: string
  roleName: string
  status: number
}

export interface MenuRoute {
  id: number
  parentId: number
  name: string
  path: string
  component: string | null
  icon: string | null
  sort: number
  children: MenuRoute[] | null
}
