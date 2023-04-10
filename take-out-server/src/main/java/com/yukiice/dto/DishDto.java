package com.yukiice.dto;

import com.yukiice.entity.Dish;
import com.yukiice.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/10 14:56
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor>  flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
