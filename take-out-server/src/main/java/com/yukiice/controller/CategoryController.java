package com.yukiice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiice.common.R;
import com.yukiice.entity.Category;
import com.yukiice.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/8 15:29
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 根据分页获取数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> getListByPage(int page, int pageSize){
        log.info("当前页数为:{},页码为:{}",page,pageSize);
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return  R.success(pageInfo);
    }

    /**
     * 添加菜品或者套餐分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> addDishes(@RequestBody Category category){
        categoryService.save(category);
        return  R.success("添加分类成功");
    }


    /**
     * 删除分类操作
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        categoryService.customizeRemoveById(ids);
        return  R.success("删除分类成功");
    }

    @PutMapping
    public R<String> modify(@RequestBody Category category){
        categoryService.updateById(category);
        return  R.success("修改分类成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        log.info("类型为:{}",category.getType());
        LambdaQueryWrapper<Category>  queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list =  categoryService.list(queryWrapper);
        return R.success(list);
    }
}
