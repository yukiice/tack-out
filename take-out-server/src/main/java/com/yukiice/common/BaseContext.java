package com.yukiice.common;

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录用户的id
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/8 15:05
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return  threadLocal.get();
    }
}
