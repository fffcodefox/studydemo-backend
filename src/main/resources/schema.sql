CREATE TABLE IF NOT EXISTS t_demo_message (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(64)  NOT NULL COMMENT '称呼',
    content     VARCHAR(255) NOT NULL COMMENT '消息内容',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删 1-已删',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '示例消息表';

CREATE TABLE IF NOT EXISTS sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username    VARCHAR(50)  NOT NULL COMMENT '用户名',
    phone       VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：1-启用 0-禁用',
    remark      VARCHAR(255) DEFAULT NULL COMMENT '备注',
    is_deleted  TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_username (username),
    KEY idx_phone (phone)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';
