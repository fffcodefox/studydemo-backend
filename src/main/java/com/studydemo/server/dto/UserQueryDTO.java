package com.studydemo.server.dto;

/**
 * 用户列表查询入参。
 *
 * <p>所有字段均为可选条件，由 Service 层按需拼接到查询中（Spring 自动把 GET 请求参数绑定到该对象）。</p>
 */
public class UserQueryDTO {

    /** 用户名模糊匹配 */
    private String username;

    /** 手机号模糊匹配 */
    private String phone;

    /** 状态精确匹配：1-启用 0-禁用 */
    private Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
