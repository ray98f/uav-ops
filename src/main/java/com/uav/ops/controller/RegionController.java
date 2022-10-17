package com.uav.ops.controller;

import com.uav.ops.annotation.LogMaker;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.RegionReqDTO;
import com.uav.ops.dto.req.RegionTypeReqDTO;
import com.uav.ops.dto.res.RegionResDTO;
import com.uav.ops.dto.res.RegionTypeResDTO;
import com.uav.ops.service.RegionService;
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
@RequestMapping("/region")
@Api(tags = "区域管理")
@Validated
public class RegionController {

    @Resource
    private RegionService regionService;

    @GetMapping("/type/list")
    @ApiOperation(value = "获取区域类型列表")
    public PageResponse<RegionTypeResDTO> listRegionType(@Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(regionService.listRegionType(pageReqDTO));
    }

    @GetMapping("/type/listAll")
    @ApiOperation(value = "获取所有区域类型列表")
    public DataResponse<List<RegionTypeResDTO>> listAllRegionType() {
        return DataResponse.of(regionService.listAllRegionType());
    }

    @PostMapping("/type/modify")
    @ApiOperation(value = "区域类型修改")
    @LogMaker(value = "网页端-双重预防机制区域类型修改")
    public DataResponse<T> modifyRegionType(@RequestBody RegionTypeReqDTO regionTypeReqDTO) {
        regionService.modifyRegionType(regionTypeReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/type/add")
    @ApiOperation(value = "区域类型新增")
    @LogMaker(value = "网页端-双重预防机制区域类型新增")
    public DataResponse<T> addRegionType(@RequestBody RegionTypeReqDTO regionTypeReqDTO) {
        regionService.addRegionType(regionTypeReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/type/delete")
    @ApiOperation(value = "区域类型删除")
    @LogMaker(value = "网页端-双重预防机制区域类型删除")
    public DataResponse<T> deleteRegionType(@RequestBody RegionTypeReqDTO regionTypeReqDTO) {
        regionService.deleteRegionType(regionTypeReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取区域列表")
    public DataResponse<List<RegionResDTO>> listRegion() {
        return DataResponse.of(regionService.listRegion());
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取区域详情")
    public DataResponse<RegionResDTO> getRegionDetail(@RequestParam String id) {
        return DataResponse.of(regionService.getRegionDetail(id));
    }

    @GetMapping("/listAll")
    @ApiOperation(value = "获取所有区域列表")
    public DataResponse<List<RegionResDTO>> listAllRegion() {
        return DataResponse.of(regionService.listAllRegion());
    }

    @GetMapping("/vx/getBody")
    @ApiOperation(value = "微信端-获取一级区域子集列表")
    public DataResponse<RegionResDTO> vxGetRegionBody(@RequestParam String id) {
        return DataResponse.of(regionService.vxGetRegionBody(id));
    }

    @PostMapping("/modify")
    @ApiOperation(value = "区域修改")
    @LogMaker(value = "网页端-双重预防机制区域修改")
    public DataResponse<T> modifyRegion(@RequestBody RegionReqDTO regionReqDTO) {
        regionService.modifyRegion(regionReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/add")
    @ApiOperation(value = "区域新增")
    @LogMaker(value = "网页端-双重预防机制区域新增")
    public DataResponse<T> addRegion(@RequestBody RegionReqDTO regionReqDTO) {
        regionService.addRegion(regionReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "区域删除")
    @LogMaker(value = "网页端-双重预防机制区域删除")
    public DataResponse<T> deleteRegion(@RequestBody RegionReqDTO regionReqDTO) {
        regionService.deleteRegion(regionReqDTO);
        return DataResponse.success();
    }

}
