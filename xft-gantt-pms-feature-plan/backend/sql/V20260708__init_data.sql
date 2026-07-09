-- ============================================================
-- V20260708__init_data.sql
-- 任务甘特图系统：种子数据
-- 前置：V20260708__init_tables.sql + V20260708__init_rbac.sql 已执行
-- 前置：admin 用户已通过 /api/auth/register 创建（当前 id=2，密码已 BCrypt 哈希）
-- ============================================================

USE `gantt`;

-- 幂等：重复执行不报错
DELETE FROM `sys_role_menu` WHERE `role_id` = 1;
DELETE FROM `sys_user_role`  WHERE `role_id` = 1;
DELETE FROM `sys_role`       WHERE `id` = 1;
DELETE FROM `sys_menu`       WHERE `id` BETWEEN 1 AND 100;

-- ============================================================
-- 1. 角色：admin
-- ============================================================
INSERT INTO `sys_role` (`id`, `role_key`, `role_name`, `description`, `status`) VALUES
    (1, 'admin', '管理员', '系统超级管理员', 1);

-- ============================================================
-- 2. 菜单（id 1-100 预留给种子数据）
--    列顺序: id, parent_id, name, path, component, icon, sort, type, permission, visible
--    type: 0=目录 1=菜单 2=按钮
-- ============================================================

-- 2.1 系统管理目录
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `icon`, `sort`, `type`, `permission`, `visible`) VALUES
    (10, 0,  '系统管理', '/system',          NULL,                          'Setting',    10, 0, NULL,         1),
    (11, 10, '用户管理', '/system/user',     'system/views/UserManage',     'User',       11, 1, NULL,         1),
    (12, 11, '用户查询', NULL,               NULL,                    NULL,         1,  2, 'user:list',   1),
    (13, 11, '用户新增', NULL,               NULL,                    NULL,         2,  2, 'user:create', 1),
    (14, 11, '用户修改', NULL,               NULL,                    NULL,         3,  2, 'user:update', 1),
    (15, 11, '用户删除', NULL,               NULL,                    NULL,         4,  2, 'user:delete', 1),
    (16, 11, '角色分配', NULL,               NULL,                    NULL,         5,  2, 'user:role',   1),
    (17, 10, '个人信息', '/system/profile',  'system/views/ProfileView',    'UserFilled', 12, 1, 'user.info',   1);

-- 2.2 计划管理目录
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `icon`, `sort`, `type`, `permission`, `visible`) VALUES
    (20, 0,  '计划管理', '/plan',            NULL,                          'Calendar',   20, 0, NULL,          1),
    (21, 20, '计划列表', '/plan/list',       'plan/views/PlanManage',       'Tickets',    21, 1, 'plan:list',   1),
    (22, 21, '计划查询', NULL,               NULL,                    NULL,         1,  2, 'plan:list',   1),
    (23, 21, '计划新增', NULL,               NULL,                    NULL,         2,  2, 'plan:create', 1),
    (24, 21, '计划修改', NULL,               NULL,                    NULL,         3,  2, 'plan:update', 1),
    (25, 21, '计划删除', NULL,               NULL,                    NULL,         4,  2, 'plan:delete', 1);

-- 2.3 任务管理目录
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `icon`, `sort`, `type`, `permission`, `visible`) VALUES
    (30, 0,  '任务管理', '/task',            NULL,                          'List',       30, 0, NULL,          1),
    (31, 30, '任务列表', '/task/list',       'task/views/TaskManage',       'Document',   31, 1, 'task:list',   1),
    (32, 31, '任务查询', NULL,               NULL,                    NULL,         1,  2, 'task:list',   1),
    (33, 31, '任务新增', NULL,               NULL,                    NULL,         2,  2, 'task:create', 1),
    (34, 31, '任务修改', NULL,               NULL,                    NULL,         3,  2, 'task:update', 1),
    (35, 31, '任务删除', NULL,               NULL,                    NULL,         4,  2, 'task:delete', 1);

-- 2.4 甘特图目录
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `component`, `icon`, `sort`, `type`, `permission`, `visible`) VALUES
    (40, 0,  '甘特图',   '/gantt',           NULL,                          'DataLine',   40, 0, NULL,          1),
    (41, 40, '甘特视图', '/gantt/view',      'gantt/views/GanttView',       'Histogram',  41, 1, 'gantt:view',  1);

-- ============================================================
-- 3. 角色-菜单关联：admin 角色 ↔ 全部菜单
-- ============================================================
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT 1, `id` FROM `sys_menu` WHERE `id` BETWEEN 1 AND 100;

-- ============================================================
-- 4. 用户-角色关联：admin 用户(id=2) ↔ admin 角色(id=1)
--    若 admin 用户 id 不是 2，请调整下方 user_id
-- ============================================================
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
    (2, 1);
