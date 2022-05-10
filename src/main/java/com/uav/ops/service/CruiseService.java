package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePlanReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePlanResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;

import java.util.List;

/**
 * @author frp
 */
public interface CruiseService {

    Page<CruiseLineResDTO> listCruiseLine(String name, PageReqDTO pageReqDTO);

    List<CruiseLineResDTO> listAllCruiseLine();

    CruiseLineResDTO getCruiseLineDetail(String id);

    void addCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    void modifyCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    void deleteCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    List<CruisePointResDTO> listCruisePoint(String lineId, String name);

    CruisePointResDTO getCruisePointDetail(String id);

    void addCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    void modifyCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    void deleteCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    Page<CruisePlanResDTO> listCruisePlan(String type, String name, PageReqDTO pageReqDTO);

    CruisePlanResDTO getCruisePlanDetail(String id);

    void addCruisePlan(CruisePlanReqDTO cruisePlanReqDTO);

    void modifyCruisePlan(CruisePlanReqDTO cruisePlanReqDTO);

    void deleteCruisePlan(CruisePlanReqDTO cruisePlanReqDTO);

    void exeCruisePlan(String id);

}
