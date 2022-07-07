package com.uav.ops.controller;

import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.req.ProblemIdentifyReqDTO;
import com.uav.ops.dto.res.*;
import com.uav.ops.service.CruiseService;
import com.uav.ops.service.DeviceService;
import com.uav.ops.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author frp
 */
@Slf4j
@RestController
@RequestMapping("/app")
@Api(tags = "安卓app接口")
@Validated
public class AndroidAppController {

    @Resource
    private DeviceService deviceService;

    @Resource
    private CruiseService cruiseService;

    @Resource
    private ProblemService problemService;

    @GetMapping("/listAllUav")
    @ApiOperation(value = "获取无人机列表")
    public DataResponse<List<DeviceResDTO>> listAllUav(@RequestParam(required = false) Integer status) {
        return DataResponse.of(deviceService.listAllUav(status));
    }

    @GetMapping("/plan/list")
    @ApiOperation(value = "获取无人机巡检计划列表")
    public DataResponse<List<CruisePlanResDTO>> listDeviceCruisePlan(@RequestParam String deviceId) {
        return DataResponse.of(cruiseService.listDeviceCruisePlan(deviceId));
    }

    @GetMapping("/plan/detail")
    @ApiOperation(value = "巡检计划详情获取")
    public DataResponse<CruisePlanResDTO> getCruisePlanDetail(@RequestParam String id) {
        return DataResponse.of(cruiseService.getCruisePlanDetail(id));
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
    public DataResponse<Map<String, Object>> addCruiseLine(@RequestBody CruiseLineReqDTO cruiseLineReqDTO) {
        return DataResponse.of(cruiseService.addCruiseLine(cruiseLineReqDTO));
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

    @GetMapping("/check/list")
    @ApiOperation(value = "获取问题识别记录列表")
    public PageResponse<ProblemIdentifyResDTO> listProblemCheck(@RequestParam(required = false) String name,
                                                                @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listProblemIdentify(name, pageReqDTO));
    }

    @GetMapping("/check/detail")
    @ApiOperation(value = "获取问题识别记录详情")
    public DataResponse<ProblemIdentifyResDTO> getProblemCheckDetail(@RequestParam String id) {
        return DataResponse.of(problemService.getProblemIdentifyDetail(id));
    }

    @PostMapping("/check/add")
    @ApiOperation(value = "新增问题识别记录")
    public DataResponse<T> addProblemCheck(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.addProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/check/modify")
    @ApiOperation(value = "修改问题识别记录")
    public DataResponse<T> modifyProblemCheck(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.modifyProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/check/delete")
    @ApiOperation(value = "删除问题识别记录")
    public DataResponse<T> deleteProblemCheck(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.deleteProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }
}
