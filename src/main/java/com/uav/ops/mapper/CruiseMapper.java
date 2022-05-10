package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author frp
 */
@Mapper
@Repository
public interface CruiseMapper {

    Page<CruiseLineResDTO> listCruiseLine(Page<CruiseLineResDTO> page, String name);

    CruiseLineResDTO getCruiseLineDetail(String id);

    Integer selectCruiseLineIsExist(CruiseLineReqDTO cruiseLineReqDTO);

    Integer addCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    Integer modifyCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    Integer deleteCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    Page<CruisePointResDTO> listCruisePoint(Page<CruisePointResDTO> page, String lineId, String name);

    CruisePointResDTO getCruisePointDetail(String id);

    Integer selectCruisePointIsExist(CruisePointReqDTO cruisePointReqDTO);

    Integer addCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    Integer modifyCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    Integer deleteCruisePoint(CruisePointReqDTO cruisePointReqDTO);
}
