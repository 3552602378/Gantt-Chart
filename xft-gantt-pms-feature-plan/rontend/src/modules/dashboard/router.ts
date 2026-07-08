import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('@/modules/dashboard/views/DashboardView.vue'),
    meta: { title: '项目看板' },
  },
]

export default routes
