package com.uav.ops.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.CruiseMapper;
import com.uav.ops.service.CruiseService;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author frp
 */
@Service
@Slf4j
public class CruiseServiceImpl implements CruiseService {

    @Autowired
    private CruiseMapper cruiseMapper;

    @Override
    public Page<CruiseLineResDTO> listCruiseLine(String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return cruiseMapper.listCruiseLine(pageReqDTO.of(), name);
    }

    @Override
    public CruiseLineResDTO getCruiseLineDetail(String id) {
        return cruiseMapper.getCruiseLineDetail(id);
    }

    @Override
    public void addCruiseLine(CruiseLineReqDTO cruiseLineReqDTO) {
        if (Objects.isNull(cruiseLineReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = cruiseMapper.selectCruiseLineIsExist(cruiseLineReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        cruiseLineReqDTO.setId(TokenUtil.getUuId());
        cruiseLineReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = cruiseMapper.addCruiseLine(cruiseLineReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void modifyCruiseLine(CruiseLineReqDTO cruiseLineReqDTO) {
        if (Objects.isNull(cruiseLineReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = cruiseMapper.selectCruiseLineIsExist(cruiseLineReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        cruiseLineReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = cruiseMapper.modifyCruiseLine(cruiseLineReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteCruiseLine(CruiseLineReqDTO cruiseLineReqDTO) {
        if (Objects.isNull(cruiseLineReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        cruiseLineReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = cruiseMapper.deleteCruiseLine(cruiseLineReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public Page<CruisePointResDTO> listCruisePoint(String lineId, String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return cruiseMapper.listCruisePoint(pageReqDTO.of(), lineId, name);
    }

    @Override
    public CruisePointResDTO getCruisePointDetail(String id) {
        return cruiseMapper.getCruisePointDetail(id);
    }

    @Override
    public void addCruisePoint(CruisePointReqDTO cruisePointReqDTO) {
        if (Objects.isNull(cruisePointReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = cruiseMapper.selectCruisePointIsExist(cruisePointReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        cruisePointReqDTO.setId(TokenUtil.getUuId());
        cruisePointReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = cruiseMapper.addCruisePoint(cruisePointReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void modifyCruisePoint(CruisePointReqDTO cruisePointReqDTO) {
        if (Objects.isNull(cruisePointReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = cruiseMapper.selectCruisePointIsExist(cruisePointReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        cruisePointReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = cruiseMapper.modifyCruisePoint(cruisePointReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteCruisePoint(CruisePointReqDTO cruisePointReqDTO) {
        if (Objects.isNull(cruisePointReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        cruisePointReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = cruiseMapper.deleteCruisePoint(cruisePointReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }
}
