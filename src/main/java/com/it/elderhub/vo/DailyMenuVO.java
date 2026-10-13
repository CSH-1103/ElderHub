package com.it.elderhub.vo;

import lombok.Data;

import java.util.List;

/**
 * 每日菜单 VO（前端餐饮页：按日期展示三餐）
 */
@Data
public class DailyMenuVO {

    // 日期（yyyy-MM-dd）
    private String date;

    // 星期几（周一~周日）
    private String weekDay;

    // 早餐
    private List<DishVO> breakfast;

    // 午餐
    private List<DishVO> lunch;

    // 晚餐
    private List<DishVO> dinner;
}
