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
          <el-input v-model="queryForm.name" placeholder="请输入" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="全部" clearable style="width: 140px">
            <el-option label="草稿" :value="0" />
            <el-option label="进行中" :value="1" />
            <el-option label="已完成" :value="2" />
            <el-option label="已归档" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="planStore.planList" v-loading="planStore.loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="计划名称" min-width="150" />
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column prop="progress" label="进度" width="140">
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

      <div class="pagination">
        <el-pagination
          v-model:current-page="planStore.page"
          v-model:page-size="planStore.size"
          :total="planStore.total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @current-change="fetchList"
          @size-change="onSizeChange"
        />
      </div>
    </el-card>

    <PlanFormDialog
      v-model="dialogVisible"
      :plan="currentPlan"
      @success="fetchList"
    />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { usePlanStore } from '@/modules/plan/store/planStore'
import { PlanStatus } from '@/modules/plan/types'
import type { Plan } from '@/modules/plan/types'
import PlanFormDialog from '@/modules/plan/components/PlanFormDialog.vue'

const planStore = usePlanStore()
const queryForm = reactive({ name: '', status: undefined as PlanStatus | undefined })
const dialogVisible = ref(false)
const currentPlan = ref<Plan | null>(null)

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

function fetchList() {
  planStore.fetchPlanList({ ...queryForm })
}

function handleSearch() {
  planStore.page = 1
  fetchList()
}

function handleReset() {
  queryForm.name = ''
  queryForm.status = undefined
  planStore.page = 1
  fetchList()
}

function onSizeChange() {
  planStore.page = 1
  fetchList()
}

function handleCreate() {
  currentPlan.value = null
  dialogVisible.value = true
}

function handleEdit(row: Plan) {
  currentPlan.value = row
  dialogVisible.value = true
}

function handleDelete(id: number) {
  planStore.deletePlan(id)
}

onMounted(() => {
  fetchList()
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

.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}
</style>
