package com.it.elderhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 食品信息表
 * @TableName food
 */
@TableName(value ="food")
@Data
public class Food {
    /**
     * 食品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 食品名称
     */
    @TableField(value = "food_name")
    private String food_name;

    /**
     * 食品分类
     */
    @TableField(value = "food_type")
    private String food_type;

    /**
     * 食品价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 是否清真（0:否 1:是）
     */
    @TableField(value = "is_halal")
    private Integer is_halal;

    /**
     * 食品图片路径
     */
    @TableField(value = "food_img")
    private String food_img;

    /**
     * 营养标签（逗号分隔，如：LOW_SALT,LOW_FAT）
     */
    @TableField(value = "nutrient_tags")
    private String nutrient_tags;

    /**
     * 过敏原/忌口（逗号分隔，如：花生,海鲜）
     */
    @TableField(value = "allergens")
    private String allergens;
}