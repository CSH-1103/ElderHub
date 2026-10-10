package com.it.elderhub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.elderhub.entity.Bed;
import com.it.elderhub.query.bed.BedDetailsQuery;
import com.it.elderhub.vo.bed.BedDetailsVO;
import com.it.elderhub.vo.bed.BedStatisticsVO;
import com.it.elderhub.vo.bed.RoomBedVO;
import java.util.List;
import com.it.elderhub.dto.bed.BedTransferDTO;


/**
 * @author Ljz
 * @description 针对表【bed(床位信息表)】的数据库操作Service
 * @createDate 2026‑10‑08 09:52:05
 */
public interface BedService extends IService<Bed> {

    /**
     * 床位分页查询
     * @param query 查询条件
     * @return 分页数据
     */
    Page<BedDetailsVO> pageQuery(BedDetailsQuery query);

    /**
     * 获取床位统计：空闲、占用、外出
     * @return 统计对象
     */
    BedStatisticsVO getStatistics();

    /**
     * 按房间分组查询床位信息
     * @return 房间床位列表
     */
    List<RoomBedVO> listBedGroupByRoom();
    /**
     * 床位转移更换老人床位
     * @param dto 转移参数
     */
    void transferBed(BedTransferDTO dto);

}
