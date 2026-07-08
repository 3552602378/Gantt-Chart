import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/message',
    name: 'Message',
    component: () => import('@/modules/message/views/MessageView.vue'),
    meta: { title: '消息中心' },
  },
]

export default routes
