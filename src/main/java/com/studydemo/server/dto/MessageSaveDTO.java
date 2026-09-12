package com.studydemo.server.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 新增/修改留言的入参对象（Data Transfer Object）。
 *
 * <p>P3C 规约：对外接口入参必须做合法性校验，不允许出现明显脏数据落到数据库。</p>
 */
public class MessageSaveDTO {

    /** 称呼 */
    @NotBlank(message = "称呼不能为空")
    @Size(max = 64, message = "称呼不能超过 64 个字")
    private String name;

    /** 消息内容 */
    @NotBlank(message = "消息内容不能为空")
    @Size(max = 255, message = "消息内容不能超过 255 个字")
    private String content;

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
}
