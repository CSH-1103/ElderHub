package com.it.elderhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.elderhub.entity.Bed;
import com.it.elderhub.mapper.BedMapper;
import com.it.elderhub.query.bed.BedDetailsQuery;
import com.it.elderhub.service.BedService;
import com.it.elderhub.vo.bed.BedDetailsVO;
import com.it.elderhub.vo.bed.BedStatisticsVO;
import com.it.elderhub.vo.bed.RoomBedVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import com.it.elderhub.dto.bed.BedTransferDTO;
import org.springframework.transaction.annotation.Transactional;
import com.it.elderhub.mapper.RoomMapper;
import com.it.elderhub.mapper.BedDetailsMapper;



/**
 * @author Ljz
 * @description 针对表【bed(床位信息表)】的数据库操作Service实现
 * @createDate 2026‑10‑08
 */
@Service
@RequiredArgsConstructor
public class BedServiceImpl extends ServiceImpl<BedMapper, Bed> implements BedService {

    private final BedMapper bedMapper;
    private final RoomMapper roomMapper;
    private final BedDetailsMapper bedDetailsMapper;


    @Override
    public Page<BedDetailsVO> pageQuery(BedDetailsQuery query) {
        Page<BedDetailsVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        return bedMapper.selectBedPage(page, query);
    }

    @Override
    public BedStatisticsVO getStatistics() {
        return bedMapper.selectBedStatistics();
    }

    @Override
    public List<RoomBedVO> listBedGroupByRoom() {
        return bedMapper.selectGroupByRoom();
    }
    @Override
    @Transactional
    public void transferBed(BedTransferDTO dto) {
        Bed oldBed = getById(dto.getOldBedId());
        Bed newBed = getById(dto.getNewBedId());

        if(oldBed == null){
            throw new RuntimeException("旧床位不存在");
        }
        if(newBed == null){
            throw new RuntimeException("新床位不存在");
        }
        //新床位状态必须是空闲(1)，不是空闲抛出异常
        if(1 != newBed.getBedStatus()){
            throw new RuntimeException("新床位不是空闲状态，无法转入");
        }
        //旧床位必须是已入住(2)才可以转移
        if(2 != oldBed.getBedStatus()){
            throw new RuntimeException("旧床位未占用，无需转移");
        }
        //旧床位置为空闲1，清空客户id
        oldBed.setBedStatus(1);
        oldBed.setCustomerId(null);
        updateById(oldBed);

        //新床位置为已入住2，设置客户id
        newBed.setBedStatus(2);
        newBed.setCustomerId(dto.getCustomerId());
        updateById(newBed);
    }



}




