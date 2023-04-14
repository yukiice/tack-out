package com.yukiice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiice.common.R;
import com.yukiice.dto.DishDto;
import com.yukiice.entity.Category;
import com.yukiice.entity.Dish;
import com.yukiice.service.CategoryService;
import com.yukiice.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/10 13:53
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishFlavorController {

    @Autowired
    DishService dishService;

    @Autowired
    CategoryService categoryService;

    /**
     * 获取分页数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> getList(int page, int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper =  new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Dish::getUpdateTime);
        queryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        dishService.page(pageInfo,queryWrapper);
//        执行对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list =  records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());
            return  dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 新增菜品操作
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return  R.success("新增菜品成功");
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> one(@PathVariable Long id){
        log.info("id为：{}",id);
        DishDto dishDto =  dishService.getByIdWithFlavor(id);
        return  R.success(dishDto);
    }

    /**
     * 修改菜品数据
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> edit(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return  R.success("修改成功");
    }

    /**
     * 获取口味列表数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
//        筛选状态为起售的菜品
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 删除操作
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dishService.deleteByList(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@RequestParam List<Long> ids,@PathVariable  int status){
        dishService.updateStatusById(ids, status);
        return  R.success("更新状态成功");
    }
}
