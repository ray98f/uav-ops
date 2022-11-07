package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.req.RegionReqDTO;
import com.uav.ops.dto.req.RegionTypeReqDTO;
import com.uav.ops.dto.res.PostResDTO;
import com.uav.ops.dto.res.RegionResDTO;
import com.uav.ops.dto.res.RegionTypeResDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface RegionMapper {

    Page<RegionTypeResDTO> listRegionType(Page<PostResDTO> page);

    List<RegionTypeResDTO> listAllRegionType();

    List<RegionTypeResDTO> listFirstRegionType();

    Integer selectRegionTypeIsExist(RegionTypeReqDTO transportReqDTO);

    Integer modifyRegionType(RegionTypeReqDTO transportReqDTO);

    Integer addRegionType(RegionTypeReqDTO transportReqDTO);

    Integer deleteRegionType(RegionTypeReqDTO transportReqDTO);

    Page<RegionResDTO> listRegion(Page<PostResDTO> page);

    List<RegionResDTO> listRegionRoot();

    List<RegionResDTO> listRegionBody();

    List<RegionResDTO> listAllRegionRoot(String typeId);

    List<RegionResDTO> listAllRegionBody(String typeId);

    RegionResDTO getRegionDetail(String id);

    String selectParentNames(String parentIds);

    List<RegionResDTO> selectRegionRootByType(RegionTypeResDTO regionTypeResDTO);

    List<RegionResDTO> selectRegionBodyByType(String id);

    Integer selectRegionIsExist(RegionReqDTO regionReqDTO);

    Integer modifyRegion(RegionReqDTO regionReqDTO);

    Integer addRegion(RegionReqDTO regionReqDTO);

    Integer deleteRegion(RegionReqDTO regionReqDTO);

}
