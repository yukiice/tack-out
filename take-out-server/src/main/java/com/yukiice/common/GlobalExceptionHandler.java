package com.yukiice.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/6 17:20
 */
@ControllerAdvice
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")){
            String[]  split = ex.getMessage().split(" ");
            String msg = split[2]+"已存在";
            return  R.error(msg);
        }
        return  R.error("失败了");
    }
}
