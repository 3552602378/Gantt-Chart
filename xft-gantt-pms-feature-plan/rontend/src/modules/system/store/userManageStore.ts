import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { User, UserQuery, UserCreate, UserUpdate, Role } from '@/modules/system/types'
import {
  getUserListApi, createUserApi, updateUserApi, deleteUserApi, updateUserStatusApi, resetUserRolesApi,
  getAllRolesApi,
} from '@/modules/system/api'
import { ElMessage } from 'element-plus'

export const useUserManageStore = defineStore('userManage', () => {
  const userList = ref<User[]>([])
  const total = ref(0)
  const loading = ref(false)
  const roleList = ref<Role[]>([])

  async function fetchUserList(params: UserQuery) {
    loading.value = true
    try {
      const res = await getUserListApi(params)
      userList.value = res.data.records
      total.value = res.data.total
    } finally {
      loading.value = false
    }
  }

  async function fetchRoleList() {
    const res = await getAllRolesApi()
    roleList.value = res.data || []
  }

  async function createUser(data: UserCreate) {
    await createUserApi(data)
    ElMessage.success('创建成功')
  }

  async function updateUser(data: UserUpdate) {
    await updateUserApi(data)
    ElMessage.success('更新成功')
  }

  async function deleteUser(id: number) {
    await deleteUserApi(id)
    ElMessage.success('删除成功')
  }

  async function toggleStatus(id: number, status: number) {
    await updateUserStatusApi(id, status)
    ElMessage.success(status === 1 ? '已启用' : '已禁用')
  }

  async function assignRoles(id: number, roleIds: number[]) {
    await resetUserRolesApi(id, roleIds)
    ElMessage.success('角色已更新')
  }

  return {
    userList, total, loading, roleList,
    fetchUserList, fetchRoleList, createUser, updateUser, deleteUser, toggleStatus, assignRoles,
  }
})
