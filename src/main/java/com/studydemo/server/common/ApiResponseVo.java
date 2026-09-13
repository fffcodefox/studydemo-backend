package com.studydemo.server.common;

/**
 * 统一接口返回结构（对齐 cdp_service 的 ApiResponseVo 风格）。
 *
 * <p>用法与 cdp 保持一致：
 * <ul>
 *   <li>成功：{@code new ApiResponseVo<>(data)} —— code 默认 "200"，message 默认 "success"；</li>
 *   <li>失败：{@code new ApiResponseVo<>(e.getClass().getName(), e.getMessage(), null)} —— code 用异常类名，message 用异常信息。</li>
 * </ul>
 * 这里 code 统一为字符串，失败时携带异常类型便于排查；前端用字符串 "200" 判定成功。</p>
 *
 * @param <T> 业务数据类型
 */
public class ApiResponseVo<T> {

    /** 业务码：成功 "200"，失败为异常类名 */
    private String code;

    /** 提示信息：成功 "success"，失败为异常 message */
    private String message;

    /** 业务数据，失败时通常为 null */
    private T data;

    public ApiResponseVo() {
    }

    /** 成功构造：只传业务数据，code/message 取默认值 */
    public ApiResponseVo(T data) {
        this.code = "200";
        this.message = "success";
        this.data = data;
    }

    /** 失败构造：异常类名 + 异常信息 + 数据（通常为 null） */
    public ApiResponseVo(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
