<template>
  <div class="user-manage">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button type="primary" @click="openCreate">新建用户</el-button>
        </div>
      </template>

      <el-form :inline="true" class="search-form">
        <el-form-item label="用户名">
          <el-input v-model="query.username" placeholder="用户名" clearable @keyup.enter="loadList" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="query.nickname" placeholder="昵称" clearable @keyup.enter="loadList" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable style="width: 120px">
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadList">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="store.userList" v-loading="store.loading" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickname" label="昵称" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openEdit(row as User)">编辑</el-button>
            <el-button link type="primary" @click="openRoles(row as User)">分配角色</el-button>
            <el-button link :type="row.status === 1 ? 'warning' : 'success'" @click="onToggle(row as User)">
              {{ row.status === 1 ? '禁用' : '启用' }}
            </el-button>
            <el-button link type="danger" @click="onDelete(row as User)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        class="pager"
        v-model:current-page="query.page"
        v-model:page-size="query.size"
        :total="store.total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next"
        @size-change="loadList"
        @current-change="loadList"
      />
    </el-card>

    <el-dialog v-model="formVisible" :title="formMode === 'create' ? '新建用户' : '编辑用户'" width="520px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="formMode === 'edit'" />
        </el-form-item>
        <el-form-item v-if="formMode === 'create'" label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="rolesVisible" title="分配角色" width="420px">
      <el-checkbox-group v-model="selectedRoleIds">
        <el-checkbox v-for="r in store.roleList" :key="r.id" :value="r.id" :label="r.roleName" />
      </el-checkbox-group>
      <template #footer>
        <el-button @click="rolesVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="submitRoles">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { useUserManageStore } from '@/modules/system/store/userManageStore'
import type { User, UserQuery } from '@/modules/system/types'

const store = useUserManageStore()

const query = reactive<UserQuery>({ page: 1, size: 10 })
const formVisible = ref(false)
const formMode = ref<'create' | 'edit'>('create')
const formRef = ref<FormInstance>()
const submitting = ref(false)
const rolesVisible = ref(false)
const selectedRoleIds = ref<number[]>([])
const currentUserId = ref<number>(0)

const form = reactive({
  id: 0,
  username: '',
  password: '',
  nickname: '',
  email: '',
  phone: '',
})

const rules: FormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  email: [{ type: 'email', message: '邮箱格式不正确', trigger: 'blur' }],
}

async function loadList() {
  await store.fetchUserList(query)
}

function resetQuery() {
  query.username = undefined
  query.nickname = undefined
  query.status = undefined
  query.page = 1
  loadList()
}

function openCreate() {
  formMode.value = 'create'
  Object.assign(form, { id: 0, username: '', password: '', nickname: '', email: '', phone: '' })
  formVisible.value = true
}

function openEdit(row: User) {
  formMode.value = 'edit'
  Object.assign(form, { id: row.id, username: row.username, password: '', nickname: row.nickname, email: row.email || '', phone: row.phone || '' })
  formVisible.value = true
}

async function submitForm() {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    if (formMode.value === 'create') {
      await store.createUser({
        username: form.username, password: form.password,
        nickname: form.nickname, email: form.email, phone: form.phone,
      })
    } else {
      await store.updateUser({
        id: form.id, nickname: form.nickname, email: form.email, phone: form.phone,
      })
    }
    formVisible.value = false
    loadList()
  } finally {
    submitting.value = false
  }
}

async function onDelete(row: User) {
  await ElMessageBox.confirm(`确定删除用户 ${row.username}？`, '提示', { type: 'warning' })
  await store.deleteUser(row.id)
  loadList()
}

async function onToggle(row: User) {
  const next = row.status === 1 ? 0 : 1
  await store.toggleStatus(row.id, next)
  loadList()
}

async function openRoles(row: User) {
  currentUserId.value = row.id
  selectedRoleIds.value = []
  if (!store.roleList.length) {
    await store.fetchRoleList()
  }
  rolesVisible.value = true
}

async function submitRoles() {
  submitting.value = true
  try {
    await store.assignRoles(currentUserId.value, selectedRoleIds.value)
    rolesVisible.value = false
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadList()
})
</script>

<style scoped lang="scss">
.user-manage {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .search-form {
    margin-bottom: 12px;
  }
  .pager {
    margin-top: 12px;
    justify-content: flex-end;
  }
}
</style>
