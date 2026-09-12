package com.studydemo.server.dto;

import java.time.LocalDateTime;

/**
 * 留言板视图对象（View Object），返回给前端。
 *
 * <p>只暴露前端需要的字段，不直接把 {@code DemoMessage} 实体抛出去。</p>
 */
public class MessageVO {

    /** 主键 */
    private Long id;

    /** 称呼 */
    private String name;

    /** 消息内容 */
    private String content;

    /** 创建时间 */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
