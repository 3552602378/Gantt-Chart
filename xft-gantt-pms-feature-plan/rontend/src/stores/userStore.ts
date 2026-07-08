import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { post, get } from '@/common/utils/request'

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

  function logout() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
  }

  return { token, userInfo, isLoggedIn, login, fetchUserInfo, logout }
})
