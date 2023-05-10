package com.yukiice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yukiice.dto.SetmealDto;
import com.yukiice.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    public void updateWithDish(SetmealDto setmealDto);

    public void deleteBy(List<Long> ids);

    public void changeStatus(List<Long> ids,int status);

    public SetmealDto getByIdWithDish(Long ids);

    public List<Setmeal> getListByCategoryId(Long categoryId,int status);

}
