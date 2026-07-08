# Gantt Project - 团队开发指南

> **技术栈**：Spring Boot 3.2 + Java 17 + MyBatis-Plus + Sa-Token | Vue 3.4 + TypeScript + Vite 5 + Pinia + Element Plus

---

## 一、环境要求

| 依赖 | 最低版本 | 说明 |
|------|---------|------|
| JDK | 17+ | 推荐 Oracle JDK 或 GraalVM 17 |
| Node.js | 18+ | 推荐 20 LTS，自带 npm |
| MySQL | 8.0+ | 必须 8.0 以上，支持窗口函数 |
| Redis | 7.0+ | Sa-Token 会话持久化使用 |
| Maven | 3.9+ | 后端构建工具 |
| IDE | IntelliJ IDEA 2023.3+ | 后端开发 |
| IDE | VS Code + Volar 插件 | 前端开发 |

---

## 二、后端启动指南

### 2.1 导入 IDEA

1. 打开 IntelliJ IDEA → `File` → `Open` → 选择 `backend/` 目录
2. IDEA 会自动识别 `pom.xml`，等待 Maven 依赖下载完成
3. 确认 Project SDK 为 JDK 17：`File` → `Project Structure` → `Project` → `SDK`

### 2.2 初始化数据库

```sql
-- 1. 创建数据库
CREATE DATABASE gantt DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 2. 创建用户表（示例）
USE gantt;

CREATE TABLE sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    username    VARCHAR(64)  NOT NULL COMMENT '用户名',
    password    VARCHAR(128) NOT NULL COMMENT '密码',
    nickname    VARCHAR(64)  DEFAULT NULL COMMENT '昵称',
    email       VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    phone       VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    status      TINYINT      DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    create_by   VARCHAR(64)  DEFAULT NULL COMMENT '创建人',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT NULL COMMENT '更新人',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT      DEFAULT 0 COMMENT '逻辑删除 0-未删 1-已删',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
```

### 2.3 修改本地配置

编辑 `backend/src/main/resources/application.yml`，将数据库和 Redis 连接信息改为你的本地环境：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gantt?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: 你的本地MySQL密码   # ← 改这里
```

### 2.4 运行

1. 在 IDEA 中找到 `com.gantt.GanttApplication`
2. 右键 → `Run 'GanttApplication'`
3. 控制台出现 `Started GanttApplication in x.xx seconds` 表示启动成功
4. 访问 `http://localhost:8080` 验证

---

## 三、前端启动指南

### 3.1 安装依赖

```bash
cd rontend
npm install
# 如果下载慢，使用国内镜像：
# npm install --registry https://registry.npmmirror.com
```

### 3.2 启动开发服务器

```bash
npm run dev
```

浏览器访问 `http://localhost:3000`，默认会跳转到登录页。

### 3.3 其他命令

| 命令 | 用途 |
|------|------|
| `npm run build` | 生产环境构建 |
| `npm run type-check` | TypeScript 类型检查 |
| `npm run lint` | ESLint 代码检查 |

---

## 四、目录结构说明

### 4.1 后端 — 模块化分包（高内聚低耦合）

```
backend/src/main/java/com/gantt/
├── common/                    # 公共层：Result、异常、BaseEntity、配置
└── modules/                   # 业务模块：按领域划分
    ├── system/                # 系统管理（用户、权限）
    │   ├── controller/
    │   ├── service/
    │   ├── mapper/
    │   ├── entity/
    │   └── dto/
    ├── plan/                  # 计划管理
    └── task/                  # 任务管理
```

**设计理念**：传统 `controller/`、`service/`、`mapper/` 平铺结构在项目变大后，修改一个功能需要在三个顶层目录间跳转。按领域模块分包后，每个模块内部自包含所有层次，新增模块只需新建目录，各模块高内聚、低耦合。

### 4.2 前端 — Feature-based 架构

```
rontend/src/
├── common/          # 全局公共：utils、styles、组件
├── layouts/         # 布局组件
├── router/          # 路由入口（自动扫描 modules/*/router.ts）
├── stores/          # 全局 Pinia Store
└── modules/         # 业务模块：按特性划分
    ├── system/      # 每个模块自包含 views/、api/、store/、types/
    ├── plan/
    └── task/
```

**设计理念**：每个模块的页面、接口、状态、类型完全内聚。新增模块只需创建目录 + 添加 `router.ts`，主路由通过 `import.meta.glob` 自动扫描注册，零改动现有代码。

---

## 五、团队 Git 规范

### 5.1 每天 16:30 的"安全合并四步曲"（全员必读 SOP）

> **核心原则**：把冲突留在自己的分支，把干净的代码交给 dev 分支。
>
> 建议将以下流程打印出来，贴在每个开发者的显示器旁边。

---

#### 🛑 第一步：停手自测，提交本地（16:30 - 16:40）

**动作**：停止让 AI 生成新代码。确保本地前后端项目能正常编译/运行（无红字报错）。

**命令**（在自己的 feature/xxx 分支上执行）：

```bash
git add .
# 规范提交信息：feat(模块): 描述 - 姓名拼音首字母
git commit -m "feat(plan): 完成甘特图拖拽接口对接 - cgl"
```

> ⚠️ **红线**：如果本地跑不起来，绝对不允许提交，自己加班修好再说，不要把烂代码推上去恶心队友。

---

#### 🔄 第二步：拉取最新 dev，在本地"排雷"（16:40 - 16:50）【最核心一步】

**动作**：把别人今天合并到 dev 的最新代码拉下来，和自己的代码合并。如果有冲突，在自己的 feature 分支里解决掉。

**命令**：

