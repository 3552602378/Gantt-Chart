import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const modules = import.meta.glob<{ default: RouteRecordRaw[] }>(
  '../modules/*/router.ts',
  { eager: true },
)

const moduleRoutes: RouteRecordRaw[] = []
for (const [, mod] of Object.entries(modules)) {
  if (mod.default) {
    moduleRoutes.push(...mod.default)
  }
}

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/modules/system/views/LoginView.vue'),
    },
    {
      path: '/',
      component: () => import('@/layouts/DefaultLayout.vue'),
      redirect: '/plan',
      children: moduleRoutes,
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import('@/common/views/NotFound.vue'),
    },
  ],
})

router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
