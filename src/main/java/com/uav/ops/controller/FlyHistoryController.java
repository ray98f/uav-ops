package com.uav.ops.controller;

import com.uav.ops.annotation.LogMaker;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.req.FlyHistoryReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.dto.res.FlyHistoryDataResDTO;
import com.uav.ops.dto.res.FlyHistoryResDTO;
import com.uav.ops.service.CruiseService;
import com.uav.ops.service.FlyHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/fly/history")
@Api(tags = "飞行记录管理")
@Validated
public class FlyHistoryController {

    @Resource
    private FlyHistoryService flyHistoryService;

    @GetMapping("/list")
    @ApiOperation(value = "获取飞行记录列表")
    public PageResponse<FlyHistoryResDTO> listFlyHistory(@RequestParam(required = false) String name,
                                                         @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(flyHistoryService.listFlyHistory(name, pageReqDTO));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取飞行记录详情")
    public DataResponse<FlyHistoryResDTO> getFlyHistoryDetail(@RequestParam String id) {
        return DataResponse.of(flyHistoryService.getFlyHistoryDetail(id));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除飞行记录")
    @LogMaker(value = "pc后台-删除飞行记录")
    public DataResponse<T> deleteFlyHistory(@RequestBody FlyHistoryReqDTO flyHistoryReqDTO) {
        flyHistoryService.deleteFlyHistory(flyHistoryReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/data/list")
    @ApiOperation(value = "获取飞行记录飞行数据列表")
    public DataResponse<List<FlyHistoryDataResDTO>> listFlyHistoryDataList(@RequestParam String startTime,
                                                                           @RequestParam String endTime,
                                                                           @RequestParam String deviceId) throws ParseException {
        return DataResponse.of(flyHistoryService.listFlyHistoryDataList(startTime, endTime, deviceId));
    }

    @PostMapping("/start")
    @ApiOperation(value = "创建飞行记录")
    @LogMaker(value = "pc后台-创建飞行记录")
    public DataResponse<T> startFly(@RequestBody FlyHistoryReqDTO flyHistoryReqDTO) {
        flyHistoryService.startFly(flyHistoryReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/close")
    @ApiOperation(value = "飞行结束")
    @LogMaker(value = "pc后台-飞行结束")
    public DataResponse<T> closeFly(@RequestBody FlyHistoryReqDTO flyHistoryReqDTO) {
        flyHistoryService.closeFly(flyHistoryReqDTO);
        return DataResponse.success();
    }
}
