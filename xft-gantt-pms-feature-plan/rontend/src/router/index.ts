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
  // path 去掉开头的 /，作为 DefaultLayout 的相对子路由
  const relativePath = menu.path.replace(/^\//, '')
  return {
    path: relativePath,
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

function firstMenuPath(menus: MenuRoute[]): string | null {
  for (const m of menus) {
    // 优先进入子菜单找叶子节点（目录 type=0 无 component，不应作为重定向目标）
    if (m.children && m.children.length) {
      const child = firstMenuPath(m.children)
      if (child) return child
    }
    // 叶子菜单（有 path 且非目录）才返回
    if (m.path && m.component) return m.path
  }
  return null
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
    const wasAdded = dynamicRoutesAdded
    await ensureDynamicRoutes()
    const userStore = useUserStore()
    // 访问根路径 / 时重定向到第一个菜单项，避免落到空 NotFound
    // 排除目标本身就是 / 的情况，防止死循环
    if (to.path === '/') {
      const target = firstMenuPath(userStore.menuRoutes)
      if (target && target !== '/') {
        next({ path: target, replace: true })
        return
      }
    }
    // 仅在本次新加了动态路由后才需要 replace 重新匹配，否则直接放行避免无限重定向
    if (wasAdded) {
      next()
    } else {
      next({ ...to, replace: true })
    }
  } catch (e) {
    const userStore = useUserStore()
    userStore.logout()
    next('/login')
  }
})

export const __resetDynamicRoutes = () => { dynamicRoutesAdded = false }

export default router
