package com.it.elderhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 客户饮食喜好表
 * @TableName customer_preference
 */
@TableName(value ="customer_preference")
@Data
public class CustomerPreference {
    /**
     * 喜好ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户ID
     */
    @TableField(value = "customer_id")
    private Integer customer_id;

    /**
     * 饮食喜好
     */
    @TableField(value = "preferences")
    private String preferences;

    /**
     * 饮食忌口注意事项
     */
    @TableField(value = "attention")
    private String attention;

    /**
     * 过敏原（逗号分隔，如：花生,海鲜）
     */
    @TableField(value = "allergies")
    private String allergies;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 逻辑删除（0:正常 1:删除）
     */
    @TableField(value = "is_deleted")
    private Integer is_deleted;
}