package com.uav.ops.controller;

import com.uav.ops.annotation.LogMaker;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.DeviceReqDTO;
import com.uav.ops.dto.res.DeviceResDTO;
import com.uav.ops.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

/**
 * @author frp
 */
@Slf4j
@RestController
@RequestMapping("/device")
@Api(tags = "设备管理")
@Validated
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    @GetMapping("/list")
    @ApiOperation(value = "获取设备列表")
    public PageResponse<DeviceResDTO> listDevice(@RequestParam(required = false) String name,
                                                 @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(deviceService.listDevice(name, pageReqDTO));
    }

    @GetMapping("/listAll")
    @ApiOperation(value = "获取设备列表")
    public DataResponse<List<DeviceResDTO>> listAllDevice() {
        return DataResponse.of(deviceService.listAllDevice());
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取设备详情")
    public DataResponse<DeviceResDTO> getDeviceDetail(@RequestParam String id) {
        return DataResponse.of(deviceService.getDeviceDetail(id));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增设备")
    @LogMaker(value = "pc后台-新增设备")
    public DataResponse<T> addDevice(@RequestBody DeviceReqDTO deviceReqDTO) {
        deviceService.addDevice(deviceReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改设备")
    @LogMaker(value = "pc后台-修改设备")
    public DataResponse<T> modifyDevice(@RequestBody DeviceReqDTO deviceReqDTO) {
        deviceService.modifyDevice(deviceReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除设备")
    @LogMaker(value = "pc后台-删除设备")
    public DataResponse<T> deleteDevice(@RequestBody DeviceReqDTO deviceReqDTO) {
        deviceService.deleteDevice(deviceReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/fault/add")
    @ApiOperation(value = "新增设备故障信息")
    @LogMaker(value = "pc后台-新增设备故障信息")
    public DataResponse<T> addDeviceFault(@RequestBody DeviceResDTO.DeviceFault deviceFault) {
        deviceService.addDeviceFault(deviceFault);
        return DataResponse.success();
    }

    @GetMapping("/operate")
    @ApiOperation(value = "无人机控制")
    @LogMaker(value = "pc后台-无人机控制")
    public DataResponse<T> operateDrone(@RequestParam @ApiParam(value = "无人机设备id") String id,
                                        @RequestParam @ApiParam(value = "无人机操作") String operate,
                                        @RequestParam(required = false) @ApiParam(value = "降落是否需要确认") Boolean isLandingConfirmationNeeded,
                                        @RequestParam(required = false) @ApiParam(value = "计划id") String planId,
                                        @RequestParam(required = false) @ApiParam(value = "线路id") String lineId,
                                        @RequestParam(required = false) @ApiParam(value = "计划时间") String time) throws ParseException {
        deviceService.operateDrone(id, operate, isLandingConfirmationNeeded, planId, lineId, time);
        return DataResponse.success();
    }
}
