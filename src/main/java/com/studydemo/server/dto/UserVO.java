package com.studydemo.server.dto;

import java.time.LocalDateTime;

/**
 * 用户视图对象（View Object），返回给前端。
 *
 * <p>只暴露前端需要的字段，不暴露 {@code isDeleted} 逻辑删除标记。</p>
 */
public class UserVO {

    /** 主键 */
    private Long id;

    /** 用户名 */
    private String username;

    /** 手机号 */
    private String phone;

    /** 状态：1-启用 0-禁用 */
    private Integer status;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
