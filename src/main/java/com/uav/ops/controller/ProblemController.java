package com.uav.ops.controller;

import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.ProblemIdentifyReqDTO;
import com.uav.ops.dto.req.ProblemReqDTO;
import com.uav.ops.dto.req.ProblemTypeReqDTO;
import com.uav.ops.dto.req.ProblemWarningReqDTO;
import com.uav.ops.dto.res.*;
import com.uav.ops.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/problem")
@Api(tags = "问题管理")
@Validated
public class ProblemController {

    @Resource
    private ProblemService problemService;

    @GetMapping("/type/list")
    @ApiOperation(value = "获取问题类型列表")
    public PageResponse<ProblemTypeResDTO> listProblemType(@RequestParam(required = false) String name,
                                                           @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listProblemType(name, pageReqDTO));
    }

    @GetMapping("/type/listAll")
    @ApiOperation(value = "获取全部问题类型列表")
    public DataResponse<List<ProblemTypeResDTO>> listAllProblemType() {
        return DataResponse.of(problemService.listAllProblemType());
    }

    @GetMapping("/type/detail")
    @ApiOperation(value = "获取问题类型详情")
    public DataResponse<ProblemTypeResDTO> getProblemTypeDetail(@RequestParam String id) {
        return DataResponse.of(problemService.getProblemTypeDetail(id));
    }

    @PostMapping("/type/add")
    @ApiOperation(value = "新增问题类型")
    public DataResponse<T> addProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        problemService.addProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/type/modify")
    @ApiOperation(value = "修改问题类型")
    public DataResponse<T> modifyProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        problemService.modifyProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/type/delete")
    @ApiOperation(value = "删除问题类型")
    public DataResponse<T> deleteProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        problemService.deleteProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/identify/list")
    @ApiOperation(value = "获取问题识别记录列表")
    public PageResponse<ProblemIdentifyResDTO> listProblemIdentify(@RequestParam(required = false) String name,
                                                                   @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listProblemIdentify(name, pageReqDTO));
    }

    @GetMapping("/identify/listNotBind")
    @ApiOperation(value = "获取未绑定问题识别记录列表")
    public PageResponse<ProblemIdentifyResDTO> listNotBindProblemIdentify(@RequestParam(required = false) String name,
                                                                          @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listNotBindProblemIdentify(name, pageReqDTO));
    }

    @GetMapping("/identify/detail")
    @ApiOperation(value = "获取问题识别记录详情")
    public DataResponse<ProblemIdentifyResDTO> getProblemIdentifyDetail(@RequestParam String id) {
        return DataResponse.of(problemService.getProblemIdentifyDetail(id));
    }

    @PostMapping("/identify/add")
    @ApiOperation(value = "新增问题识别记录")
    public DataResponse<T> addProblemIdentify(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.addProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/identify/modify")
    @ApiOperation(value = "修改问题识别记录")
    public DataResponse<T> modifyProblemIdentify(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.modifyProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/identify/delete")
    @ApiOperation(value = "删除问题识别记录")
    public DataResponse<T> deleteProblemIdentify(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.deleteProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取问题列表")
    public PageResponse<ProblemResDTO> listProblem(@RequestParam(required = false) String name,
                                                   @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listProblem(name, pageReqDTO));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取问题详情")
    public DataResponse<ProblemDetailResDTO> getProblemDetail(@RequestParam String id) {
        return DataResponse.of(problemService.getProblemDetail(id));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增问题")
    public DataResponse<T> addProblem(@RequestBody ProblemReqDTO problemReqDTO) {
        problemService.addProblem(problemReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改问题")
    public DataResponse<T> modifyProblem(@RequestBody ProblemReqDTO problemReqDTO) {
        problemService.modifyProblem(problemReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除问题")
    public DataResponse<T> deleteProblem(@RequestBody ProblemReqDTO problemReqDTO) {
        problemService.deleteProblem(problemReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/bind")
    @ApiOperation(value = "问题 绑定/解绑 问题识别记录")
    public DataResponse<T> bindProblem(@RequestParam @ApiParam(value = "问题id") String problemId,
                                       @RequestParam @ApiParam(value = "问题识别记录ids") List<String> identifyIds,
                                       @RequestParam @ApiParam(value = "类型 1 绑定 2 解绑") Integer type) {
        problemService.bindProblem(problemId, identifyIds, type);
        return DataResponse.success();
    }

    @GetMapping("/warn/list")
    @ApiOperation(value = "获取问题预警列表")
    public PageResponse<ProblemWarningResDTO> listProblemWarning(@Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listProblemWarning(pageReqDTO));
    }

    @GetMapping("/warn/detail")
    @ApiOperation(value = "获取问题预警详情")
    public DataResponse<ProblemWarningResDTO> getProblemWarningDetail(@RequestParam String id) {
        return DataResponse.of(problemService.getProblemWarningDetail(id));
    }

    @PostMapping("/warn/add")
    @ApiOperation(value = "新增问题预警")
    public DataResponse<T> addProblemWarning(@RequestBody ProblemWarningReqDTO problemWarningReqDTO) {
        problemService.addProblemWarning(problemWarningReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/warn/close")
    @ApiOperation(value = "关闭问题预警")
    public DataResponse<T> closeProblemWarning(@RequestBody ProblemWarningReqDTO problemWarningReqDTO) {
        problemService.closeProblemWarning(problemWarningReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/warn/delete")
    @ApiOperation(value = "删除问题预警")
    public DataResponse<T> deleteProblemWarning(@RequestBody ProblemWarningReqDTO problemWarningReqDTO) {
        problemService.deleteProblemWarning(problemWarningReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/statistics/month")
    @ApiOperation(value = "统计-每月新增问题数")
    public DataResponse<List<MonthlyProblemNumResDTO>> monthlyProblemNum() {
        return DataResponse.of(problemService.monthlyProblemNum());
    }

    @GetMapping("/statistics/proportion")
    @ApiOperation(value = "统计-问题占比")
    public DataResponse<Map<String, Object>> problemProportion() {
        return DataResponse.of(problemService.problemProportion());
    }

    @GetMapping("/statistics/type")
    @ApiOperation(value = "统计-各类新增问题数")
    public DataResponse<List<TypeProblemNumResDTO>> typeProblemNum() {
        return DataResponse.of(problemService.typeProblemNum());
    }
}
