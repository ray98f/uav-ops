package com.uav.ops.controller;

import com.uav.ops.annotation.LogMaker;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.DangerReqDTO;
import com.uav.ops.dto.req.ProblemTypeReqDTO;
import com.uav.ops.dto.res.*;
import com.uav.ops.service.DangerService;
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

/**
 * @author frp
 */
@Slf4j
@RestController
@RequestMapping("/danger")
@Api(tags = "隐患管理")
@Validated
public class DangerController {

    @Resource
    private DangerService dangerService;

    @GetMapping("/type/list")
    @ApiOperation(value = "获取问题类型列表")
    public DataResponse<List<ProblemTypeResDTO>> listProblemType(@RequestParam(required = false) String name) {
        return DataResponse.of(dangerService.listProblemType(name));
    }

    @GetMapping("/type/listAll")
    @ApiOperation(value = "获取全部问题类型列表")
    public DataResponse<List<ProblemTypeResDTO>> listAllProblemType() {
        return DataResponse.of(dangerService.listAllProblemType());
    }

    @GetMapping("/type/detail")
    @ApiOperation(value = "获取问题类型详情")
    public DataResponse<ProblemTypeResDTO> getProblemTypeDetail(@RequestParam String id) {
        return DataResponse.of(dangerService.getProblemTypeDetail(id));
    }

    @PostMapping("/type/add")
    @ApiOperation(value = "新增问题类型")
    @LogMaker(value = "pc后台-新增问题类型")
    public DataResponse<T> addProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        dangerService.addProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/type/modify")
    @ApiOperation(value = "修改问题类型")
    @LogMaker(value = "pc后台-修改问题类型")
    public DataResponse<T> modifyProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        dangerService.modifyProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/type/delete")
    @ApiOperation(value = "删除问题类型")
    @LogMaker(value = "pc后台-删除问题类型")
    public DataResponse<T> deleteProblemType(@RequestBody ProblemTypeReqDTO problemTypeReqDTO) {
        dangerService.deleteProblemType(problemTypeReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取隐患列表")
    public PageResponse<DangerResDTO> listDanger(@RequestParam(required = false) @ApiParam(value = "区域id") String regionId,
                                                 @RequestParam(required = false) @ApiParam(value = "关键字") String searchKey,
                                                 @RequestParam(required = false) @ApiParam(value = "状态") Integer status,
                                                 @RequestParam(required = false) @ApiParam(value = "开始时间") String startTime,
                                                 @RequestParam(required = false) @ApiParam(value = "结束时间") String endTime,
                                                 @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(dangerService.listDanger(regionId, searchKey, status, startTime, endTime, pageReqDTO));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取隐患详情")
    public DataResponse<DangerResDTO> getDangerDetail(@RequestParam String id) {
        return DataResponse.of(dangerService.getDangerDetail(id));
    }

    @PostMapping("/modify")
    @ApiOperation(value = "隐患修改")
    @LogMaker(value = "问题检查问题排查管理修改")
    public DataResponse<T> modifyDanger(@RequestBody DangerReqDTO dangerReqDTO) {
        dangerService.modifyDanger(dangerReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/add")
    @ApiOperation(value = "隐患新增")
    @LogMaker(value = "问题检查问题排查管理新增")
    public DataResponse<T> addDanger(@RequestBody DangerReqDTO dangerReqDTO) {
        dangerService.addDanger(dangerReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "隐患删除")
    @LogMaker(value = "问题检查问题排查管理删除")
    public DataResponse<T> deleteDanger(@RequestBody DangerReqDTO dangerReqDTO) {
        dangerService.deleteDanger(dangerReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/visible/user/get")
    @ApiOperation(value = "获取隐患不可见范围")
    public DataResponse<List<UserResDTO>> getVisibleUser(@RequestParam @ApiParam(value = "隐患id") String id) {
        return DataResponse.of(dangerService.getVisibleUser(id));
    }

    @PostMapping("/visible/user/bind")
    @ApiOperation(value = "设置隐患不可见范围")
    public DataResponse<T> bindVisibleUser(@RequestBody DangerReqDTO dangerReqDTO) {
        dangerService.bindVisibleUser(dangerReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/rectify")
    @ApiOperation(value = "问题整改")
    @LogMaker(value = "问题检查问题排查管理问题整改")
    public DataResponse<T> rectifyDanger(@RequestParam @ApiParam(value = "隐患id") String dangerId,
                                         @RequestParam(required = false) @ApiParam(value = "是否消项 0 否 1 是") Integer isEliminate,
                                         @RequestParam(required = false) @ApiParam(value = "整改措施") String rectifyMeasure,
                                         @RequestParam(required = false) @ApiParam(value = "整改后图片") String afterPic) {
        dangerService.rectifyDanger(dangerId, isEliminate, rectifyMeasure, afterPic);
        return DataResponse.success();
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出隐患")
    @LogMaker(value = "问题检查问题排查管理导出")
    public void exportDanger(@RequestParam(required = false) @ApiParam(value = "区域id") String regionId,
                             @RequestParam(required = false) @ApiParam(value = "关键字") String searchKey,
                             @RequestParam(required = false) @ApiParam(value = "状态") Integer status,
                             @RequestParam(required = false) @ApiParam(value = "开始时间") String startTime,
                             @RequestParam(required = false) @ApiParam(value = "结束时间") String endTime,
                             HttpServletResponse response) {
        dangerService.exportDanger(regionId, searchKey, status, startTime, endTime, response);
    }

}