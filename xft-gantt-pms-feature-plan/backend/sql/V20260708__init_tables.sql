-- ============================================================
-- V20260708__init_tables.sql
-- 任务甘特图系统：业务表初始化（plan_project / plan_task）
-- 依据：docs/database.md
-- 注意：biz_plan / biz_task 为旧实体遗留表名，此处 DROP 兜底
-- ============================================================

USE `gantt`;

-- 旧表清理（若存在）
DROP TABLE IF EXISTS `biz_task`;
DROP TABLE IF EXISTS `biz_plan`;

-- ============================================================
-- plan_project：计划/项目表
-- ============================================================
CREATE TABLE `plan_project` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(128) NOT NULL COMMENT '计划名称',
    `description` TEXT         DEFAULT NULL COMMENT '描述',
    `start_date`  DATE         NOT NULL COMMENT '开始日期',
    `end_date`    DATE         NOT NULL COMMENT '结束日期',
    `status`      TINYINT      DEFAULT 0 COMMENT '0-草稿，1-进行中，2-已完成，3-已归档',
    `progress`    INT          DEFAULT 0 COMMENT '进度百分比 0-100',
    `create_by`   VARCHAR(64)  DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   VARCHAR(64)  DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      DEFAULT 0 COMMENT '逻辑删除 0-未删 1-已删',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计划项目表';

-- ============================================================
-- plan_task：任务表
-- ============================================================
CREATE TABLE `plan_task` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `project_id`  BIGINT       NOT NULL COMMENT '所属计划ID',
    `parent_id`   BIGINT       DEFAULT 0 COMMENT '父任务ID（0=顶级）',
    `name`        VARCHAR(128) NOT NULL COMMENT '任务名称',
    `description` TEXT         DEFAULT NULL COMMENT '描述',
    `assignee`    VARCHAR(64)  DEFAULT NULL COMMENT '负责人',
    `start_date`  DATE         NOT NULL COMMENT '开始日期',
    `end_date`    DATE         NOT NULL COMMENT '结束日期',
    `priority`    TINYINT      DEFAULT 1 COMMENT '0-低，1-中，2-高，3-紧急',
    `status`      TINYINT      DEFAULT 0 COMMENT '0-待办，1-进行中，2-已完成',
    `progress`    INT          DEFAULT 0 COMMENT '进度 0-100',
    `sort_order`  INT          DEFAULT 0 COMMENT '排序',
    `create_by`   VARCHAR(64)  DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`   VARCHAR(64)  DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      DEFAULT 0 COMMENT '逻辑删除 0-未删 1-已删',
    PRIMARY KEY (`id`),
    KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';
