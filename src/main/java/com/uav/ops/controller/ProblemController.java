package com.uav.ops.controller;

import com.uav.ops.annotation.LogMaker;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.*;
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
import javax.servlet.http.HttpServletResponse;
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
    public DataResponse<List<ProblemTypeResDTO>> listProblemType(@RequestParam(required = false) String name) {
        return DataResponse.of(problemService.listProblemType(name));
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
    @LogMaker(value = "pc后台-新增问题类型")
    public DataResponse<T> addProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        problemService.addProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/type/modify")
    @ApiOperation(value = "修改问题类型")
    @LogMaker(value = "pc后台-修改问题类型")
    public DataResponse<T> modifyProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        problemService.modifyProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/type/delete")
    @ApiOperation(value = "删除问题类型")
    @LogMaker(value = "pc后台-删除问题类型")
    public DataResponse<T> deleteProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        problemService.deleteProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/identify/list")
    @ApiOperation(value = "获取问题识别记录列表")
    public PageResponse<ProblemIdentifyResDTO> listProblemIdentify(@RequestParam(required = false) String name,
                                                                   @RequestParam(required = false) Integer isChecked,
                                                                   @RequestParam(required = false) Integer status,
                                                                   @RequestParam(required = false) String startTime,
                                                                   @RequestParam(required = false) String endTime,
                                                                   @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listProblemIdentify(name, isChecked, status, startTime, endTime, pageReqDTO));
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
    @LogMaker(value = "pc后台-新增问题识别记录")
    public DataResponse<T> addProblemIdentify(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.addProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/identify/modify")
    @ApiOperation(value = "修改问题识别记录")
    @LogMaker(value = "pc后台-修改问题识别记录")
    public DataResponse<T> modifyProblemIdentify(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.modifyProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/identify/delete")
    @ApiOperation(value = "删除问题识别记录")
    @LogMaker(value = "pc后台-删除问题识别记录")
    public DataResponse<T> deleteProblemIdentify(@RequestBody ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        problemService.deleteProblemIdentify(problemIdentifyReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出问题列表")
    public void exportProblem(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String startTime,
                              @RequestParam(required = false) String endTime,
                              @RequestParam(required = false) String typeId,
                              @RequestParam(required = false) Integer status,
                              HttpServletResponse response) {
        problemService.exportProblem(name, startTime, endTime, typeId, status, response);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取问题列表")
    public PageResponse<ProblemResDTO> listProblem(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String startTime,
                                                   @RequestParam(required = false) String endTime,
                                                   @RequestParam(required = false) String typeId,
                                                   @RequestParam(required = false) Integer status,
                                                   @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listProblem(name, startTime, endTime, typeId, status, pageReqDTO));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取问题详情")
    public DataResponse<ProblemDetailResDTO> getProblemDetail(@RequestParam String id) {
        return DataResponse.of(problemService.getProblemDetail(id));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增问题")
    @LogMaker(value = "pc后台-新增问题")
    public DataResponse<T> addProblem(@RequestBody ProblemReqDTO problemReqDTO) {
        problemService.addProblem(problemReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改问题")
    @LogMaker(value = "pc后台-修改问题")
    public DataResponse<T> modifyProblem(@RequestBody ProblemReqDTO problemReqDTO) {
        problemService.modifyProblem(problemReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除问题")
    @LogMaker(value = "pc后台-删除问题")
    public DataResponse<T> deleteProblem(@RequestBody ProblemReqDTO problemReqDTO) {
        problemService.deleteProblem(problemReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/rectify")
    @ApiOperation(value = "问题整改")
    @LogMaker(value = "pc后台-问题整改")
    public DataResponse<T> rectifyProblem(@RequestParam @ApiParam(value = "问题id") String problemId,
                                          @RequestParam(required = false) @ApiParam(value = "整改措施") String rectifyMeasure,
                                          @RequestParam(required = false) @ApiParam(value = "整改后图片") String afterPic,
                                          @RequestParam(required = false) @ApiParam(value = "备注") String remark) {
        problemService.rectifyProblem(problemId, rectifyMeasure, afterPic, remark);
        return DataResponse.success();
    }

    @GetMapping("/solve")
    @ApiOperation(value = "问题解决")
    @LogMaker(value = "pc后台-问题解决")
    public DataResponse<T> solveProblem(@RequestParam @ApiParam(value = "问题id") String problemId,
                                        @RequestParam(required = false) @ApiParam(value = "是否整改完成 0 否 1 是") Integer status) {
        problemService.solveProblem(problemId, status);
        return DataResponse.success();
    }

    @PostMapping("/bind")
    @ApiOperation(value = "问题 绑定/解绑 问题识别记录")
    @LogMaker(value = "pc后台-问题 绑定/解绑 问题识别记录")
    public DataResponse<T> bindProblem(@RequestBody ProblemBindReqDTO problemBindReqDTO) {
        problemService.bindProblem(problemBindReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/warn/list")
    @ApiOperation(value = "获取问题预警列表")
    public PageResponse<ProblemWarningResDTO> listProblemWarning(@RequestParam(required = false) String startTime,
                                                                 @RequestParam(required = false) String endTime,
                                                                 @RequestParam(required = false) Integer status,
                                                                 @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(problemService.listProblemWarning(startTime, endTime, status, pageReqDTO));
    }

    @GetMapping("/warn/detail")
    @ApiOperation(value = "获取问题预警详情")
    public DataResponse<ProblemWarningResDTO> getProblemWarningDetail(@RequestParam String id) {
        return DataResponse.of(problemService.getProblemWarningDetail(id));
    }

    @PostMapping("/warn/add")
    @ApiOperation(value = "新增问题预警")
    @LogMaker(value = "pc后台-新增问题预警")
    public DataResponse<T> addProblemWarning(@RequestBody ProblemWarningReqDTO problemWarningReqDTO) {
        problemService.addProblemWarning(problemWarningReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/warn/close")
    @ApiOperation(value = "关闭问题预警")
    @LogMaker(value = "pc后台-关闭问题预警")
    public DataResponse<T> closeProblemWarning(@RequestBody ProblemWarningReqDTO problemWarningReqDTO) {
        problemService.closeProblemWarning(problemWarningReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/warn/delete")
    @ApiOperation(value = "删除问题预警")
    @LogMaker(value = "pc后台-删除问题预警")
    public DataResponse<T> deleteProblemWarning(@RequestBody ProblemWarningReqDTO problemWarningReqDTO) {
        problemService.deleteProblemWarning(problemWarningReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/statistics/month")
    @ApiOperation(value = "统计-每月新增问题数")
    public DataResponse<List<MonthlyProblemNumResDTO>> monthlyProblemNum(@RequestParam String year) {
        return DataResponse.of(problemService.monthlyProblemNum(year));
    }

    @GetMapping("/statistics/typeProportion")
    @ApiOperation(value = "统计-类型问题占比")
    public DataResponse<Map<String, Object>> problemTypeProportion(@RequestParam String startTime,
                                                                   @RequestParam String endTime,
                                                                   @RequestParam(required = false) String typeIds) {
        return DataResponse.of(problemService.problemTypeProportion(startTime, endTime, typeIds));
    }

    @GetMapping("/statistics/proportion")
    @ApiOperation(value = "统计-问题占比")
    public DataResponse<Map<String, Object>> problemProportion(@RequestParam String month,
                                                               @RequestParam String typeId) {
        return DataResponse.of(problemService.problemProportion(month, typeId));
    }

    @GetMapping("/statistics/type")
    @ApiOperation(value = "统计-各类新增问题数")
    public DataResponse<List<TypeProblemNumResDTO>> typeProblemNum(@RequestParam String year) {
        return DataResponse.of(problemService.typeProblemNum(year));
    }

    @GetMapping("/statistics/ resolved")
    @ApiOperation(value = "统计-各类问题解决数")
    public DataResponse<List<TypeProblemNumResDTO>> resolvedNum(@RequestParam String year) {
        return DataResponse.of(problemService.resolvedNum(year));
    }
}
