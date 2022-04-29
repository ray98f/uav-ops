package com.uav.ops.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.DicDataReqDTO;
import com.uav.ops.dto.req.DicReqDTO;
import com.uav.ops.dto.res.DicDataResDTO;
import com.uav.ops.dto.res.DicResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.DicMapper;
import com.uav.ops.service.DicService;
import com.uav.ops.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicMapper dicMapper;

    @Override
    public Page<DicResDTO> getDics(PageReqDTO pageReqDTO, String name, String type, Integer isEnable) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return dicMapper.listDicType(pageReqDTO.of(), name, type, isEnable);
    }

    @Override
    public void addDic(DicReqDTO reqDTO) {
        if (Objects.isNull(reqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = dicMapper.selectDicTypeIsExist(reqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DIC_TYPE_EXIST);
        }
        reqDTO.setId(TokenUtil.getUuId());
        reqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        result = dicMapper.addDicType(reqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void updateDic(DicReqDTO reqDTO) {
        if (Objects.isNull(reqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = dicMapper.selectDicTypeIsExist(reqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DIC_TYPE_EXIST);
        }
        reqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        result = dicMapper.modifyDicType(reqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteDic(DicReqDTO reqDTO) {
        if (Objects.isNull(reqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        reqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        Integer result = dicMapper.deleteDicType(reqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public Page<DicDataResDTO> getDicDataByPage(PageReqDTO pageReqDTO, String type, String value, Integer isEnable) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return dicMapper.listDicData(pageReqDTO.of(), type, value, isEnable);
    }

    @Override
    public void addDicData(DicDataReqDTO reqDTO) {
        if (Objects.isNull(reqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = dicMapper.selectDicDataIsExist(reqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DIC_DATA_EXIST);
        }
        reqDTO.setId(TokenUtil.getUuId());
        reqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        result = dicMapper.addDicData(reqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void updateDicData(DicDataReqDTO reqDTO) {
        if (Objects.isNull(reqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = dicMapper.selectDicDataIsExist(reqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DIC_DATA_EXIST);
        }
        reqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        result = dicMapper.modifyDicData(reqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteDicData(DicDataReqDTO reqDTO) {
        if (Objects.isNull(reqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        reqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        Integer result = dicMapper.deleteDicData(reqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

}
