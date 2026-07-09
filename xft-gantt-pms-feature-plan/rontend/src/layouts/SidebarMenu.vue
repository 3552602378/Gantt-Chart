<template>
  <template v-for="menu in menus" :key="menu.id">
    <el-sub-menu
      v-if="menu.children && menu.children.length"
      :index="menu.path"
    >
      <template #title>
        <el-icon v-if="menu.icon"><component :is="menu.icon" /></el-icon>
        <span>{{ menu.name }}</span>
      </template>
      <SidebarMenu :menus="menu.children" />
    </el-sub-menu>
    <el-menu-item v-else :index="menu.path">
      <el-icon v-if="menu.icon"><component :is="menu.icon" /></el-icon>
      <template #title>{{ menu.name }}</template>
    </el-menu-item>
  </template>
</template>

<script setup lang="ts">
import type { MenuRoute } from '@/modules/system/types'

defineProps<{ menus: MenuRoute[] }>()
</script>
