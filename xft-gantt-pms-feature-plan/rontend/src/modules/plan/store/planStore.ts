import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Plan, PlanQuery, PlanCreateDTO, PlanUpdateDTO } from '@/modules/plan/types'
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
  const page = ref(1)
  const size = ref(10)

  async function fetchPlanList(query: PlanQuery = {}) {
    loading.value = true
    try {
      const params = { page: page.value, size: size.value, ...query }
      const res = await getPlanListApi(params)
      planList.value = res.data.records
      total.value = res.data.total
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
    await fetchPlanList()
    return res.data
  }

  async function updatePlan(data: PlanUpdateDTO) {
    const res = await updatePlanApi(data)
    ElMessage.success('更新成功')
    const idx = planList.value.findIndex((p) => p.id === data.id)
    if (idx !== -1) planList.value[idx] = res.data
    return res.data
  }

  async function deletePlan(id: number) {
    await deletePlanApi(id)
    ElMessage.success('删除成功')
    await fetchPlanList()
  }

  return {
    planList,
    currentPlan,
    loading,
    total,
    page,
    size,
    fetchPlanList,
    fetchPlanById,
    createPlan,
    updatePlan,
    deletePlan,
  }
})
