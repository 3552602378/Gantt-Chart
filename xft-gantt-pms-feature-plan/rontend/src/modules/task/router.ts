import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/task',
    name: 'Task',
    redirect: '/task/list',
    children: [
      {
        path: 'list',
        name: 'TaskList',
        component: () => import('@/modules/task/views/TaskManage.vue'),
        meta: { title: '任务管理' },
      },
    ],
  },
]

export default routes
