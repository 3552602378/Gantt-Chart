import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import type { MenuRoute } from '@/modules/system/types'

const modules = import.meta.glob<{ default: any }>('../modules/**/views/*.vue')

function loadComponent(component: string) {
  const path = `../modules/${component}.vue`
  return modules[path]
}

function toRouteRecord(menu: MenuRoute): RouteRecordRaw | null {
  if (!menu.component) return null
  const component = loadComponent(menu.component)
  if (!component) return null
  return {
    path: menu.path,
    name: menu.path.replace(/\//g, '-').slice(1),
    component,
    meta: { title: menu.name, icon: menu.icon, permission: menu.path },
  }
}

function flattenMenuRoutes(menus: MenuRoute[]): RouteRecordRaw[] {
  const result: RouteRecordRaw[] = []
  for (const m of menus) {
    const record = toRouteRecord(m)
    if (record) result.push(record)
    if (m.children && m.children.length) {
      result.push(...flattenMenuRoutes(m.children))
    }
  }
  return result
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
      name: 'DefaultLayout',
      component: () => import('@/layouts/DefaultLayout.vue'),
      redirect: '/system/user',
      children: [],
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import('@/common/views/NotFound.vue'),
    },
  ],
})

let dynamicRoutesAdded = false

async function ensureDynamicRoutes() {
  if (dynamicRoutesAdded) return
  const userStore = useUserStore()
  const menus = await userStore.fetchMenuRoutes()
  const records = flattenMenuRoutes(menus)
  for (const r of records) {
    router.addRoute('DefaultLayout', r)
  }
  dynamicRoutesAdded = true
}

router.beforeEach(async (to, _from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    next()
    return
  }
  if (!token) {
    next('/login')
    return
  }
  try {
    await ensureDynamicRoutes()
    next({ ...to, replace: true })
  } catch (e) {
    const userStore = useUserStore()
    userStore.logout()
    next('/login')
  }
})

export const __resetDynamicRoutes = () => { dynamicRoutesAdded = false }

export default router
