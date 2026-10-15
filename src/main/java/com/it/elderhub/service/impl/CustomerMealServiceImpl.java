package com.it.elderhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.elderhub.entity.Customer;
import com.it.elderhub.entity.CustomerMeal;
import com.it.elderhub.entity.Food;
import com.it.elderhub.mapper.CustomerMapper;
import com.it.elderhub.mapper.CustomerMealMapper;
import com.it.elderhub.mapper.FoodMapper;
import com.it.elderhub.service.CustomerMealService;
import com.it.elderhub.utils.WeekDayUtils;
import com.it.elderhub.vo.CustomerMealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * 客户膳食配置Service实现类
 */
@Service
public class CustomerMealServiceImpl implements CustomerMealService {

    @Autowired
    private CustomerMealMapper customerMealMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public IPage<CustomerMealVO> listCustomerMeals(Integer pageNum, Integer pageSize, Integer customerId, String customerName) {
        LambdaQueryWrapper<CustomerMeal> wrapper = new LambdaQueryWrapper<>();
        // 只查未删除的
        wrapper.eq(CustomerMeal::getIs_deleted, 0);
        // 按客户ID筛选
        if (customerId != null) {
            wrapper.eq(CustomerMeal::getCustomer_id, customerId);
        }
        // 按客户姓名模糊筛选（子查询客户表）
        if (customerName != null && !customerName.isEmpty()) {
            wrapper.apply("customer_id IN (SELECT id FROM customer WHERE customer_name LIKE {0} AND is_deleted = 0)",
                    "%" + customerName + "%");
        }
        wrapper.orderByAsc(CustomerMeal::getCustomer_id).orderByAsc(CustomerMeal::getWeek_day).orderByAsc(CustomerMeal::getMeal_type);

        Page<CustomerMeal> page = new Page<>(pageNum, pageSize);
        IPage<CustomerMeal> doPage = customerMealMapper.selectPage(page, wrapper);

        // 转换VO并联查客户名、食品名
        Page<CustomerMealVO> voPage = new Page<>(doPage.getCurrent(), doPage.getSize(), doPage.getTotal());
        voPage.setRecords(doPage.getRecords().stream().map(d -> {
            CustomerMealVO vo = new CustomerMealVO();
            vo.setId(d.getId());
            vo.setCustomerId(d.getCustomer_id());
            vo.setWeekDay(d.getWeek_day());
            vo.setMealType(d.getMeal_type());
            vo.setFoodId(d.getFood_id());
            // 联查客户姓名
            Customer customer = customerMapper.selectById(d.getCustomer_id());
            if (customer != null) {
                vo.setCustomerName(customer.getCustomer_name());
            }
            // 联查食品名称
            Food food = foodMapper.selectById(d.getFood_id());
            if (food != null) {
                vo.setFoodName(food.getFood_name());
            }
            return vo;
        }).collect(Collectors.toList()));

        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCustomerMeal(CustomerMeal customerMeal) {
        // 校验客户
        if (customerMeal.getCustomer_id() == null || customerMapper.selectById(customerMeal.getCustomer_id()) == null) {
            throw new RuntimeException("所选客户不存在");
        }
        // 校验星期
        String weekDay = WeekDayUtils.normalize(customerMeal.getWeek_day());
        if (weekDay == null) {
            throw new RuntimeException("星期格式错误，应为 星期一~星期日");
        }
        // 校验餐次
        if (customerMeal.getMeal_type() == null || customerMeal.getMeal_type() < 1 || customerMeal.getMeal_type() > 3) {
            throw new RuntimeException("餐次类型错误（1早餐 2午餐 3晚餐）");
        }
        // 校验食品
        if (customerMeal.getFood_id() == null || foodMapper.selectById(customerMeal.getFood_id()) == null) {
            throw new RuntimeException("所选食品不存在");
        }
        // 重复校验：同一客户同一天同一餐次不能重复配置同一食品
        Long exists = customerMealMapper.selectCount(new LambdaQueryWrapper<CustomerMeal>()
                .eq(CustomerMeal::getCustomer_id, customerMeal.getCustomer_id())
                .eq(CustomerMeal::getWeek_day, weekDay)
                .eq(CustomerMeal::getMeal_type, customerMeal.getMeal_type())
                .eq(CustomerMeal::getFood_id, customerMeal.getFood_id())
                .eq(CustomerMeal::getIs_deleted, 0));
        if (exists != null && exists > 0) {
            throw new RuntimeException("该客户在此餐次已配置过此食品");
        }

        customerMeal.setWeek_day(weekDay);
        customerMeal.setIs_deleted(0);
        customerMealMapper.insert(customerMeal);
    }

    @Override
    public void updateCustomerMeal(CustomerMeal customerMeal) {
        if (customerMeal.getId() == null) {
            throw new RuntimeException("配置ID不能为空");
        }
        if (customerMealMapper.selectById(customerMeal.getId()) == null) {
            throw new RuntimeException("配置记录不存在");
        }
        // 星期归一化
        if (customerMeal.getWeek_day() != null && !customerMeal.getWeek_day().isEmpty()) {
            String weekDay = WeekDayUtils.normalize(customerMeal.getWeek_day());
            if (weekDay == null) {
                throw new RuntimeException("星期格式错误，应为 星期一~星期日");
            }
            customerMeal.setWeek_day(weekDay);
        }
        customerMealMapper.updateById(customerMeal);
    }

    @Override
    public void removeCustomerMeal(Integer id) {
        // 逻辑删除
        CustomerMeal customerMeal = new CustomerMeal();
        customerMeal.setId(id);
        customerMeal.setIs_deleted(1);
        customerMealMapper.updateById(customerMeal);
    }
}
