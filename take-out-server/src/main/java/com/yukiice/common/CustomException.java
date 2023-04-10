package com.yukiice.common;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/8 17:52
 */
public class CustomException  extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
