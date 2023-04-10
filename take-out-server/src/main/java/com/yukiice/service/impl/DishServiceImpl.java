package com.yukiice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.dto.DishDto;
import com.yukiice.entity.Dish;
import com.yukiice.entity.DishFlavor;
import com.yukiice.mapper.DishMapper;
import com.yukiice.service.DishFlavorService;
import com.yukiice.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/8 17:13
 */
@Service
@Slf4j
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishFlavorService dishFlavorService;
    @Override
    public void saveWithFlavor(DishDto dishDto) {
//        保存基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors =   flavors.stream().peek((item) -> item.setDishId(dishId)).collect(Collectors.toList());
//        保存菜品口味到菜品口味表DishFlavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
//        拿到对应的dish
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
//        查询口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(lambdaQueryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    /**
     * 更新dish表和dishflavor表
     * @param dishDto
     */
    @Override
    public void updateWithFlavor(DishDto dishDto) {
//        更新dish表
        this.updateById(dishDto);
//        清理原有的口味数据
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
//        添加新口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return  item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
