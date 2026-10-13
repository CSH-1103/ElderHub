package com.it.elderhub.vo;

import lombok.Data;

import java.util.List;

/**
 * 客户饮食偏好 VO（前端餐饮页字段：customer_name/allergies/avoid_tags/notes）
 */
@Data
public class PreferenceVO {

    // 客户ID
    private Integer id;

    // 客户姓名
    private String customer_name;

    // 过敏原（数组）
    private List<String> allergies;

    // 忌口（数组）
    private List<String> avoid_tags;

    // 备注
    private String notes;
}
