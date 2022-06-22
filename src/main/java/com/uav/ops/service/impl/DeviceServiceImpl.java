package com.uav.ops.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.config.component.WebSocketServer;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.DeviceReqDTO;
import com.uav.ops.dto.res.DeviceResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.DeviceMapper;
import com.uav.ops.service.DeviceService;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author frp
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public Page<DeviceResDTO> listDevice(String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        Page<DeviceResDTO> page = deviceMapper.listDevice(pageReqDTO.of(), name);
        List<DeviceResDTO> list = page.getRecords();
        if (list != null && !list.isEmpty()) {
            for (DeviceResDTO resDTO : list) {
                resDTO.setFault(deviceMapper.selectDeviceFault(resDTO.getId()));
            }
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public List<DeviceResDTO> listAllDevice() {
        List<DeviceResDTO> list = deviceMapper.listAllDevice();
        if (list != null && !list.isEmpty()) {
            for (DeviceResDTO resDTO : list) {
                resDTO.setFault(deviceMapper.selectDeviceFault(resDTO.getId()));
            }
        }
        return list;
    }

    @Override
    public List<DeviceResDTO> listAllUav(Integer status) {
        return deviceMapper.listAllUav(status);
    }

    @Override
    public DeviceResDTO getDeviceDetail(String id) {
        DeviceResDTO dto = deviceMapper.getDeviceDetail(id);
        if (!Objects.isNull(dto)) {
            dto.setFault(deviceMapper.selectDeviceFault(id));
        }
        return dto;
    }

    @Override
    public void addDevice(DeviceReqDTO deviceReqDTO) {
        if (Objects.isNull(deviceReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = deviceMapper.selectDeviceIsExist(deviceReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        deviceReqDTO.setId(TokenUtil.getUuId());
        deviceReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = deviceMapper.addDevice(deviceReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void modifyDevice(DeviceReqDTO deviceReqDTO) {
        if (Objects.isNull(deviceReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = deviceMapper.selectDeviceIsExist(deviceReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        deviceReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = deviceMapper.modifyDevice(deviceReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteDevice(DeviceReqDTO deviceReqDTO) {
        if (Objects.isNull(deviceReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        deviceReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = deviceMapper.deleteDevice(deviceReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public void addDeviceFault(DeviceResDTO.DeviceFault deviceFault) {
        if (Objects.isNull(deviceFault.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        deviceFault.setId(TokenUtil.getUuId());
        Integer result = deviceMapper.addDeviceFault(deviceFault, TokenUtil.getCurrentPersonNo());
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void operateDrone(String id, String operate) {
        webSocketServer.sendMessage("无人机控制:" + operate, "app:" + id);
    }
}
