package com.yukiice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.common.CustomException;
import com.yukiice.dto.SetmealDto;
import com.yukiice.entity.Setmeal;
import com.yukiice.entity.SetmealDish;
import com.yukiice.mapper.SetmealMapper;
import com.yukiice.service.SetmealDishService;
import com.yukiice.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/8 17:17
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    SetmealDishService setmealDishService;

    @Autowired
    SetmealService setmealService;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
//        保存套餐基本信息
        this.save(setmealDto);
//        保存套餐和菜品的关联信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return  item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void updateWithDish(SetmealDto setmealDto) {
       this.updateById(setmealDto);
       LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
       setmealDishService.remove(queryWrapper);
       List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
       setmealDishes = setmealDishes.stream().map((item)->{
           item.setSetmealId(setmealDto.getId());
           return  item;
       }).collect(Collectors.toList());
       setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void deleteBy(List<Long> ids) {
        ids.stream().peek((item)->{
            LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Setmeal::getId,item);
            queryWrapper.eq(Setmeal::getStatus,1);
            int count = this.count(queryWrapper);
            if (count > 0 ){
                throw  new CustomException("套餐正在售卖中，无法删除！");
            }
        });
//        套餐都不在售卖，执行删除操作
        this.removeByIds(ids);
    }

    @Override
    public void changeStatus(List<Long> ids, int status) {
        ids.stream().peek((item)->{
           Setmeal setmeal =  setmealService.getById(item);
           if (setmeal == null){
               throw  new CustomException("套餐不存在！");
           }
           setmeal.setStatus(status);
           setmealService.updateById(setmeal);
        }).collect(Collectors.toList());
    }

    /**
     * 根据id获取数据
     * @param ids
     * @return
     */
    @Override
    public SetmealDto getByIdWithDish(Long ids) {
        Setmeal setmeal = this.getById(ids);
        SetmealDto setmealDto  = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
//        查询菜品表
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);
//        查询分类表
        return setmealDto;
    }

    @Override
    public List<Setmeal> getListByCategoryId(Long categoryId,int status) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(categoryId != null,Setmeal::getCategoryId,categoryId);
        queryWrapper.eq(Setmeal::getStatus,1);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        return setmealService.list(queryWrapper);
    }
}
