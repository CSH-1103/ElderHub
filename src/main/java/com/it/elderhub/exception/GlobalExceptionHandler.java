package com.it.elderhub.exception;

import com.it.elderhub.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleIllegalArgument(IllegalArgumentException e) {
        return Result.error(400, e.getMessage());
    }

    /**
     * 业务异常（RuntimeException）返回具体提示，便于前端展示
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntime(RuntimeException e) {
        String msg = e.getMessage();
        return Result.error(500, (msg == null || msg.isEmpty()) ? "操作失败" : msg);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 其他未知异常不返回细节，避免泄露敏感信息
        return Result.error("Internal server error");
    }
}