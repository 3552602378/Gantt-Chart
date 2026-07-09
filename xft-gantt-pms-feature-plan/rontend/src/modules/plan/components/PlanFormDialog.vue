<template>
  <el-dialog
    v-model="visible"
    :title="isEdit ? '编辑计划' : '新建计划'"
    width="520px"
    @closed="onClosed"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="计划名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入计划名称" maxlength="64" />
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" type="textarea" :rows="2" maxlength="256" />
      </el-form-item>
      <el-form-item label="起止日期" prop="dateRange">
        <el-date-picker
          v-model="form.dateRange"
          type="daterange"
          value-format="YYYY-MM-DD"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" placeholder="请选择" style="width: 100%">
          <el-option label="草稿" :value="0" />
          <el-option label="进行中" :value="1" />
          <el-option label="已完成" :value="2" />
          <el-option label="已归档" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="进度" prop="progress">
        <el-input-number v-model="form.progress" :min="0" :max="100" :step="5" style="width: 100%" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :loading="submitting" @click="handleSubmit">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { usePlanStore } from '@/modules/plan/store/planStore'
import type { Plan, PlanCreateDTO, PlanUpdateDTO, PlanStatus } from '@/modules/plan/types'

const props = defineProps<{ modelValue: boolean; plan: Plan | null }>()
const emit = defineEmits<{
  (e: 'update:modelValue', val: boolean): void
  (e: 'success'): void
}>()

const planStore = usePlanStore()
const formRef = ref<FormInstance>()
const submitting = ref(false)

const visible = ref(props.modelValue)
watch(() => props.modelValue, (v) => (visible.value = v))
watch(visible, (v) => emit('update:modelValue', v))

const isEdit = ref(false)
const form = reactive({
  id: undefined as number | undefined,
  name: '',
  description: '',
  dateRange: [] as string[],
  status: 0 as PlanStatus,
  progress: 0,
})

const rules: FormRules = {
  name: [{ required: true, message: '请输入计划名称', trigger: 'blur' }],
  dateRange: [
    {
      required: true,
      validator: (_rule, val, cb) => {
        if (!val || val.length !== 2 || !val[0] || !val[1]) {
          cb(new Error('请选择起止日期'))
        } else {
          cb()
        }
      },
      trigger: 'change',
    },
  ],
}

watch(
  () => props.modelValue,
  (open) => {
    if (!open) return
    resetForm()
    if (props.plan) {
      isEdit.value = true
      form.id = props.plan.id
      form.name = props.plan.name
      form.description = props.plan.description || ''
      form.dateRange = [props.plan.startDate, props.plan.endDate]
      form.status = props.plan.status
      form.progress = props.plan.progress
    } else {
      isEdit.value = false
    }
  },
)

function resetForm() {
  form.id = undefined
  form.name = ''
  form.description = ''
  form.dateRange = []
  form.status = 0
  form.progress = 0
  formRef.value?.clearValidate()
}

function onClosed() {
  resetForm()
}

async function handleSubmit() {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    if (isEdit.value && form.id) {
      const dto: PlanUpdateDTO = {
        id: form.id,
        name: form.name,
        description: form.description,
        startDate: form.dateRange[0],
        endDate: form.dateRange[1],
        status: form.status,
        progress: form.progress,
      }
      await planStore.updatePlan(dto)
    } else {
      const dto: PlanCreateDTO = {
        name: form.name,
        description: form.description,
        startDate: form.dateRange[0],
        endDate: form.dateRange[1],
        status: form.status,
        progress: form.progress,
      }
      await planStore.createPlan(dto)
    }
    emit('success')
    visible.value = false
  } finally {
    submitting.value = false
  }
}
</script>
