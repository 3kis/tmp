package com.thinkstu.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Result结果对象
 *
 * @author : Asher
 * @since : 2024-04/12, 9:40 AM, 周四
 **/
@Data
public class R<T> implements Serializable {
    private Integer code;   // 相应代码，1表示成功
    private String msg;     // 错误信息
    private T data;         // 成功信息
    private Map<String, Object> map = new HashMap<>();      //动态数据（扩展用法）

    public static <K> R<K> success(K object) {
        R<K> result = new R<>();
        result.code = 1;
        result.data = object;
        return result;
    }

    public static <K> R<K> error(String msg) {
        R<K> result = new R<>();
        result.code = 0;
        result.msg = msg;
        return result;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
