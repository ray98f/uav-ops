package com.uav.ops.controller;

import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.service.CruiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author frp
 */
@Slf4j
@RestController
@RequestMapping("/cruise")
@Api(tags = "巡航管理")
@Validated
public class CruiseController {

    @Resource
    private CruiseService deviceService;

    @GetMapping("/line/list")
    @ApiOperation(value = "获取巡航路线列表")
    public PageResponse<CruiseLineResDTO> listCruiseLine(@RequestParam(required = false) String name,
                                                         @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(deviceService.listCruiseLine(name, pageReqDTO));
    }

    @GetMapping("/line/detail")
    @ApiOperation(value = "获取巡航路线详情")
    public DataResponse<CruiseLineResDTO> getCruiseLineDetail(@RequestParam String id) {
        return DataResponse.of(deviceService.getCruiseLineDetail(id));
    }

    @PostMapping("/line/add")
    @ApiOperation(value = "新增巡航路线")
    public DataResponse<T> addCruiseLine(@RequestBody CruiseLineReqDTO cruiseLineReqDTO) {
        deviceService.addCruiseLine(cruiseLineReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/line/modify")
    @ApiOperation(value = "修改巡航路线")
    public DataResponse<T> modifyCruiseLine(@RequestBody CruiseLineReqDTO cruiseLineReqDTO) {
        deviceService.modifyCruiseLine(cruiseLineReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/line/delete")
    @ApiOperation(value = "删除巡航路线")
    public DataResponse<T> deleteCruiseLine(@RequestBody CruiseLineReqDTO cruiseLineReqDTO) {
        deviceService.deleteCruiseLine(cruiseLineReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/point/list")
    @ApiOperation(value = "获取巡航点列表")
    public PageResponse<CruisePointResDTO> listCruisePoint(@RequestParam String lineId,
                                                           @RequestParam(required = false) String name,
                                                           @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(deviceService.listCruisePoint(lineId, name, pageReqDTO));
    }

    @GetMapping("/point/detail")
    @ApiOperation(value = "巡航点详情获取")
    public DataResponse<CruisePointResDTO> getCruisePointDetail(@RequestParam String id) {
        return DataResponse.of(deviceService.getCruisePointDetail(id));
    }

    @PostMapping("/point/add")
    @ApiOperation(value = "新增巡航点")
    public DataResponse<T> addCruisePoint(@RequestBody CruisePointReqDTO cruisePointReqDTO) {
        deviceService.addCruisePoint(cruisePointReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/point/modify")
    @ApiOperation(value = "修改巡航点")
    public DataResponse<T> modifyCruisePoint(@RequestBody CruisePointReqDTO cruisePointReqDTO) {
        deviceService.modifyCruisePoint(cruisePointReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/point/delete")
    @ApiOperation(value = "删除巡航点")
    public DataResponse<T> deleteCruisePoint(@RequestBody CruisePointReqDTO cruisePointReqDTO) {
        deviceService.deleteCruisePoint(cruisePointReqDTO);
        return DataResponse.success();
    }
}
