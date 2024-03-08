package com.thinkstu.common;

/**
 * 自定义异常类
 *
 * @author : Asher
 * @since : 2023-04/14, 8:40 PM, 周六
 **/
public class MyCheckException extends RuntimeException {
    public MyCheckException(String msg) {
        super(msg);
    }
}
