package com.uav.ops.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.config.component.WebSocketServer;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.DeviceReqDTO;
import com.uav.ops.dto.res.CruisePlanResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.dto.res.DeviceResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.CruiseMapper;
import com.uav.ops.mapper.DeviceMapper;
import com.uav.ops.service.DeviceService;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private CruiseMapper cruiseMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public Page<DeviceResDTO> listDevice(String name, String deviceCode,PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        Page<DeviceResDTO> page = deviceMapper.listDevice(pageReqDTO.of(), name,deviceCode);
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
    public void operateDrone(String id, String operate, Boolean isLandingConfirmationNeeded, String planId, String lineId, String time) throws ParseException {
        String message = "";
        if ("startPlanLine".equals(operate)) {
            if (Objects.isNull(lineId) || Objects.isNull(planId) || Objects.isNull(time)) {
                throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date(System.currentTimeMillis());
            Date timeDate = sdf.parse(time);
            if (timeDate.after(beforeOneHourToNowDate(now)) && timeDate.before(afterOneHourToNowDate(now))) {
                List<CruisePointResDTO> list = cruiseMapper.listCruisePoint(lineId, null);
                message = "{\"event\":\"command\",\"data\":{\"command\":\"" + operate + "\",\"lindId\":\"" +
                        lineId + "\",\"detail\":" + JSONArray.toJSONString(list) + "}}";
            } else {
                throw new CommonException(ErrorCode.PLAN_TIME_ERROR);
            }
        } else if ("landing".equals(operate)) {
            message = "{\"event\":\"command\",\"data\":{\"command\":\"" + operate + "\",\"isLandingConfirmationNeeded\":" + isLandingConfirmationNeeded + "}}";
        } else {
            message = "{\"event\":\"command\",\"data\":{\"command\":\"" + operate + "\"}}";
        }
        webSocketServer.sendMessage(message, "app:" + id);
    }

    @Override
    public void insertFlyHistory(String deviceId, Date time) {

    }

    /**
     * 获取当前时间前一小时的时间
     *
     * @param date
     * @return java.util.Date
     */
    public static Date beforeOneHourToNowDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return calendar.getTime();
    }

    /**
     * 获取当前时间后一小时的时间
     *
     * @param date
     * @return java.util.Date
     */
    public static Date afterOneHourToNowDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }

}
