-- ============================================================
--  文章表 + 初始数据（文章管理模块）
--  执行方式：直接连 study_db 执行本文件即可
--  注意：本文件为「首次初始化脚本」，重复执行会追加数据；
--        如需重跑，请先 DELETE FROM article;
-- ============================================================

CREATE TABLE IF NOT EXISTS article (
  id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  title       VARCHAR(120) NOT NULL                COMMENT '标题',
  summary     VARCHAR(255) DEFAULT NULL            COMMENT '摘要',
  content     LONGTEXT                              COMMENT '正文',
  cover       VARCHAR(255) DEFAULT NULL            COMMENT '封面图地址',
  author      VARCHAR(50)  DEFAULT NULL            COMMENT '作者',
  status      TINYINT      NOT NULL DEFAULT 1       COMMENT '状态：1-已发布 0-草稿',
  views       INT          NOT NULL DEFAULT 0       COMMENT '浏览量',
  is_deleted  TINYINT      NOT NULL DEFAULT 0       COMMENT '逻辑删除：0-未删 1-已删',
  create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_status (status),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 造 5 条练手主题文章（含草稿与已发布）；content 为单行文本，避免多行转义问题
INSERT INTO article (title, summary, content, cover, author, status, views) VALUES
('Vue 3 响应式原理：从 ref 到 effect',
 '为什么解构 reactive 会丢响应式？ref 的 .value 到底做了什么？顺着依赖收集这条线串一遍。',
 '# Vue 3 响应式原理 / 从 ref 到 effect，核心是 Proxy 加依赖收集 / reactive 用 Proxy 拦截 get/set；读时收集依赖 track，写时触发 trigger；ref 是对基本类型的包装，通过 .value 访问',
 NULL, 'fffcodefox', 1, 128),
('MyBatis-Plus 父子表 CRUD 的通用写法',
 '主子表一起保存、级联删除、事务边界怎么划，整理一套可以直接抄的模板。',
 '# 父子表 CRUD / 主表存 template_id，子表存 main_config_id / 1.保存：先插主表拿主键再批量插子表；2.删除：级联删子表再删主表；3.事务：service 方法加 @Transactional',
 NULL, 'fffcodefox', 1, 86),
('Spring Boot 统一返回体与全局异常处理',
 '用 Result 收敛所有接口结构，配合 RestControllerAdvice 把校验失败和业务异常统一转成固定 JSON。',
 '# 统一返回体 / public class Result(T) 包含 code、message、data 三个字段 / 全局异常处理器负责把异常翻译成 Result.error(...)',
 NULL, 'fffcodefox', 1, 54),
('Vite 项目体积与构建速度的几个抓手',
 '按需加载、手动分包、依赖预构建，以及一份可复用的 vue-tsc 检查配置。',
 '# Vite 优化 / 路由级懒加载；manualChunks 手动分包；依赖预构建缓存',
 NULL, 'fffcodefox', 0, 12),
('前端樱花飘落特效的纯 CSS 实现',
 '不用 canvas，用 22 片绝对定位的 span 加 keyframes 飘落，配合 prefers-reduced-motion 做降级。',
 '# 纯 CSS 樱花 / 每片花瓣随机 left、大小、飘落时长；负 animation-delay 让首批花瓣错峰；减少动态效果时改为静态散落',
 NULL, 'fffcodefox', 1, 203);
