package com.uav.ops.controller;

import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePlanReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePlanResDTO;
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
import java.util.List;

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

    @GetMapping("/line/listAll")
    @ApiOperation(value = "获取全部巡航路线列表")
    public DataResponse<List<CruiseLineResDTO>> listAllCruiseLine() {
        return DataResponse.of(deviceService.listAllCruiseLine());
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
    public DataResponse<List<CruisePointResDTO>> listCruisePoint(@RequestParam String lineId,
                                                                @RequestParam(required = false) String name) {
        return DataResponse.of(deviceService.listCruisePoint(lineId, name));
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

    @GetMapping("/plan/list")
    @ApiOperation(value = "获取巡检计划列表")
    public PageResponse<CruisePlanResDTO> listCruisePlan(@RequestParam(required = false) String type,
                                                         @RequestParam(required = false) String name,
                                                         @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(deviceService.listCruisePlan(type, name, pageReqDTO));
    }

    @GetMapping("/plan/detail")
    @ApiOperation(value = "巡检计划详情获取")
    public DataResponse<CruisePlanResDTO> getCruisePlanDetail(@RequestParam String id) {
        return DataResponse.of(deviceService.getCruisePlanDetail(id));
    }

    @PostMapping("/plan/add")
    @ApiOperation(value = "新增巡检计划")
    public DataResponse<T> addCruisePlan(@RequestBody CruisePlanReqDTO cruisePlanReqDTO) {
        deviceService.addCruisePlan(cruisePlanReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/plan/modify")
    @ApiOperation(value = "修改巡检计划")
    public DataResponse<T> modifyCruisePlan(@RequestBody CruisePlanReqDTO cruisePlanReqDTO) {
        deviceService.modifyCruisePlan(cruisePlanReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/plan/delete")
    @ApiOperation(value = "删除巡检计划")
    public DataResponse<T> deleteCruisePlan(@RequestBody CruisePlanReqDTO cruisePlanReqDTO) {
        deviceService.deleteCruisePlan(cruisePlanReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/plan/exe")
    @ApiOperation(value = "执行巡检计划")
    public DataResponse<T> exeCruisePlan(@RequestParam String id) {
        deviceService.exeCruisePlan(id);
        return DataResponse.success();
    }
}
