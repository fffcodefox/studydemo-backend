-- ============================================================
--  项目表 + 初始数据（项目管理模块）
--  执行方式：直接连 study_db 执行本文件即可
--  注意：本文件为「首次初始化脚本」，重复执行会追加数据；
--        如需重跑，请先 DELETE FROM project;
-- ============================================================

CREATE TABLE IF NOT EXISTS project (
  id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_name VARCHAR(120) NOT NULL                COMMENT '项目名称',
  project_code VARCHAR(64)  DEFAULT NULL            COMMENT '项目编号',
  leader       VARCHAR(50)  DEFAULT NULL            COMMENT '负责人',
  status       TINYINT      NOT NULL DEFAULT 1       COMMENT '状态：1-进行中 0-已结束',
  description  VARCHAR(255) DEFAULT NULL            COMMENT '项目描述',
  start_date   DATE         DEFAULT NULL            COMMENT '开始日期',
  end_date     DATE         DEFAULT NULL            COMMENT '结束日期',
  is_deleted   TINYINT      NOT NULL DEFAULT 0       COMMENT '逻辑删除：0-未删 1-已删',
  create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_status (status),
  KEY idx_project_name (project_name),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- 造 4 条练手项目（含进行中与已结束）；start_date / end_date 为日期，便于列表展示时间区间
INSERT INTO project (project_name, project_code, leader, status, description, start_date, end_date) VALUES
('智慧园区物联网平台',   'P-2026-001', '张三', 1, '园区设备接入与统一监控',     '2026-01-05', '2026-12-31'),
('计量器具检定系统升级', 'P-2026-002', '李四', 1, '检定流程数字化改造',         '2026-03-01', '2026-09-30'),
('老旧台账数据清洗',     'P-2026-003', '王五', 0, '历史 Excel 台账导入与校验', '2025-11-01', '2026-02-28'),
('图纸 AI 识别试点',     'P-2026-004', '赵六', 1, 'CAD 图纸自动识别关键字段',  '2026-04-10', NULL);
