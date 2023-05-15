package com.yukiice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiice.common.R;
import com.yukiice.dto.DishDto;
import com.yukiice.dto.SetmealDto;
import com.yukiice.entity.Category;
import com.yukiice.entity.Setmeal;
import com.yukiice.service.CategoryService;
import com.yukiice.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/11 10:02
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetMealController {

    @Autowired
    SetmealService setmealService;

    @Autowired
    CategoryService categoryService;

    /**
     * 查看分类菜品数据
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> list(int page, int pageSize,String name){
        log.info("name：{}",name);
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        queryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        setmealService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        List<Setmeal> records =pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            setmealDto.setCategoryName(category.getName());
            return  setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return  R.success(setmealDtoPage);
    }


    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return  R.success("新增成功");
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return  R.success("更新成功");
    }

    /**
     * 删除操作
     * @param ids
     * @return
     */
    @DeleteMapping("")
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.deleteBy(ids);
        return  R.success("删除成功!");
    }

    /**
     * 更新状态操作
     * @param ids
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> changeStatus(@RequestParam List<Long> ids,@PathVariable  int status){
        setmealService.changeStatus(ids,status);
        return  R.success("更改状态成功!");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> detail(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return  R.success(setmealDto);
    }
    @GetMapping("/list")
    public R<List<Setmeal>> getList(@RequestParam Long categoryId,@RequestParam int status){
        return R.success(setmealService.getListByCategoryId(categoryId,status));
    }

    @GetMapping("/dish/{id}")
    public R<SetmealDto> getOneInCart(@PathVariable Long id){
        return  null;
    }
}
