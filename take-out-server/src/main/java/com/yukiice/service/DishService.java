package com.yukiice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yukiice.dto.DishDto;
import com.yukiice.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void deleteByList(List<Long> ids);

    public void updateStatusById(List<Long> ids,int status);
}
