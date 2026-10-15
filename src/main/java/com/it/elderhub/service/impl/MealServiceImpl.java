package com.it.elderhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.elderhub.entity.Food;
import com.it.elderhub.entity.Meal;
import com.it.elderhub.mapper.FoodMapper;
import com.it.elderhub.mapper.MealMapper;
import com.it.elderhub.service.MealService;
import com.it.elderhub.utils.WeekDayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 膳食日历Service实现类
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealMapper mealMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public IPage<Meal> listMeals(Integer pageNum, Integer pageSize, String weekDay, Integer mealType, Integer status) {
        LambdaQueryWrapper<Meal> wrapper = new LambdaQueryWrapper<>();
        // 只查未删除的
        wrapper.eq(Meal::getIs_deleted, 0);
        // 按星期筛选（统一归一化为 星期X，兼容 周一/星期一 两种写法）
        if (weekDay != null && !weekDay.isEmpty()) {
            String normalized = WeekDayUtils.normalize(weekDay);
            if (normalized == null) {
                throw new RuntimeException("星期格式错误，应为 星期一~星期日");
            }
            wrapper.eq(Meal::getWeek_day, normalized);
        }
        // 按餐次筛选
        if (mealType != null) {
            wrapper.eq(Meal::getMeal_type, mealType);
        }
        // 按状态筛选（0 或空表示全部）
        if (status != null && status != 0) {
            wrapper.eq(Meal::getStatus, status);
        }
        // 按 星期（周一到周日） + 餐次 排序（中文字符排序不准确，用 FIELD 指定顺序）
        wrapper.last("ORDER BY FIELD(week_day,'星期一','星期二','星期三','星期四','星期五','星期六','星期日'), meal_type ASC, id ASC");

        Page<Meal> page = new Page<>(pageNum, pageSize);
        return mealMapper.selectPage(page, wrapper);
    }

    @Override
    public Meal getMealById(Integer id) {
        return mealMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMeal(Meal meal) {
        // 校验星期
        String weekDay = WeekDayUtils.normalize(meal.getWeek_day());
        if (weekDay == null) {
            throw new RuntimeException("星期格式错误，应为 星期一~星期日");
        }
        // 校验餐次
        if (meal.getMeal_type() == null || meal.getMeal_type() < 1 || meal.getMeal_type() > 3) {
            throw new RuntimeException("餐次类型错误（1早餐 2午餐 3晚餐）");
        }
        // 校验食品
        if (meal.getFood_id() == null || foodMapper.selectById(meal.getFood_id()) == null) {
            throw new RuntimeException("所选食品不存在");
        }
        // 重复校验：同一天同一餐次不能重复添加同一食品
        Long exists = mealMapper.selectCount(new LambdaQueryWrapper<Meal>()
                .eq(Meal::getWeek_day, weekDay)
                .eq(Meal::getMeal_type, meal.getMeal_type())
                .eq(Meal::getFood_id, meal.getFood_id())
                .eq(Meal::getIs_deleted, 0));
        if (exists != null && exists > 0) {
            throw new RuntimeException("该餐次已添加过此食品");
        }

        meal.setWeek_day(weekDay);
        meal.setStatus(meal.getStatus() == null ? 1 : meal.getStatus());
        meal.setIs_deleted(0);
        mealMapper.insert(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        if (meal.getId() == null) {
            throw new RuntimeException("菜单ID不能为空");
        }
        if (mealMapper.selectById(meal.getId()) == null) {
            throw new RuntimeException("菜单记录不存在");
        }
        // 星期归一化
        if (meal.getWeek_day() != null && !meal.getWeek_day().isEmpty()) {
            String weekDay = WeekDayUtils.normalize(meal.getWeek_day());
            if (weekDay == null) {
                throw new RuntimeException("星期格式错误，应为 星期一~星期日");
            }
            meal.setWeek_day(weekDay);
        }
        // 餐次校验
        if (meal.getMeal_type() != null && (meal.getMeal_type() < 1 || meal.getMeal_type() > 3)) {
            throw new RuntimeException("餐次类型错误（1早餐 2午餐 3晚餐）");
        }
        mealMapper.updateById(meal);
    }

    @Override
    public void removeMeal(Integer id) {
        // 逻辑删除
        Meal meal = new Meal();
        meal.setId(id);
        meal.setIs_deleted(1);
        mealMapper.updateById(meal);
    }

    @Override
    public void updateMealStatus(Integer id, Integer status) {
        if (status == null || (status != 1 && status != 2)) {
            throw new RuntimeException("状态值错误（1:启用 2:停用）");
        }
        if (mealMapper.selectById(id) == null) {
            throw new RuntimeException("菜单记录不存在");
        }
        Meal meal = new Meal();
        meal.setId(id);
        meal.setStatus(status);
        mealMapper.updateById(meal);
    }
}
