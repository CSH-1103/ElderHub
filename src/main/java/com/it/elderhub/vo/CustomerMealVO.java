package com.it.elderhub.vo;

import lombok.Data;

/**
 * 客户膳食配置VO（含客户名/食品名联查结果，供前端展示）
 */
@Data
public class CustomerMealVO {
    private Integer id;
    private Integer customerId;
    private String customerName; // 联查客户名
    private String weekDay;
    private Integer mealType;
    private Integer foodId;
    private String foodName; // 联查食品名
}
