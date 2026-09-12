package com.studydemo.server.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理。
 *
 * <p>把校验失败、业务异常统一收敛成 {@link Result}，前端永远拿到固定结构，
 * 不用在每个接口里 try-catch。</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 入参校验失败（@Valid 标注的 @RequestBody）。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "参数校验失败";
        return Result.error(ResultCode.BAD_REQUEST, msg);
    }

    /**
     * 业务层的「找不到」类异常（如修改/删除不存在的留言）。
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegal(IllegalArgumentException ex) {
        return Result.error(ResultCode.NOT_FOUND, ex.getMessage());
    }
}
