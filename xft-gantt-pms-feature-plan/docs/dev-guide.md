# 开发规范文档

---

## 1. 后端规范

### 1.1 包结构

```
com.gantt
├── common/
│   ├── config/        # 全局配置（MyBatis-Plus、Sa-Token、跨域）
│   ├── entity/        # 公共基类（BaseEntity）
│   ├── exception/     # 异常定义 + 全局处理器
│   └── result/        # 统一响应封装
└── modules/
    └── {module}/
        ├── controller/
        ├── service/
        │   └── impl/
        ├── mapper/
        ├── entity/
        └── dto/
```

### 1.2 命名规范

| 类型 | 规则 | 示例 |
|------|------|------|
| 类名 | PascalCase | `UserService`、`PlanController` |
| 方法名 | camelCase | `getUserById`、`createPlan` |
| 常量 | UPPER_SNAKE | `MAX_PAGE_SIZE` |
| 表名 | 模块_业务 snake_case | `sys_user`、`plan_task` |
| 列名 | snake_case | `create_time`、`user_id` |
| DTO | 业务+DTO | `UserLoginDTO`、`PlanCreateDTO` |

### 1.3 接口规范

| 项目 | 规则 |
|------|------|
| 响应封装 | 所有 Controller 方法返回 `Result<T>` |
| 参数校验 | 使用 `@Valid` + Jakarta Validation |
| 异常抛出 | 业务异常使用 `throw new BusinessException(...)` |
| 权限控制 | 使用 `@SaCheckPermission("资源标识")` |
| 日志 | 使用 `@Slf4j`，异常必须 `log.error(...)` 打印堆栈 |

---

## 2. 前端规范

### 2.1 目录结构

```
src/
├── common/            # 全局公共（跨模块复用）
│   ├── components/
│   ├── composables/   # 组合式函数
│   ├── utils/         # 工具函数
│   └── styles/
├── layouts/
├── router/
├── stores/            # 全局 Pinia
└── modules/
    └── {module}/
        ├── views/     # 页面
        ├── components/# 模块私有组件
        ├── api/       # 接口封装
        ├── store/     # 模块状态
        └── types/     # TS 类型
```

### 2.2 命名规范

| 类型 | 规则 | 示例 |
|------|------|------|
| 组件文件 | PascalCase.vue | `PlanManage.vue`、`UserTable.vue` |
| 工具文件 | camelCase.ts | `request.ts`、`formatDate.ts` |
| Store | camelCase + Store 后缀 | `usePlanStore`、`useUserStore` |
| API 函数 | camelCase + Api 后缀 | `getPlanListApi`、`createTaskApi` |
| 路由 name | PascalCase | `PlanList`、`TaskManage` |
| CSS 类名 | kebab-case | `.plan-manage`、`.card-header` |

### 2.3 API 封装规范

每个模块的 `api/index.ts` 通过 `@/common/utils/request` 的泛型方法调用：

```ts
import { get, post, put, del } from '@/common/utils/request'
import type { Plan } from '@/modules/plan/types'

export function getPlanListApi(params: object) {
  return get<Plan[]>('/plan/list', params)
}

export function createPlanApi(data: object) {
  return post<Plan>('/plan', data)
}
```

### 2.4 Store 规范

使用 Pinia Setup Store 模式，与 Composition API 一致：

```ts
export const usePlanStore = defineStore('plan', () => {
  const planList = ref<Plan[]>([])
  const loading = ref(false)

  async function fetchPlanList() { ... }

  return { planList, loading, fetchPlanList }
})
```

---

## 3. Git 分支策略

| 分支 | 用途 | 保护规则 |
|------|------|---------|
| `main` | 生产分支 | 禁止直推，必须 PR + Review |
| `dev` | 开发集成 | 禁止直推，必须 PR + Review |
| `feature/*` | 功能分支 | 命名：`feature/模块-功能-姓名首字母` |
| `hotfix/*` | 紧急修复 | 从 main 拉出，修完合回 main + dev |

**分支命名示例：**

```
feature/plan-gantt-drag-cgl
feature/task-crud-lgf
feature/system-auth-htq
hotfix/login-timeout-htq
```
