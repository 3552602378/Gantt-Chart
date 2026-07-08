import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/plan',
    name: 'Plan',
    redirect: '/plan/list',
    children: [
      {
        path: 'list',
        name: 'PlanList',
        component: () => import('@/modules/plan/views/PlanManage.vue'),
        meta: { title: '计划管理' },
      },
    ],
  },
]

export default routes
