package com.yukiice.dto;

import com.yukiice.entity.User;
import lombok.Data;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/27 15:26
 */
@Data
public class CodeDto extends User {
    private String code;
}
