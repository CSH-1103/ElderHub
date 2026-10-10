package com.it.elderhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 床位信息表
 */
@TableName(value = "bed")
public class Bed {

    /**
     * 床位ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 床位编号
     */
    @TableField(value = "bed_no")
    private String bedNo;

    /**
     * 所属房间编号
     */
    @TableField(value = "room_no")
    private Integer roomNo;

    /**
     * 床位状态 (1:空闲 2:已入住 3:外出)
     */
    @TableField(value = "bed_status")
    private Integer bedStatus;

    /**
     * 客户id
     */
    @TableField(value = "customer_id")
    private Integer customerId;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    //========下面这一大块，你自己按 Alt+Insert 选择 Getter and Setter自动生成，不要手写========
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getBedStatus() {
        return bedStatus;
    }

    public void setBedStatus(Integer bedStatus) {
        this.bedStatus = bedStatus;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
