package com.uav.ops.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.RegionReqDTO;
import com.uav.ops.dto.req.RegionTypeReqDTO;
import com.uav.ops.dto.res.RegionResDTO;
import com.uav.ops.dto.res.RegionTypeResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.RegionMapper;
import com.uav.ops.service.RegionService;
import com.uav.ops.utils.TokenUtil;
import com.uav.ops.utils.treeTool.RegionTreeToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author frp
 */
@Service
@Slf4j
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Page<RegionTypeResDTO> listRegionType(PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return regionMapper.listRegionType(pageReqDTO.of());
    }

    @Override
    public List<RegionTypeResDTO> listAllRegionType() {
        return regionMapper.listAllRegionType();
    }

    @Override
    public void modifyRegionType(RegionTypeReqDTO transportReqDTO) {
        if (Objects.isNull(transportReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = regionMapper.selectRegionTypeIsExist(transportReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        transportReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = regionMapper.modifyRegionType(transportReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void addRegionType(RegionTypeReqDTO transportReqDTO) {
        if (Objects.isNull(transportReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = regionMapper.selectRegionTypeIsExist(transportReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        transportReqDTO.setId(TokenUtil.getUuId());
        transportReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = regionMapper.addRegionType(transportReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void deleteRegionType(RegionTypeReqDTO transportReqDTO) {
        if (Objects.isNull(transportReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        transportReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = regionMapper.deleteRegionType(transportReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }
    @Override
    public List<RegionResDTO> listRegion() {
        List<RegionResDTO> root = regionMapper.listRegionRoot();
        List<RegionResDTO> child = regionMapper.listRegionBody();
        RegionTreeToolUtils res = new RegionTreeToolUtils(root, child);
        return res.getTree();
    }

    @Override
    public RegionResDTO getRegionDetail(String id) {
        if (Objects.isNull(id)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        RegionResDTO regionResDTO = regionMapper.getRegionDetail(id);
        if (!Objects.isNull(regionResDTO)) {
            String name = regionMapper.selectParentNames(regionResDTO.getParentIds());
            regionResDTO.setParentNames(name == null || "".equals(name) ? regionResDTO.getName() : name + "," + regionResDTO.getName());
        }
        return regionResDTO;
    }

    @Override
    public List<RegionResDTO> listAllRegion(String typeId) {
        List<RegionResDTO> root = regionMapper.listAllRegionRoot(typeId);
        List<RegionResDTO> body = regionMapper.listAllRegionBody(typeId);
        RegionTreeToolUtils res = new RegionTreeToolUtils(root, body);
        return res.getTree();
    }

    @Override
    public RegionResDTO vxGetRegionBody(String id) {
        if (Objects.isNull(id)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        RegionResDTO regionResDTO = regionMapper.getRegionDetail(id);
        if (!Objects.isNull(regionResDTO)) {
            regionResDTO.setChildren(regionMapper.selectRegionBodyByType(id));
        }
        return regionResDTO;
    }

    @Override
    public void modifyRegion(RegionReqDTO regionReqDTO) {
        if (Objects.isNull(regionReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = regionMapper.selectRegionIsExist(regionReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        regionReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = regionMapper.modifyRegion(regionReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void addRegion(RegionReqDTO regionReqDTO) {
        if (Objects.isNull(regionReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = regionMapper.selectRegionIsExist(regionReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        regionReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = regionMapper.addRegion(regionReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void deleteRegion(RegionReqDTO regionReqDTO) {
        if (Objects.isNull(regionReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        regionReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = regionMapper.deleteRegion(regionReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }
}
