package com.yukiice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yukiice.entity.Category;

public interface CategoryService extends IService<Category> {
    public void customizeRemoveById(Long ids);
}
