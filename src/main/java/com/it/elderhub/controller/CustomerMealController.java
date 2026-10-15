package com.it.elderhub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.elderhub.common.Result;
import com.it.elderhub.entity.CustomerMeal;
import com.it.elderhub.service.CustomerMealService;
import com.it.elderhub.vo.CustomerMealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 客户膳食配置Controller
 */
@RestController
@RequestMapping("/admin/customer-meal")
public class CustomerMealController {

    @Autowired
    private CustomerMealService customerMealService;

    /**
     * 分页查询客户膳食配置
     * @param pageNum 页码（默认1）
     * @param pageSize 每页条数（默认10）
     * @param customerId 客户ID筛选（可空）
     * @param customerName 客户姓名模糊筛选（可空）
     */
    @GetMapping("/list")
    public Result<IPage<CustomerMealVO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Integer customerId,
            String customerName) {
        return Result.success(customerMealService.listCustomerMeals(pageNum, pageSize, customerId, customerName));
    }

    /**
     * 新增客户膳食配置
     */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody CustomerMeal customerMeal) {
        customerMealService.saveCustomerMeal(customerMeal);
        return Result.success();
    }

    /**
     * 修改客户膳食配置
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody CustomerMeal customerMeal) {
        customerMealService.updateCustomerMeal(customerMeal);
        return Result.success();
    }

    /**
     * 删除客户膳食配置（逻辑删除）
     * @param id 配置ID
     */
    @DeleteMapping("/remove/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        customerMealService.removeCustomerMeal(id);
        return Result.success();
    }
}
