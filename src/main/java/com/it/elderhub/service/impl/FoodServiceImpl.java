package com.it.elderhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.elderhub.entity.Food;
import com.it.elderhub.entity.Meal;
import com.it.elderhub.mapper.FoodMapper;
import com.it.elderhub.mapper.MealMapper;
import com.it.elderhub.service.FoodService;
import com.it.elderhub.utils.TagUtils;
import com.it.elderhub.vo.DailyMenuVO;
import com.it.elderhub.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 食品信息Service实现类
 */
@Service
public class FoodServiceImpl implements FoodService {

    // 周一~周日（下标 = DayOfWeek值 - 1）
    private static final String[] WEEK_NAMES = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private MealMapper mealMapper;

    @Override
    public IPage<Food> listFoods(Integer pageNum, Integer pageSize, String foodName, String foodType, Integer isHalal) {
        LambdaQueryWrapper<Food> wrapper = new LambdaQueryWrapper<>();
        // 按名称模糊查询
        if (foodName != null && !foodName.isEmpty()) {
            wrapper.like(Food::getFood_name, foodName);
        }
        // 按分类筛选
        if (foodType != null && !foodType.isEmpty()) {
            wrapper.eq(Food::getFood_type, foodType);
        }
        // 按清真筛选
        if (isHalal != null) {
            wrapper.eq(Food::getIs_halal, isHalal);
        }
        // 按ID升序
        wrapper.orderByAsc(Food::getId);

        Page<Food> page = new Page<>(pageNum, pageSize);
        return foodMapper.selectPage(page, wrapper);
    }

    @Override
    public Food getFoodById(Integer id) {
        return foodMapper.selectById(id);
    }

    @Override
    public void saveFood(Food food) {
        // 默认非清真
        if (food.getIs_halal() == null) {
            food.setIs_halal(0);
        }
        foodMapper.insert(food);
    }

    @Override
    public void updateFood(Food food) {
        if (food.getId() == null) {
            throw new RuntimeException("食品ID不能为空");
        }
        foodMapper.updateById(food);
    }

    @Override
    public void removeFood(Integer id) {
        // food 表无 is_deleted 字段，采用物理删除
        foodMapper.deleteById(id);
    }

    @Override
    public DailyMenuVO getDailyMenu(String date) {
        LocalDate day;
        try {
            day = LocalDate.parse(date);
        } catch (Exception e) {
            throw new RuntimeException("日期格式错误，应为 yyyy-MM-dd");
        }
        String weekDay = WEEK_NAMES[day.getDayOfWeek().getValue() - 1];

        // 查询该星期未删除的菜单
        LambdaQueryWrapper<Meal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Meal::getWeek_day, weekDay)
                .eq(Meal::getIs_deleted, 0)
                .orderByAsc(Meal::getMeal_type)
                .orderByAsc(Meal::getId);
        List<Meal> meals = mealMapper.selectList(wrapper);

        // 批量查询菜品信息，避免循环查库
        Map<Integer, Food> foodMap = new HashMap<>();
        List<Integer> foodIds = meals.stream()
                .map(Meal::getFood_id).filter(Objects::nonNull).distinct()
                .collect(Collectors.toList());
        if (!foodIds.isEmpty()) {
            foodMap = foodMapper.selectBatchIds(foodIds).stream()
                    .collect(Collectors.toMap(Food::getId, f -> f, (v1, v2) -> v1));
        }

        // 按餐次分组
        List<DishVO> breakfast = new ArrayList<>();
        List<DishVO> lunch = new ArrayList<>();
        List<DishVO> dinner = new ArrayList<>();
        for (Meal meal : meals) {
            if (meal.getMeal_type() == null) {
                continue;
            }
            DishVO dish = toDishVO(meal, foodMap.get(meal.getFood_id()));
            if (meal.getMeal_type() == 1) {
                breakfast.add(dish);
            } else if (meal.getMeal_type() == 2) {
                lunch.add(dish);
            } else if (meal.getMeal_type() == 3) {
                dinner.add(dish);
            }
        }

        DailyMenuVO vo = new DailyMenuVO();
        vo.setDate(day.toString());
        vo.setWeekDay(weekDay);
        vo.setBreakfast(breakfast);
        vo.setLunch(lunch);
        vo.setDinner(dinner);
        return vo;
    }

    private DishVO toDishVO(Meal meal, Food food) {
        DishVO vo = new DishVO();
        vo.setId(meal.getId());
        vo.setFoodId(meal.getFood_id());
        vo.setName(food == null ? null : food.getFood_name());
        vo.setPrice(food == null ? null : food.getPrice());
        vo.setNutrient_tags(food == null ? new ArrayList<>() : TagUtils.splitTags(food.getNutrient_tags()));
        vo.setAllergens(food == null ? new ArrayList<>() : TagUtils.splitTags(food.getAllergens()));
        vo.setTaste(meal.getTaste());
        return vo;
    }
}
