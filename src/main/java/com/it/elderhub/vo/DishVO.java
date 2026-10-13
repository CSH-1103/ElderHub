package com.it.elderhub.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 每日菜单中的菜品 VO（前端餐饮页字段：name/price/nutrient_tags/allergens）
 */
@Data
public class DishVO {

    // 菜单记录ID
    private Integer id;

    // 菜品ID
    private Integer foodId;

    // 菜品名称
    private String name;

    // 价格
    private BigDecimal price;

    // 营养标签（数组，如 LOW_SALT）
    private List<String> nutrient_tags;

    // 过敏原/忌口（数组，如 花生）
    private List<String> allergens;

    // 口味要求
    private String taste;
}
