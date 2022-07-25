package com.uav.ops.controller;

import com.uav.ops.annotation.LogMaker;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.*;
import com.uav.ops.dto.res.*;
import com.uav.ops.entity.Role;
import com.uav.ops.service.SysService;
import com.uav.ops.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author frp
 */
@Slf4j
@RestController
@RequestMapping("/sys")
@Api(tags = "系统管理")
@Validated
public class SysController {

    @Resource
    private SysService sysService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public DataResponse<Map<String, Object>> login(@RequestBody LoginReqDTO loginReqDTO) throws Exception {
        return DataResponse.of(sysService.login(loginReqDTO));
    }

    @PostMapping("/user/menu")
    @ApiOperation(value = "获取登录用户菜单列表")
    public DataResponse<List<MenuResDTO>> listUserMenu() {
        return DataResponse.of(sysService.listUserMenu(TokenUtil.getCurrentPersonNo()));
    }

    @GetMapping("/menu/list")
    @ApiOperation(value = "获取菜单列表")
    public PageResponse<MenuResDTO> listMenu(@RequestParam(required = false) Integer type,
                                             @RequestParam(required = false) String name,
                                             @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(sysService.listMenu(type, name, pageReqDTO));
    }

    @GetMapping("/menu/listAll")
    @ApiOperation(value = "获取所有菜单列表")
    public DataResponse<List<MenuResDTO>> listAllMenu() {
        return DataResponse.of(sysService.listAllMenu());
    }

    @PostMapping("/menu/modify")
    @ApiOperation(value = "菜单修改")
    @LogMaker(value = "pc后台-菜单修改")
    public DataResponse<T> modifyMenu(@RequestBody MenuReqDTO menuReqDTO) {
        sysService.modifyMenu(menuReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/menu/add")
    @ApiOperation(value = "菜单新增")
    @LogMaker(value = "pc后台-菜单新增")
    public DataResponse<T> addMenu(@RequestBody MenuReqDTO menuReqDTO) {
        sysService.addMenu(menuReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/menu/delete")
    @ApiOperation(value = "菜单删除")
    @LogMaker(value = "pc后台-菜单删除")
    public DataResponse<T> deleteMenu(@RequestBody MenuReqDTO menuReqDTO) {
        sysService.deleteMenu(menuReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/role/listAll")
    @ApiOperation(value = "获取所有角色信息")
    public DataResponse<List<Role>> listAllRole() {
        return DataResponse.of(sysService.listAllRole());
    }

    @GetMapping("/role/list")
    @ApiOperation(value = "分页获取角色信息")
    public PageResponse<Role> listRole(@RequestParam(required = false) @ApiParam("状态") Integer status,
                                       @RequestParam(required = false) @ApiParam("角色名称") String roleName,
                                       @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(sysService.listRole(status, roleName, pageReqDTO));
    }

    @PostMapping("/role/delete")
    @ApiOperation(value = "删除角色")
    @LogMaker(value = "pc后台-删除角色")
    public DataResponse<T> deleteRole(@RequestBody RoleReqDTO roleReqDTO) {
        sysService.deleteRole(roleReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/role/add")
    @ApiOperation(value = "新增角色")
    @LogMaker(value = "pc后台-新增角色")
    public DataResponse<T> insertRole(@RequestBody RoleReqDTO roleReqDTO) {
        sysService.insertRole(roleReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/role/modify")
    @ApiOperation(value = "修改角色")
    @LogMaker(value = "pc后台-修改角色")
    public DataResponse<T> updateRole(@RequestBody RoleReqDTO roleReqDTO) {
        sysService.updateRole(roleReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/menu/{roleId}")
    @ApiOperation(value = "获取角色对应菜单id")
    public DataResponse<List<String>> detailRoleMenu(@PathVariable String roleId) {
        return DataResponse.of(sysService.selectMenuIds(roleId));
    }

    @GetMapping("/role/user/list")
    @ApiOperation(value = "角色绑定人员列表")
    public PageResponse<UserResDTO> listUserRole(@RequestParam String roleId,
                                                 @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(sysService.listUserRole(roleId, pageReqDTO));
    }

    @PostMapping("/role/user/add")
    @ApiOperation(value = "新增角色绑定人员")
    @LogMaker(value = "pc后台-新增角色绑定人员")
    public DataResponse<T> addUserRole(@RequestBody UserRoleReqDTO userRoleReqDTO) {
        sysService.addUserRole(userRoleReqDTO);
        return DataResponse.success();
    }

    @PostMapping("/role/user/delete")
    @ApiOperation(value = "删除角色绑定人员")
    @LogMaker(value = "pc后台-删除角色绑定人员")
    public DataResponse<T> deleteUserRole(@RequestBody UserRoleReqDTO userRoleReqDTO) {
        sysService.deleteUserRole(userRoleReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/oper/log")
    @ApiOperation(value = "获取操作日志列表")
    public PageResponse<OperationLogResDTO> listOperLog(@RequestParam(required = false) String startTime,
                                                        @RequestParam(required = false) String endTime,
                                                        @RequestParam(required = false) String type,
                                                        @Valid PageReqDTO pageReqDTO) {
        return PageResponse.of(sysService.listOperLog(startTime, endTime, type, pageReqDTO));
    }

    @GetMapping("/menu")
    @ApiOperation(value = "menu新增初始数据")
    public DataResponse<T> menu() {
        sysService.menu();
        return DataResponse.success();
    }
}
