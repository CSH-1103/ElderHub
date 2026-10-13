package com.it.elderhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.elderhub.entity.Customer;
import com.it.elderhub.entity.CustomerPreference;
import com.it.elderhub.mapper.CustomerMapper;
import com.it.elderhub.mapper.CustomerPreferenceMapper;
import com.it.elderhub.service.CustomerPreferenceService;
import com.it.elderhub.utils.TagUtils;
import com.it.elderhub.vo.PreferenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户饮食喜好Service实现类
 */
@Service
public class CustomerPreferenceServiceImpl implements CustomerPreferenceService {

    @Autowired
    private CustomerPreferenceMapper customerPreferenceMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public IPage<CustomerPreference> listPreferences(Integer pageNum, Integer pageSize, Integer customerId) {
        LambdaQueryWrapper<CustomerPreference> wrapper = new LambdaQueryWrapper<>();
        // 只查未删除的
        wrapper.eq(CustomerPreference::getIs_deleted, 0);
        // 按客户筛选
        if (customerId != null) {
            wrapper.eq(CustomerPreference::getCustomer_id, customerId);
        }
        // 按ID升序
        wrapper.orderByAsc(CustomerPreference::getId);

        Page<CustomerPreference> page = new Page<>(pageNum, pageSize);
        return customerPreferenceMapper.selectPage(page, wrapper);
    }

    @Override
    public CustomerPreference getPreferenceById(Integer id) {
        return customerPreferenceMapper.selectById(id);
    }

    @Override
    public void savePreference(CustomerPreference preference) {
        preference.setIs_deleted(0);
        customerPreferenceMapper.insert(preference);
    }

    @Override
    public void updatePreference(CustomerPreference preference) {
        if (preference.getId() == null) {
            throw new RuntimeException("喜好ID不能为空");
        }
        customerPreferenceMapper.updateById(preference);
    }

    @Override
    public void removePreference(Integer id) {
        // 逻辑删除
        CustomerPreference preference = new CustomerPreference();
        preference.setId(id);
        preference.setIs_deleted(1);
        customerPreferenceMapper.updateById(preference);
    }

    @Override
    public List<PreferenceVO> listCustomerPreferences() {
        // 全部在住客户
        LambdaQueryWrapper<Customer> customerWrapper = new LambdaQueryWrapper<>();
        customerWrapper.eq(Customer::getIs_deleted, 0).orderByAsc(Customer::getId);
        List<Customer> customers = customerMapper.selectList(customerWrapper);

        // 未删除的偏好记录（按客户ID去重，保留最早一条）
        LambdaQueryWrapper<CustomerPreference> preferenceWrapper = new LambdaQueryWrapper<>();
        preferenceWrapper.eq(CustomerPreference::getIs_deleted, 0);
        List<CustomerPreference> preferences = customerPreferenceMapper.selectList(preferenceWrapper);
        Map<Integer, CustomerPreference> preferenceMap = preferences.stream()
                .filter(p -> p.getCustomer_id() != null)
                .collect(Collectors.toMap(CustomerPreference::getCustomer_id, p -> p, (v1, v2) -> v1));

        return customers.stream()
                .map(c -> toPreferenceVO(c.getId(), c.getCustomer_name(), preferenceMap.get(c.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public PreferenceVO getCustomerPreference(Integer customerId) {
        Customer customer = customerMapper.selectById(customerId);
        if (customer == null || (customer.getIs_deleted() != null && customer.getIs_deleted() == 1)) {
            throw new RuntimeException("客户不存在");
        }
        LambdaQueryWrapper<CustomerPreference> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomerPreference::getCustomer_id, customerId)
                .eq(CustomerPreference::getIs_deleted, 0)
                .orderByAsc(CustomerPreference::getId)
                .last("LIMIT 1");
        CustomerPreference preference = customerPreferenceMapper.selectOne(wrapper);
        return toPreferenceVO(customerId, customer.getCustomer_name(), preference);
    }

    private PreferenceVO toPreferenceVO(Integer customerId, String customerName, CustomerPreference preference) {
        PreferenceVO vo = new PreferenceVO();
        vo.setId(customerId);
        vo.setCustomer_name(customerName);
        vo.setAllergies(preference == null ? new ArrayList<>() : TagUtils.splitTags(preference.getAllergies()));
        vo.setAvoid_tags(preference == null ? new ArrayList<>() : TagUtils.splitTags(preference.getAttention()));
        vo.setNotes(preference == null ? null : preference.getRemark());
        return vo;
    }
}
