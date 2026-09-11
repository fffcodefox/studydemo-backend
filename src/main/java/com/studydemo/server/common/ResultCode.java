package com.studydemo.server.common;

/**
 * 统一返回结果状态码。
 *
 * <p>P3C 规约：不允许任何魔法值出现在代码中，状态码统一定义在此。</p>
 */
public final class ResultCode {

    public static final Integer SUCCESS = 200;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer ERROR = 500;

    private ResultCode() {
    }
}
