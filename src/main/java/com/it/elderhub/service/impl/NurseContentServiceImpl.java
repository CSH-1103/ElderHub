package com.it.elderhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.elderhub.entity.NurseContent;
import com.it.elderhub.entity.NurseLevelItem;
import com.it.elderhub.mapper.NurseContentMapper;
import com.it.elderhub.mapper.NurseLevelItemMapper;
import com.it.elderhub.service.NurseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 护理项目Service实现类
 */
@Service
public class NurseContentServiceImpl implements NurseContentService {

    @Autowired
    private NurseContentMapper nurseContentMapper;

    @Autowired
    private NurseLevelItemMapper nurseLevelItemMapper;

    @Override
    public IPage<NurseContent> listContents(Integer pageNum, Integer pageSize, Integer status, String nursingName) {
        // 构建查询条件
        LambdaQueryWrapper<NurseContent> wrapper = new LambdaQueryWrapper<>();
        // 只查未删除的
        wrapper.eq(NurseContent::getIs_deleted, 0);
        // 按状态筛选
        if (status != null) {
            wrapper.eq(NurseContent::getStatus, status);
        }
        // 按名称模糊查询
        if (nursingName != null && !nursingName.isEmpty()) {
            wrapper.like(NurseContent::getNursing_name, nursingName);
        }
        // 按ID倒序（表无 create_time 列）
        wrapper.orderByDesc(NurseContent::getId);

        // 执行分页查询
        Page<NurseContent> page = new Page<>(pageNum, pageSize);
        return nurseContentMapper.selectPage(page, wrapper);
    }

    @Override
    public void saveContent(NurseContent content) {
        // 新增为自增主键，忽略客户端可能带入的 id（否则会撞 PRIMARY 主键冲突）
        content.setId(null);
        // 编号不能为空，且在未删除的护理项目中必须唯一
        // （数据库原有 uk_serial_number 唯一索引与逻辑删除冲突——已删除项目会占用编号，故移除后在业务层校验）
        if (content.getSerial_number() == null || content.getSerial_number().isBlank()) {
            throw new RuntimeException("项目编号不能为空");
        }
        if (isSerialNumberDuplicated(content.getSerial_number(), null)) {
            throw new RuntimeException("项目编号已存在");
        }
        // 设置默认值
        if (content.getStatus() == null) {
            content.setStatus(1); // 默认启用
        }
        content.setIs_deleted(0);
        nurseContentMapper.insert(content);
    }

    /** 校验编号在未删除项目中是否重复（excludeId 用于修改时排除自身） */
    private boolean isSerialNumberDuplicated(String serialNumber, Integer excludeId) {
        LambdaQueryWrapper<NurseContent> wrapper = new LambdaQueryWrapper<NurseContent>()
                .eq(NurseContent::getSerial_number, serialNumber)
                .eq(NurseContent::getIs_deleted, 0);
        if (excludeId != null) {
            wrapper.ne(NurseContent::getId, excludeId);
        }
        Long count = nurseContentMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContent(NurseContent content) {
        if (content.getId() == null) {
            throw new RuntimeException("项目ID不能为空");
        }
        // 编号在未删除项目中必须唯一（排除自身）
        if (content.getSerial_number() != null && !content.getSerial_number().isBlank()
                && isSerialNumberDuplicated(content.getSerial_number(), content.getId())) {
            throw new RuntimeException("项目编号已存在");
        }
        nurseContentMapper.updateById(content);

        // 如果状态改为停用，自动从护理级别项目关联表中移除该项目
        if (content.getStatus() != null && content.getStatus() == 2) {
            LambdaQueryWrapper<NurseLevelItem> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(NurseLevelItem::getItem_id, content.getId());
            nurseLevelItemMapper.delete(wrapper);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeContent(Integer id) {
        // 1. 先删除护理级别项目关联表中的记录
        LambdaQueryWrapper<NurseLevelItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NurseLevelItem::getItem_id, id);
        nurseLevelItemMapper.delete(wrapper);

        // 2. 再逻辑删除护理项目
        NurseContent content = new NurseContent();
        content.setId(id);
        content.setIs_deleted(1);
        nurseContentMapper.updateById(content);
    }
}
