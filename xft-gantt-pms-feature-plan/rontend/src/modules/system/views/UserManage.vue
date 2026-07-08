<template>
  <div class="user-manage">
    <el-card>
      <template #header>
        <span>用户管理</span>
      </template>
      <el-table :data="userStore.userList" v-loading="userStore.loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useUserManageStore } from '@/modules/system/store/userManageStore'

const userStore = useUserManageStore()

onMounted(() => {
  userStore.fetchUserList()
})
</script>
