import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { post, get } from '@/common/utils/request'
import type { MenuRoute } from '@/modules/system/types'
import { getMenuRoutesApi } from '@/modules/system/api'

interface UserInfo {
  id: number
  username: string
  nickname: string
  email: string
  avatar: string
}

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<UserInfo | null>(null)
  const menuRoutes = ref<MenuRoute[]>([])
  const permissions = ref<string[]>([])

  const isLoggedIn = computed(() => !!token.value)

  async function login(username: string, password: string) {
    const res = await post<string>('/auth/login', { username, password })
    token.value = res.data
    localStorage.setItem('token', res.data)
  }

  async function fetchUserInfo() {
    const res = await get<UserInfo>('/auth/me')
    userInfo.value = res.data
  }

  async function fetchMenuRoutes() {
    const res = await getMenuRoutesApi()
    menuRoutes.value = res.data || []
    return res.data || []
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    menuRoutes.value = []
    permissions.value = []
    localStorage.removeItem('token')
  }

  return { token, userInfo, menuRoutes, permissions, isLoggedIn, login, fetchUserInfo, fetchMenuRoutes, logout }
})
