package com.uav.ops.controller;

import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePlanReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.req.CruiseWarnReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePlanResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.dto.res.CruiseWarnResDTO;
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
    private CruiseService cruiseService;

    @GetMapping("/line/list")
    @ApiOperation(value = "获取巡航路线列表")
    public PageResponse<CruiseLineResDTO> listCruiseLine(@RequestParam(required = false) String name,
                                                         @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(cruiseService.listCruiseLine(name, pageReqDTO));
    }

    @GetMapping("/line/listAll")
    @ApiOperation(value = "获取全部巡航路线列表")
    public DataResponse<List<CruiseLineResDTO>> listAllCruiseLine() {
        return DataResponse.of(cruiseService.listAllCruiseLine());
    }

    @GetMapping("/line/detail")
    @ApiOperation(value = "获取巡航路线详情")
    public DataResponse<CruiseLineResDTO> getCruiseLineDetail(@RequestParam String id) {
        return DataResponse.of(cruiseService.getCruiseLineDetail(id));
    }

    @PostMapping("/line/add")
    @ApiOperation(value = "新增巡航路线")
    public DataResponse<T> addCruiseLine(@RequestBody CruiseLineReqDTO cruiseLineReqDTO) {
        cruiseService.addCruiseLine(cruiseLineReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/line/modify")
    @ApiOperation(value = "修改巡航路线")
    public DataResponse<T> modifyCruiseLine(@RequestBody CruiseLineReqDTO cruiseLineReqDTO) {
        cruiseService.modifyCruiseLine(cruiseLineReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/line/delete")
    @ApiOperation(value = "删除巡航路线")
    public DataResponse<T> deleteCruiseLine(@RequestBody CruiseLineReqDTO cruiseLineReqDTO) {
        cruiseService.deleteCruiseLine(cruiseLineReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/point/list")
    @ApiOperation(value = "获取巡航点列表")
    public DataResponse<List<CruisePointResDTO>> listCruisePoint(@RequestParam String lineId,
                                                                 @RequestParam(required = false) String name) {
        return DataResponse.of(cruiseService.listCruisePoint(lineId, name));
    }

    @GetMapping("/point/detail")
    @ApiOperation(value = "巡航点详情获取")
    public DataResponse<CruisePointResDTO> getCruisePointDetail(@RequestParam String id) {
        return DataResponse.of(cruiseService.getCruisePointDetail(id));
    }

    @PostMapping("/point/add")
    @ApiOperation(value = "新增巡航点")
    public DataResponse<T> addCruisePoint(@RequestBody CruisePointReqDTO cruisePointReqDTO) {
        cruiseService.addCruisePoint(cruisePointReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/point/modify")
    @ApiOperation(value = "修改巡航点")
    public DataResponse<T> modifyCruisePoint(@RequestBody CruisePointReqDTO cruisePointReqDTO) {
        cruiseService.modifyCruisePoint(cruisePointReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/point/delete")
    @ApiOperation(value = "删除巡航点")
    public DataResponse<T> deleteCruisePoint(@RequestBody CruisePointReqDTO cruisePointReqDTO) {
        cruiseService.deleteCruisePoint(cruisePointReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/plan/list")
    @ApiOperation(value = "获取巡检计划列表")
    public PageResponse<CruisePlanResDTO> listCruisePlan(@RequestParam(required = false) Integer type,
                                                         @RequestParam(required = false) String name,
                                                         @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(cruiseService.listCruisePlan(type, name, pageReqDTO));
    }

    @GetMapping("/plan/detail")
    @ApiOperation(value = "巡检计划详情获取")
    public DataResponse<CruisePlanResDTO> getCruisePlanDetail(@RequestParam String id) {
        return DataResponse.of(cruiseService.getCruisePlanDetail(id));
    }

    @PostMapping("/plan/add")
    @ApiOperation(value = "新增巡检计划")
    public DataResponse<T> addCruisePlan(@RequestBody CruisePlanReqDTO cruisePlanReqDTO) {
        cruiseService.addCruisePlan(cruisePlanReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/plan/modify")
    @ApiOperation(value = "修改巡检计划")
    public DataResponse<T> modifyCruisePlan(@RequestBody CruisePlanReqDTO cruisePlanReqDTO) {
        cruiseService.modifyCruisePlan(cruisePlanReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/plan/delete")
    @ApiOperation(value = "删除巡检计划")
    public DataResponse<T> deleteCruisePlan(@RequestBody CruisePlanReqDTO cruisePlanReqDTO) {
        cruiseService.deleteCruisePlan(cruisePlanReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/plan/exe")
    @ApiOperation(value = "执行巡检计划")
    public DataResponse<T> exeCruisePlan(@RequestParam String id) {
        cruiseService.exeCruisePlan(id);
        return DataResponse.success();
    }

    @GetMapping("/warn/list")
    @ApiOperation(value = "获取巡检预警列表")
    public PageResponse<CruiseWarnResDTO> listCruiseWarn(@RequestParam(required = false) Integer type,
                                                         @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(cruiseService.listCruiseWarn(type, pageReqDTO));
    }

    @GetMapping("/warn/detail")
    @ApiOperation(value = "巡检预警详情获取")
    public DataResponse<CruiseWarnResDTO> getCruiseWarnDetail(@RequestParam String id) {
        return DataResponse.of(cruiseService.getCruiseWarnDetail(id));
    }

    @PostMapping("/warn/add")
    @ApiOperation(value = "新增巡检预警")
    public DataResponse<T> addCruiseWarn(@RequestBody CruiseWarnReqDTO cruiseWarnReqDTO) {
        cruiseService.addCruiseWarn(cruiseWarnReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/warn/modify")
    @ApiOperation(value = "处理巡检预警")
    public DataResponse<T> handleCruiseWarn(@RequestBody CruiseWarnReqDTO cruiseWarnReqDTO) {
        cruiseService.handleCruiseWarn(cruiseWarnReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/warn/delete")
    @ApiOperation(value = "删除巡检预警")
    public DataResponse<T> deleteCruiseWarn(@RequestBody CruiseWarnReqDTO cruiseWarnReqDTO) {
        cruiseService.deleteCruiseWarn(cruiseWarnReqDTO);
        return DataResponse.success();
    }
}
