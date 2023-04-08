package com.yukiice.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/8 14:19
 */
@Component
@Slf4j
public class CustomizeMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入操作");
        Long id = BaseContext.getCurrentId();
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser",id);
        metaObject.setValue("updateUser",id);
        log.info(metaObject.toString());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("修改操作");
        Long id = BaseContext.getCurrentId();
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",id);
    }
}
