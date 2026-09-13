package com.studydemo.server.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文章新增/修改入参。
 *
 * <p>title 与 status 为必填（Controller 方法体内手动校验，失败走统一 ApiResponseVo 的异常返回，
 * 与 cdp_service 的 try-catch 风格一致）；id 为修改时必填、新增时留空；
 * 其余字段可选，由 Service 层按需覆盖，未传的字段保持原值（修改时）或留空（新增时）。</p>
 */
public class ArticleSaveDTO {

    /** 主键：新增时为空，修改时由前端合并进 body */
    private Long id;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String summary;

    private String content;

    private String cover;

    private String author;

    @NotNull(message = "状态不能为空")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
