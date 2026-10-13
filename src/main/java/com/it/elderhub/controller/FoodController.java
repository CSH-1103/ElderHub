package com.it.elderhub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.elderhub.common.Result;
import com.it.elderhub.entity.Food;
import com.it.elderhub.service.CustomerPreferenceService;
import com.it.elderhub.service.FoodService;
import com.it.elderhub.vo.DailyMenuVO;
import com.it.elderhub.vo.PreferenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食品信息Controller
 */
@RestController
@RequestMapping("/admin/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private CustomerPreferenceService customerPreferenceService;

    /**
     * 分页查询食品
     * @param pageNum 页码（默认1）
     * @param pageSize 每页条数（默认10）
     * @param foodName 食品名称模糊查询（可空）
     * @param foodType 食品分类筛选（可空）
     * @param isHalal 是否清真筛选（0:否 1:是，可空）
     */
    @GetMapping("/list")
    public Result<IPage<Food>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            String foodName,
            String foodType,
            Integer isHalal) {
        return Result.success(foodService.listFoods(pageNum, pageSize, foodName, foodType, isHalal));
    }

    /**
     * 查询食品详情
     * @param id 食品ID
     */
    @GetMapping("/{id}")
    public Result<Food> detail(@PathVariable Integer id) {
        return Result.success(foodService.getFoodById(id));
    }

    /**
     * 新增食品
     */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody Food food) {
        foodService.saveFood(food);
        return Result.success();
    }

    /**
     * 修改食品
     */
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Food food) {
        foodService.updateFood(food);
        return Result.success();
    }

    /**
     * 删除食品（物理删除）
     * @param id 食品ID
     */
    @DeleteMapping("/remove/{id}")
    public Result<Void> remove(@PathVariable Integer id) {
        foodService.removeFood(id);
        return Result.success();
    }

    /**
     * 按日期查询每日菜单（三餐，关联菜品与营养标签）
     * 供前端「餐饮管理」页面使用
     * @param date 日期（yyyy-MM-dd）
     */
    @GetMapping("/menu")
    public Result<DailyMenuVO> menu(@RequestParam String date) {
        return Result.success(foodService.getDailyMenu(date));
    }

    /**
     * 客户饮食偏好列表（含客户姓名、过敏原/忌口数组）
     * 供前端「餐饮管理」页面客户下拉使用
     */
    @GetMapping("/preferences")
    public Result<List<PreferenceVO>> preferences() {
        return Result.success(customerPreferenceService.listCustomerPreferences());
    }

    /**
     * 单个客户饮食偏好（含客户姓名、过敏原/忌口数组）
     * @param customerId 客户ID
     */
    @GetMapping("/preference/{customerId}")
    public Result<PreferenceVO> preference(@PathVariable Integer customerId) {
        return Result.success(customerPreferenceService.getCustomerPreference(customerId));
    }
}
