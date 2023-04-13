package com.yukiice.dto;

import com.yukiice.entity.Setmeal;
import com.yukiice.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/11 10:20
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
