package com.studydemo.server.dto;

/**
 * 按主键操作的轻量入参（对齐 cdp_service 的 reqVo 风格）。
 *
 * <p>详情查询等只需主键的场景复用它，避免把主键塞进查询/保存 DTO 造成语义混乱。</p>
 */
public class ProjectIdReq {

    /** 项目主键 */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
