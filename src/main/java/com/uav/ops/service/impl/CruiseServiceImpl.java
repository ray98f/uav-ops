package com.uav.ops.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.config.component.WebSocketServer;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePlanReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.req.CruiseWarnReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePlanResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.dto.res.CruiseWarnResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.CruiseMapper;
import com.uav.ops.mapper.DeviceMapper;
import com.uav.ops.service.CruiseService;
import com.uav.ops.service.DeviceService;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author frp
 */
@Service
@Slf4j
public class CruiseServiceImpl implements CruiseService {

    @Autowired
    private CruiseMapper cruiseMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public Page<CruiseLineResDTO> listCruiseLine(String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return cruiseMapper.listCruiseLine(pageReqDTO.of(), name);
    }

    @Override
    public List<CruiseLineResDTO> listAllCruiseLine() {
        return cruiseMapper.listAllCruiseLine();
    }

    @Override
    public CruiseLineResDTO getCruiseLineDetail(String id) {
        return cruiseMapper.getCruiseLineDetail(id);
    }

    @Override
    public Map<String, Object> addCruiseLine(CruiseLineReqDTO cruiseLineReqDTO) {
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
        Map<String, Object> data = new HashMap<>();
        data.put("id", cruiseLineReqDTO.getId());
        return data;
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
    public List<CruisePointResDTO> listCruisePoint(String lineId, String name) {
        return cruiseMapper.listCruisePoint(lineId, name);
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

    @Override
    public Page<CruisePlanResDTO> listCruisePlan(Integer type, String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return cruiseMapper.listCruisePlan(pageReqDTO.of(), type, name);
    }

    @Override
    public List<CruisePlanResDTO> listDeviceCruisePlan(String deviceId) {
        return cruiseMapper.listDeviceCruisePlan(deviceId);
    }

    @Override
    public CruisePlanResDTO getCruisePlanDetail(String id) {
        CruisePlanResDTO resDTO = cruiseMapper.getCruisePlanDetail(id);
        if (!Objects.isNull(resDTO)) {
            resDTO.setLineInfo(cruiseMapper.getCruiseLineDetail(resDTO.getLineId()));
            resDTO.setDeviceInfo(deviceMapper.getDeviceDetail(resDTO.getDeviceId()));
        }
        return resDTO;
    }

    @Override
    public void addCruisePlan(CruisePlanReqDTO cruisePlanReqDTO) {
        if (Objects.isNull(cruisePlanReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = cruiseMapper.selectCruisePlanIsExist(cruisePlanReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        cruisePlanReqDTO.setId(TokenUtil.getUuId());
        cruisePlanReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = cruiseMapper.addCruisePlan(cruisePlanReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void modifyCruisePlan(CruisePlanReqDTO cruisePlanReqDTO) {
        if (Objects.isNull(cruisePlanReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = cruiseMapper.selectCruisePlanIsExist(cruisePlanReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        cruisePlanReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = cruiseMapper.modifyCruisePlan(cruisePlanReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteCruisePlan(CruisePlanReqDTO cruisePlanReqDTO) {
        if (Objects.isNull(cruisePlanReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        cruisePlanReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = cruiseMapper.deleteCruisePlan(cruisePlanReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public void exeCruisePlan(String id) {
        // todo 执行巡检计划
        CruisePlanResDTO res = cruiseMapper.getCruisePlanDetail(id);
        if (!Objects.isNull(res)) {
            webSocketServer.sendMessage("执行巡检计划:" + JSON.toJSONString(res), "app:" + res.getDeviceId());
        }
    }

    @Override
    public Page<CruiseWarnResDTO> listCruiseWarn(Integer type, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return cruiseMapper.listCruiseWarn(pageReqDTO.of(), type);
    }

    @Override
    public CruiseWarnResDTO getCruiseWarnDetail(String id) {
        return cruiseMapper.getCruiseWarnDetail(id);
    }

    @Override
    public void addCruiseWarn(CruiseWarnReqDTO cruiseWarnReqDTO) {
        if (Objects.isNull(cruiseWarnReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        if (!Objects.isNull(cruiseWarnReqDTO.getFaultInfo())) {
            cruiseWarnReqDTO.getFaultInfo().setId(TokenUtil.getUuId());
            Integer result = deviceMapper.addDeviceFault(cruiseWarnReqDTO.getFaultInfo(), TokenUtil.getCurrentPersonNo());
            if (result < 0) {
                throw new CommonException(ErrorCode.INSERT_ERROR);
            }
        }
        cruiseWarnReqDTO.setFaultId(cruiseWarnReqDTO.getFaultInfo().getId());
        cruiseWarnReqDTO.setId(TokenUtil.getUuId());
        cruiseWarnReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = cruiseMapper.addCruiseWarn(cruiseWarnReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void handleCruiseWarn(CruiseWarnReqDTO cruiseWarnReqDTO) {
        if (Objects.isNull(cruiseWarnReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        cruiseWarnReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = cruiseMapper.handleCruiseWarn(cruiseWarnReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteCruiseWarn(CruiseWarnReqDTO cruiseWarnReqDTO) {
        if (Objects.isNull(cruiseWarnReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        cruiseWarnReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = cruiseMapper.deleteCruiseWarn(cruiseWarnReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }
}
