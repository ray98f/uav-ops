package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;

/**
 * @author frp
 */
public interface CruiseService {

    Page<CruiseLineResDTO> listCruiseLine(String name, PageReqDTO pageReqDTO);

    CruiseLineResDTO getCruiseLineDetail(String id);

    void addCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    void modifyCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    void deleteCruiseLine(CruiseLineReqDTO cruiseLineReqDTO);

    Page<CruisePointResDTO> listCruisePoint(String lineId, String name, PageReqDTO pageReqDTO);

    CruisePointResDTO getCruisePointDetail(String id);

    void addCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    void modifyCruisePoint(CruisePointReqDTO cruisePointReqDTO);

    void deleteCruisePoint(CruisePointReqDTO cruisePointReqDTO);

}
