package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.req.DicDataReqDTO;
import com.uav.ops.dto.req.DicReqDTO;
import com.uav.ops.dto.res.DicDataResDTO;
import com.uav.ops.dto.res.DicResDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DicMapper {

    Page<DicResDTO> listDicType(Page<DicResDTO> page, String name, String type, Integer isEnable);

    Integer selectDicTypeIsExist(DicReqDTO dicReqDTO);

    Integer addDicType(DicReqDTO dicReqDTO);

    Integer modifyDicType(DicReqDTO dicReqDTO);

    Integer deleteDicType(DicReqDTO dicReqDTO);

    Page<DicDataResDTO> listDicData(Page<DicDataResDTO> page, String type, String value, Integer isEnable);

    Integer selectDicDataIsExist(DicDataReqDTO dicDataReqDTO);

    Integer addDicData(DicDataReqDTO dicDataReqDTO);

    Integer modifyDicData(DicDataReqDTO dicDataReqDTO);

    Integer deleteDicData(DicDataReqDTO dicDataReqDTO);

}
