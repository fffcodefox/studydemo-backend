package com.studydemo.server.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 新增/修改用户的入参对象（Data Transfer Object）。
 *
 * <p>P3C 规约：对外接口入参必须做合法性校验，不允许明显脏数据落到数据库。</p>
 */
public class UserSaveDTO {

    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名不能超过 50 个字")
    private String username;

    /** 手机号（选填） */
    @Size(max = 20, message = "手机号不能超过 20 位")
    private String phone;

    /** 状态：1-启用 0-禁用（选填，缺省默认启用） */
    private Integer status;

    /** 备注（选填） */
    @Size(max = 255, message = "备注不能超过 255 个字")
    private String remark;

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
}
