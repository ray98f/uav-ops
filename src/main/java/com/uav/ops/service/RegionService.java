package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.RegionReqDTO;
import com.uav.ops.dto.req.RegionTypeReqDTO;
import com.uav.ops.dto.res.RegionResDTO;
import com.uav.ops.dto.res.RegionTypeResDTO;

import java.util.List;

/**
 * @author frp
 */
public interface RegionService {

    Page<RegionTypeResDTO> listRegionType(PageReqDTO pageReqDTO);

    List<RegionTypeResDTO> listAllRegionType();

    void modifyRegionType(RegionTypeReqDTO regionTypeReqDTO);

    void addRegionType(RegionTypeReqDTO regionTypeReqDTO);

    void deleteRegionType(RegionTypeReqDTO regionTypeReqDTO);

    List<RegionResDTO> listRegion();

    RegionResDTO getRegionDetail(String id);

    List<RegionResDTO> listAllRegion();

    RegionResDTO vxGetRegionBody(String id);

    void modifyRegion(RegionReqDTO regionReqDTO);

    void addRegion(RegionReqDTO regionReqDTO);

    void deleteRegion(RegionReqDTO regionReqDTO);

}
