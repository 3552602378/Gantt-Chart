import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/system',
    name: 'System',
    redirect: '/system/user',
    children: [
      {
        path: 'user',
        name: 'SystemUser',
        component: () => import('@/modules/system/views/UserManage.vue'),
        meta: { title: '用户管理' },
      },
    ],
  },
]

export default routes
