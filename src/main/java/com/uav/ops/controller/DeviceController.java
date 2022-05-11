package com.uav.ops.controller;

import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.DeviceReqDTO;
import com.uav.ops.dto.res.DeviceResDTO;
import com.uav.ops.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    public DataResponse<T> addDevice(@RequestBody DeviceReqDTO deviceReqDTO) {
        deviceService.addDevice(deviceReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改设备")
    public DataResponse<T> modifyDevice(@RequestBody DeviceReqDTO deviceReqDTO) {
        deviceService.modifyDevice(deviceReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除设备")
    public DataResponse<T> deleteDevice(@RequestBody DeviceReqDTO deviceReqDTO) {
        deviceService.deleteDevice(deviceReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/fault/add")
    @ApiOperation(value = "新增设备故障信息")
    public DataResponse<T> addDeviceFault(@RequestBody DeviceResDTO.DeviceFault deviceFault) {
        deviceService.addDeviceFault(deviceFault);
        return DataResponse.success();
    }
}
