package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.req.FlyHistoryReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.dto.res.FlyHistoryResDTO;
import com.uav.ops.entity.TFlyVideo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface FlyHistoryMapper {

    Page<FlyHistoryResDTO> listFlyHistory(Page<FlyHistoryResDTO> page, String name);

    FlyHistoryResDTO getFlyHistoryDetail(String id);

    Integer deleteFlyHistory(FlyHistoryReqDTO flyHistoryReqDTO);

    Integer addFlyHistory(FlyHistoryReqDTO flyHistoryReqDTO);

    Integer addFlyVideo(List<TFlyVideo> list);

    Integer closeFlyHistory(FlyHistoryReqDTO flyHistoryReqDTO);


}
