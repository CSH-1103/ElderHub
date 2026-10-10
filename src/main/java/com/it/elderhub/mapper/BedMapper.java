package com.it.elderhub.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.elderhub.entity.Bed;
import com.it.elderhub.query.bed.BedDetailsQuery;
import com.it.elderhub.vo.bed.BedDetailsVO;
import com.it.elderhub.vo.bed.BedStatisticsVO;
import com.it.elderhub.vo.bed.RoomBedVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BedMapper extends BaseMapper<Bed> {

    Page<BedDetailsVO> selectBedPage(Page<BedDetailsVO> page, @Param("query") BedDetailsQuery query);

    BedStatisticsVO selectBedStatistics();

    List<RoomBedVO> selectGroupByRoom();
}



