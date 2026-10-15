package com.it.elderhub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.elderhub.entity.CustomerMeal;
import com.it.elderhub.vo.CustomerMealVO;

/**
 * 客户膳食配置Service接口
 */
public interface CustomerMealService {

    /**
     * 分页查询客户膳食配置
     * @param pageNum 页码（默认1）
     * @param pageSize 每页条数（默认10）
     * @param customerId 客户ID筛选（可空）
     * @param customerName 客户姓名模糊筛选（可空）
     * @return 分页结果（含客户名/食品名）
     */
    IPage<CustomerMealVO> listCustomerMeals(Integer pageNum, Integer pageSize, Integer customerId, String customerName);

    /**
     * 新增客户膳食配置
     * @param customerMeal 配置信息
     */
    void saveCustomerMeal(CustomerMeal customerMeal);

    /**
     * 修改客户膳食配置
     * @param customerMeal 配置信息（必须带ID）
     */
    void updateCustomerMeal(CustomerMeal customerMeal);

    /**
     * 删除客户膳食配置（逻辑删除）
     * @param id 配置ID
     */
    void removeCustomerMeal(Integer id);
}