```bash
# 1. 切换到 dev 分支，拉取远程最新代码
git checkout dev
git pull origin dev

# 2. 切回自己的 feature 分支
git checkout feature/plan-gantt-cgl

# 3. 将最新的 dev 合并到自己的分支（在这里解决所有冲突！）
git merge dev
```

> ⚠️ **红线**：遇到冲突时，严禁无脑点击"Accept Current（保留我的）"或"Accept Incoming（保留别人的）"。必须逐行看 Diff，如果是业务逻辑冲突，立刻把写那段代码的队友叫过来一起看。解决完冲突后，执行 `git add .` 和 `git commit`。

---

#### 🚀 第三步：推送干净的 feature 分支到远程（16:50 - 16:55）

**动作**：经过第二步，你的 feature 分支已经包含了最新的 dev 代码，且没有冲突了。现在把它推到远程仓库。

**命令**：

```bash
git push origin feature/plan-gantt-cgl
```

---

#### 👀 第四步：发起 PR 与 Code Review（16:55 - 17:10）

**动作**：

1. 陈谷林/罗国繁登录 Gitee/GitHub，将自己的 feature 分支向 **dev** 分支发起 Pull Request (PR)。
2. PM 霍泰企进行 Review：点开"文件变更（Files Changed）"，重点检查 AI 有没有"越界"（比如陈谷林改了 task 模块的代码，或者擅自改了 `pom.xml` / `application.yml`）。
3. 确认无误后，霍泰企点击 Merge（建议勾选 **Squash and Merge** 压缩提交，保持 dev 历史干净）。

> ⚠️ **红线**：霍泰企绝对不能闭着眼睛点 Merge！你是架构师，你是代码质量的最后一道防线。

---

### 5.2 分支保护规则（PM 霍泰企 Day 1 必须配置）

> 不要考验人性，要用物理手段防止误操作。

无论你们使用 Gitee（码云）、GitHub 还是 GitLab，PM 必须在仓库的 **Settings（设置）→ Branches（分支管理/保护规则）** 中，对 `main` 和 `dev` 分支进行以下配置：

#### 🛡️ 基础保护（防误删、防强推）

| 配置项 | 说明 | 必须勾选 |
|--------|------|---------|
| **禁止强制推送** (Restrict Force Pushes) | 防止有人用 `git push -f` 把别人一天的代码直接覆盖掉 | ✅ |
| **禁止删除** (Restrict Deletions) | 防止有人手滑把 dev 分支删了 | ✅ |

#### 🛡️ 合并准入规则（防脏代码直接入库）

| 配置项 | 说明 | 必须勾选 |
|--------|------|---------|
| **禁止直接 Push** (Require a pull request before merging) | 任何人（包括 PM 自己）都不能在本地直接 `git push origin dev`，必须走 PR 流程 | ✅ |
| **要求代码审查** (Require approvals) | 设置为 **1 人**。提交 PR 的人不能自己合并自己，必须有另一个人点击 Approve 后才能合并 | ✅ |
| **取消"允许管理员绕过"** (Allow specified actors to bypass required pull requests) | 确保 PM 自己也不能搞特权直接推代码 | 取消勾选 |

#### 🛡️ 状态检查（进阶防坑，可选但强烈推荐）

如果后续接入了 CI/CD（如 GitHub Actions 或 Gitee Go），可以勾选 **Require status checks to pass before merging**。

效果：只有当自动化脚本确认"后端能编译通过"、"前端 ESLint 没报错"时，Merge 按钮才会亮起。

---

### 5.3 团队 Git 协作"三大铁律"

> 请在团队群里置顶以下三条铁律。

---

**铁律 1：公共文件"独占提交"原则（防 AI 乱改依赖）**

- **场景**：AI 为了图省事，经常擅自修改 `pom.xml` 加依赖，或者修改 `utils/request.ts` 加拦截器。如果和业务代码一起提交，合并时必出大冲突。
- **规则**：如果今天必须修改公共文件（如加个 Maven 依赖），这次 Commit **只能包含公共文件的修改**，绝对不能夹杂业务代码！
- **沟通**：修改前必须在群里吼一声："我要改 pom.xml 加个 hutool 依赖，大家先别合并，等我推上去大家 pull 一下最新 dev 再继续。"

---

**铁律 2：严禁在 dev/main 分支上直接写代码**

- **场景**：早上打开电脑，没注意当前在 dev 分支，直接用 AI 写了一天代码，下班发现无法提交。
- **规则**：写代码前第一件事，看一眼 IDE 右下角的当前分支！
- **补救措施**（如果不小心在 dev 写了代码）：

```bash
git stash              # 1. 把改动暂存起来
git checkout feature/xxx # 2. 切回自己的分支
git stash pop          # 3. 把改动恢复到自己的分支上
```

---

**铁律 3：遇到"史诗级冲突"立刻摇人**

- **场景**：AI 把某个文件改得面目全非（比如重新格式化了整个 500 行的 Vue 文件），导致 Git 冲突多到无法手动解决。
- **规则**：如果一个文件的冲突超过 20 处，**立刻停止手动解冲突**！在群里呼叫 PM 霍泰企和写那个文件的队友。
- **PM 的"时光倒流术"**：

```bash
# 找到该文件昨天正常时的 commit hash
git log --oneline src/modules/plan/views/Gantt.vue

# 把该文件单独恢复到昨天的版本，放弃今天的冲突
git checkout a1b2c3d -- src/modules/plan/views/Gantt.vue
```

---

## 六、提交信息规范

```
<type>(<scope>): <subject> - <姓名首字母>

type:   feat | fix | refactor | style | docs | test | chore
scope:  system | plan | task | common | config
```

示例：

```
feat(plan): 新建计划接口联调 - cgl
fix(auth): 修复 token 过期后未跳转登录页 - lgf
refactor(common): 抽取 BaseEntity 公共字段 - htq
```
