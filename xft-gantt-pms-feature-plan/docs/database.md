# 数据库设计文档

> 最后更新：2026-06-30 | 维护人：霍泰企

---

## 1. 设计规范

| 规范项 | 说明 |
|--------|------|
| 字符集 | `utf8mb4` / `utf8mb4_general_ci` |
| 引擎 | InnoDB |
| 主键 | `BIGINT AUTO_INCREMENT` |
| 命名 | 表名 `模块_业务`（如 `sys_user`、`plan_task`），列名 `snake_case` |
| 通用字段 | 每张表必须包含 `create_by`、`create_time`、`update_by`、`update_time`、`deleted` |
| 逻辑删除 | `deleted`：0=未删，1=已删（MyBatis-Plus `@TableLogic`） |

---

## 2. 表结构

### 2.1 sys_user（系统用户表）

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| username | VARCHAR(64) | UNIQUE, NOT NULL | 用户名 |
| password | VARCHAR(128) | NOT NULL | 密码（BCrypt 加密） |
| nickname | VARCHAR(64) | | 昵称 |
| email | VARCHAR(128) | | 邮箱 |
| phone | VARCHAR(20) | | 手机号 |
| status | TINYINT | DEFAULT 1 | 状态：1-启用，0-禁用 |
| create_by | VARCHAR(64) | | 创建人 |
| create_time | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| update_by | VARCHAR(64) | | 更新人 |
| update_time | DATETIME | ON UPDATE CURRENT_TIMESTAMP | 更新时间 |
| deleted | TINYINT | DEFAULT 0 | 逻辑删除 |

```sql
CREATE TABLE sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    username    VARCHAR(64)  NOT NULL,
    password    VARCHAR(128) NOT NULL,
    nickname    VARCHAR(64)  DEFAULT NULL,
    email       VARCHAR(128) DEFAULT NULL,
    phone       VARCHAR(20)  DEFAULT NULL,
    status      TINYINT      DEFAULT 1,
    create_by   VARCHAR(64)  DEFAULT NULL,
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  DEFAULT NULL,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT      DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
```

### 2.2 sys_role（角色表）

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK | 主键 |
| role_key | VARCHAR(64) | UNIQUE, NOT NULL | 角色标识 |
| role_name | VARCHAR(64) | NOT NULL | 角色名称 |
| description | VARCHAR(256) | | 描述 |
| status | TINYINT | DEFAULT 1 | 状态 |
| create_by ~ deleted | | | 通用字段（同上） |

```sql
CREATE TABLE sys_role (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    role_key    VARCHAR(64)  NOT NULL,
    role_name   VARCHAR(64)  NOT NULL,
    description VARCHAR(256) DEFAULT NULL,
    status      TINYINT      DEFAULT 1,
    create_by   VARCHAR(64)  DEFAULT NULL,
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  DEFAULT NULL,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT      DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_key (role_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';
```

### 2.3 sys_user_role（用户角色关联表）

```sql
CREATE TABLE sys_user_role (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    user_id     BIGINT NOT NULL COMMENT '用户ID',
    role_id     BIGINT NOT NULL COMMENT '角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';
```

### 2.4 plan_project（计划/项目表）

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK | 主键 |
| name | VARCHAR(128) | NOT NULL | 计划名称 |
| description | TEXT | | 描述 |
| start_date | DATE | NOT NULL | 开始日期 |
| end_date | DATE | NOT NULL | 结束日期 |
| status | TINYINT | DEFAULT 0 | 0-草稿，1-进行中，2-已完成，3-已归档 |
| progress | INT | DEFAULT 0 | 进度百分比 0-100 |
| create_by ~ deleted | | | 通用字段 |

```sql
CREATE TABLE plan_project (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(128) NOT NULL,
    description TEXT,
    start_date  DATE         NOT NULL,
    end_date    DATE         NOT NULL,
    status      TINYINT      DEFAULT 0,
    progress    INT          DEFAULT 0,
    create_by   VARCHAR(64)  DEFAULT NULL,
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  DEFAULT NULL,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT      DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划项目表';
```

### 2.5 plan_task（任务表）

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK | 主键 |
| project_id | BIGINT | NOT NULL, FK | 所属计划ID |
| parent_id | BIGINT | DEFAULT 0 | 父任务ID（0=顶级） |
| name | VARCHAR(128) | NOT NULL | 任务名称 |
| description | TEXT | | 描述 |
| assignee | VARCHAR(64) | | 负责人 |
| start_date | DATE | NOT NULL | 开始日期 |
| end_date | DATE | NOT NULL | 结束日期 |
| priority | TINYINT | DEFAULT 1 | 0-低，1-中，2-高，3-紧急 |
| status | TINYINT | DEFAULT 0 | 0-待办，1-进行中，2-已完成 |
| progress | INT | DEFAULT 0 | 进度 0-100 |
| sort_order | INT | DEFAULT 0 | 排序 |
| create_by ~ deleted | | | 通用字段 |

```sql
CREATE TABLE plan_task (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    project_id  BIGINT       NOT NULL,
    parent_id   BIGINT       DEFAULT 0,
    name        VARCHAR(128) NOT NULL,
    description TEXT,
    assignee    VARCHAR(64)  DEFAULT NULL,
    start_date  DATE         NOT NULL,
    end_date    DATE         NOT NULL,
    priority    TINYINT      DEFAULT 1,
    status      TINYINT      DEFAULT 0,
    progress    INT          DEFAULT 0,
    sort_order  INT          DEFAULT 0,
    create_by   VARCHAR(64)  DEFAULT NULL,
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_by   VARCHAR(64)  DEFAULT NULL,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted     TINYINT      DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_project_id (project_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';
```

---

## 3. ER 关系

```
sys_user ──M:N── sys_role        (通过 sys_user_role 关联)
plan_project ──1:N── plan_task   (通过 project_id)
plan_task ──1:N── plan_task      (通过 parent_id，自引用实现子任务)
```

---

## 4. 待扩展表

| 表名 | 说明 | 优先级 |
|------|------|--------|
| `plan_task_dependency` | 任务依赖关系（FS/FF/SS/SF） | P1 |
| `sys_permission` | 权限表 | P2 |
| `sys_role_permission` | 角色权限关联 | P2 |
| `sys_log` | 操作日志 | P3 |
| `plan_task_comment` | 任务评论 | P3 |
