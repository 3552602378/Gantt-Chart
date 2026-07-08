<template>
  <div class="plan-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>计划管理</span>
          <el-button type="primary" @click="handleCreate">新建计划</el-button>
        </div>
      </template>

      <el-form inline :model="queryForm" class="query-form">
        <el-form-item label="计划名称">
          <el-input v-model="queryForm.name" placeholder="请输入" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="全部" clearable>
            <el-option label="草稿" :value="0" />
            <el-option label="进行中" :value="1" />
            <el-option label="已完成" :value="2" />
            <el-option label="已归档" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="planStore.planList" v-loading="planStore.loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="计划名称" min-width="150" />
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column prop="progress" label="进度" width="100">
          <template #default="{ row }">
            <el-progress :percentage="row.progress" :stroke-width="6" />
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusTagType(row.status)">{{ statusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-popconfirm title="确认删除?" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button link type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive } from 'vue'
import { usePlanStore } from '@/modules/plan/store/planStore'
import { PlanStatus } from '@/modules/plan/types'

const planStore = usePlanStore()
const queryForm = reactive({ name: '', status: undefined as PlanStatus | undefined })

function statusLabel(status: PlanStatus) {
  const map: Record<PlanStatus, string> = {
    [PlanStatus.DRAFT]: '草稿',
    [PlanStatus.IN_PROGRESS]: '进行中',
    [PlanStatus.COMPLETED]: '已完成',
    [PlanStatus.ARCHIVED]: '已归档',
  }
  return map[status] ?? '未知'
}

function statusTagType(status: PlanStatus): 'info' | 'primary' | 'success' | 'warning' {
  const map: Record<PlanStatus, 'info' | 'primary' | 'success' | 'warning'> = {
    [PlanStatus.DRAFT]: 'info',
    [PlanStatus.IN_PROGRESS]: 'primary',
    [PlanStatus.COMPLETED]: 'success',
    [PlanStatus.ARCHIVED]: 'warning',
  }
  return map[status] ?? 'info'
}

function handleSearch() {
  planStore.fetchPlanList({ ...queryForm })
}

function handleCreate() {
  // TODO: open dialog
}

function handleEdit(_row: any) {
  // TODO: open dialog
}

function handleDelete(id: number) {
  planStore.deletePlan(id)
}

onMounted(() => {
  planStore.fetchPlanList()
})
</script>

<style scoped lang="scss">
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.query-form {
  margin-bottom: 16px;
}
</style>
