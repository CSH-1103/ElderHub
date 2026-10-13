package com.it.elderhub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.elderhub.entity.CustomerPreference;
import com.it.elderhub.vo.PreferenceVO;

import java.util.List;

/**
 * 客户饮食喜好Service接口
 */
public interface CustomerPreferenceService {

    /**
     * 分页查询客户饮食喜好
     * @param pageNum 页码（默认1）
     * @param pageSize 每页条数（默认10）
     * @param customerId 客户ID筛选（可空）
     * @return 分页结果
     */
    IPage<CustomerPreference> listPreferences(Integer pageNum, Integer pageSize, Integer customerId);

    /**
     * 查询饮食喜好详情
     * @param id 喜好ID
     * @return 饮食喜好信息
     */
    CustomerPreference getPreferenceById(Integer id);

    /**
     * 新增客户饮食喜好
     * @param preference 饮食喜好信息
     */
    void savePreference(CustomerPreference preference);

    /**
     * 修改客户饮食喜好
     * @param preference 饮食喜好信息（必须带ID）
     */
    void updatePreference(CustomerPreference preference);

    /**
     * 删除客户饮食喜好（逻辑删除）
     * @param id 喜好ID
     */
    void removePreference(Integer id);

    /**
     * 客户饮食偏好列表（含客户姓名、过敏原/忌口数组，供前端餐饮页客户下拉）
     * @return 全部在住客户的偏好
     */
    List<PreferenceVO> listCustomerPreferences();

    /**
     * 单个客户饮食偏好（含客户姓名、过敏原/忌口数组）
     * @param customerId 客户ID
     * @return 客户偏好
     */
    PreferenceVO getCustomerPreference(Integer customerId);
}
