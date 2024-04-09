package com.thinkstu.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 *
 * @author : Asher
 * @since : 2024-04/12, 8:43 PM, 周四
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptiopnHandler {
    /**
     * SQL异常捕获器
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> handler(Exception ex) {
        ex.printStackTrace();   // 输出错误信息
        String msg = ex.getMessage();
        log.error("数据库操作发生错误：{}", msg);
        if (msg.contains("Duplicate entry")) {
            String[] split = msg.split(" ");
            return R.error(split[9] + "用户名已经存在！");
        }
        return R.error("未知错误~");
    }

    /**
     * 捕获自定义异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MyCheckException.class)
    public R<String> myHandler(Exception ex) {
        String msg = ex.getMessage();
        log.error("自定义错误：{}", msg);
        return R.error(msg);
    }
}
