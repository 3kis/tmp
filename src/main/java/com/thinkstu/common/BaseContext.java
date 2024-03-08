package com.thinkstu.common;

/**
 * 线程同步
 *
 * @author : Asher
 * @since : 2023-04/14, 9:15 AM, 周六
 **/
public class BaseContext {
    private static ThreadLocal<Long> local = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        local.set(id);
    }

    public static Long getCurrentId() {
        return local.get();
    }
}
