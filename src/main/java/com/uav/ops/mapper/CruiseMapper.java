package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePlanReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.req.CruiseWarnReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePlanResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.dto.res.CruiseWarnResDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface CruiseMapper {

    Page<CruiseLineResDTO> listCruiseLine(Page<CruiseLineResDTO> page, String name);

    List<CruiseLineResDTO> listAllCruiseLine();

    CruiseLineResDTO getCruiseLineDetail(String id);

    Integer selectCruiseLineIsExist(CruiseLineReqDTO cruiseLineReqDTO);

    Integer addCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    Integer modifyCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    Integer deleteCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    List<CruisePointResDTO> listCruisePoint(String lineId, String name);

    CruisePointResDTO getCruisePointDetail(String id);

    Integer selectCruisePointIsExist(CruisePointReqDTO cruisePointReqDTO);

    Integer addCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    Integer modifyCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    Integer deleteCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    Page<CruisePlanResDTO> listCruisePlan(Page<CruisePlanResDTO> page, Integer type, String name);

    CruisePlanResDTO getCruisePlanDetail(String id);

    Integer selectCruisePlanIsExist(CruisePlanReqDTO cruisePlanReqDTO);

    Integer addCruisePlan(CruisePlanReqDTO cruisePlanReqDTO);

    Integer modifyCruisePlan(CruisePlanReqDTO cruisePlanReqDTO);

    Integer deleteCruisePlan(CruisePlanReqDTO cruisePlanReqDTO);

    Page<CruiseWarnResDTO> listCruiseWarn(Page<CruiseWarnResDTO> page, Integer type);

    CruiseWarnResDTO getCruiseWarnDetail(String id);

    Integer addCruiseWarn(CruiseWarnReqDTO cruiseWarnReqDTO);

    Integer handleCruiseWarn(CruiseWarnReqDTO cruiseWarnReqDTO);

    Integer deleteCruiseWarn(CruiseWarnReqDTO cruiseWarnReqDTO);
}
