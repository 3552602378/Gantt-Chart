import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Plan, PlanQuery, PlanCreateDTO } from '@/modules/plan/types'
import {
  getPlanListApi,
  getPlanByIdApi,
  createPlanApi,
  updatePlanApi,
  deletePlanApi,
} from '@/modules/plan/api'
import { ElMessage } from 'element-plus'

export const usePlanStore = defineStore('plan', () => {
  const planList = ref<Plan[]>([])
  const currentPlan = ref<Plan | null>(null)
  const loading = ref(false)
  const total = ref(0)

  async function fetchPlanList(query: PlanQuery = {}) {
    loading.value = true
    try {
      const res = await getPlanListApi(query)
      planList.value = res.data
    } finally {
      loading.value = false
    }
  }

  async function fetchPlanById(id: number) {
    loading.value = true
    try {
      const res = await getPlanByIdApi(id)
      currentPlan.value = res.data
      return res.data
    } finally {
      loading.value = false
    }
  }

  async function createPlan(data: PlanCreateDTO) {
    const res = await createPlanApi(data)
    ElMessage.success('创建成功')
    planList.value.unshift(res.data)
    return res.data
  }

  async function updatePlan(id: number, data: Partial<PlanCreateDTO>) {
    const res = await updatePlanApi(id, data)
    ElMessage.success('更新成功')
    const idx = planList.value.findIndex((p) => p.id === id)
    if (idx !== -1) planList.value[idx] = res.data
    return res.data
  }

  async function deletePlan(id: number) {
    await deletePlanApi(id)
    ElMessage.success('删除成功')
    planList.value = planList.value.filter((p) => p.id !== id)
  }

  return {
    planList,
    currentPlan,
    loading,
    total,
    fetchPlanList,
    fetchPlanById,
    createPlan,
    updatePlan,
    deletePlan,
  }
})
