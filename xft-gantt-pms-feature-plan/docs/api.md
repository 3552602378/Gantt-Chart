# 接口文档

> 基础路径：`/api` | Content-Type：`application/json` | 认证方式：Sa-Token（Header `Authorization: Bearer {token}`）

---

## 通用响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

| code | 说明 |
|------|------|
| 200 | 成功 |
| 400 | 参数校验失败 |
| 401 | 未登录 / token 失效 |
| 403 | 无权限 |
| 405 | 请求方法不支持 |
| 500 | 业务异常 / 系统异常 |

---

## 一、认证模块 `/auth`

### 1.1 用户注册

```
POST /auth/register
```

**请求体：**

```json
{
  "username": "zhangsan",
  "password": "123456",
  "nickname": "张三",
  "email": "zhangsan@example.com",
  "phone": "13800138000"
}
```

**响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "zhangsan",
    "nickname": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138000",
    "status": 1,
    "createTime": "2026-06-30T10:00:00"
  }
}
```

**错误码：**

| code | message |
|------|---------|
| 500 | 用户名已存在 |

---

### 1.2 用户登录

```
POST /auth/login
```

**请求体：**

```json
{
  "username": "zhangsan",
  "password": "123456"
}
```

**响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9..."
}
```

> `data` 为 Sa-Token 生成的 `token` 字符串，后续请求放入 Header `Authorization: Bearer {token}`

**错误码：**

| code | message |
|------|---------|
| 500 | 用户名或密码错误 |
| 500 | 账号已被禁用 |

---

### 1.3 获取当前用户信息

```
GET /auth/me
```

**请求头：**

```
Authorization: Bearer {token}
```

**响应：**

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "zhangsan",
    "nickname": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138000",
    "status": 1,
    "createTime": "2026-06-30T10:00:00"
  }
}
```

**权限要求：** `@SaCheckPermission("user.info")`

---

## 二、计划模块 `/plan`

> 以下接口均为规划接口，需登录后调用。

### 2.1 获取计划列表

```
GET /plan/list?name=&status=&pageNum=1&pageSize=10
```

### 2.2 获取计划详情

```
GET /plan/{id}
```

### 2.3 创建计划

```
POST /plan
```

```json
{
  "name": "Q3 产品迭代",
  "description": "第三季度产品迭代计划",
  "startDate": "2026-07-01",
  "endDate": "2026-09-30"
}
```

### 2.4 更新计划

```
PUT /plan/{id}
```

### 2.5 删除计划

```
DELETE /plan/{id}
```

---

## 三、任务模块 `/task`

### 3.1 获取任务列表

```
GET /task/list?planId=&name=&status=&pageNum=1&pageSize=10
```

### 3.2 获取任务详情

```
GET /task/{id}
```

### 3.3 创建任务

```
POST /task
```

```json
{
  "projectId": 1,
  "parentId": 0,
  "name": "需求评审",
  "assignee": "zhangsan",
  "startDate": "2026-07-01",
  "endDate": "2026-07-05",
  "priority": 2
}
```

### 3.4 更新任务

```
PUT /task/{id}
```

### 3.5 删除任务

```
DELETE /task/{id}
```
