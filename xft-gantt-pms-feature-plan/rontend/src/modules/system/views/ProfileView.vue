<template>
  <div class="profile-view">
    <el-card>
      <template #header>个人信息</template>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="用户名">{{ userStore.userInfo?.username }}</el-descriptions-item>
        <el-descriptions-item label="昵称">{{ userStore.userInfo?.nickname }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ userStore.userInfo?.email || '-' }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/stores/userStore'

const userStore = useUserStore()
</script>
