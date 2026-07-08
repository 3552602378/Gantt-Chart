import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { User } from '@/modules/system/types'
import { getUserListApi } from '@/modules/system/api'

export const useUserManageStore = defineStore('userManage', () => {
  const userList = ref<User[]>([])
  const loading = ref(false)

  async function fetchUserList(params?: object) {
    loading.value = true
    try {
      const res = await getUserListApi(params)
      userList.value = res.data
    } finally {
      loading.value = false
    }
  }

  return { userList, loading, fetchUserList }
})
