package com.uav.ops.controller;

import com.uav.ops.annotation.LogMaker;
import com.uav.ops.dto.res.DeptTreeResDTO;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.res.UserResDTO;
import com.uav.ops.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author frp
 */
@Slf4j
@RestController
@RequestMapping("/dept")
@Api(tags = "组织机构管理")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @GetMapping("/sync")
    @ApiOperation(value = "同步企业微信组织机构")
    @LogMaker(value = "pc后台-同步企业微信组织机构")
    public DataResponse<T> syncDept() {
        deptService.syncDept();
        return DataResponse.success();
    }

    @GetMapping("/listTree")
    @ApiOperation(value = "公司层级结构获取")
    public DataResponse<List<DeptTreeResDTO>> listTree() {
        return DataResponse.of(deptService.listTree());
    }

    @GetMapping("/listFirst")
    @ApiOperation(value = "获取一级目录列表")
    public DataResponse<List<DeptTreeResDTO>> listFirst() {
        return DataResponse.of(deptService.listFirst());
    }

    @GetMapping("/getDeptUser")
    @ApiOperation(value = "获取最上级部门人员")
    public DataResponse<List<UserResDTO>> getDeptUser(@RequestParam String deptId,
                                                      @RequestParam(required = false) String dangerId) {
        return DataResponse.of(deptService.getDeptUser(deptId, dangerId));
    }
}
