package com.uav.ops.controller;

import com.uav.ops.annotation.LogMaker;
import com.uav.ops.dto.DataResponse;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.PageResponse;
import com.uav.ops.dto.req.PasswordReqDTO;
import com.uav.ops.dto.req.PostReqDTO;
import com.uav.ops.dto.req.UserReqDTO;
import com.uav.ops.dto.res.PostResDTO;
import com.uav.ops.dto.res.UserResDTO;
import com.uav.ops.entity.User;
import com.uav.ops.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 同步员工
     *
     * @return
     */
    @GetMapping("/sync")
    @ApiOperation(value = "同步员工")
    @LogMaker(value = "pc后台-同步员工")
    public DataResponse<T> syncUser() {
        userService.syncUser();
        return DataResponse.success();
    }

    /**
     * 修改密码
     *
     * @param passwordReqDTO 密码信息
     * @return DataResponse
     */
    @PostMapping("/change")
    @ApiOperation(value = "修改密码")
    @LogMaker(value = "pc后台-修改密码")
    public DataResponse<T> changePwd(@RequestBody @Valid PasswordReqDTO passwordReqDTO) throws Exception {
        userService.changePwd(passwordReqDTO);
        return DataResponse.success();
    }

    /**
     * 修改用户信息
     *
     * @param userReqDTO 用户修改信息
     * @return <T>
     */
    @PostMapping("/modify")
    @ApiOperation(value = "修改用户信息")
    @LogMaker(value = "pc后台-修改用户信息")
    public DataResponse<T> edit(@RequestBody @Valid UserReqDTO userReqDTO) throws Exception {
        userService.editUser(userReqDTO);
        return DataResponse.success();
    }

    /**
     * 获取所有用户
     */
    @GetMapping("/all")
    @ApiOperation(value = "获取所有用户")
    public DataResponse<List<UserResDTO>> listAllUser(){
        return DataResponse.of(userService.listAllUser());
    }

    /**
     * 查询用户列表
     * @param status
     * @param userRealName
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询用户列表")
    public DataResponse<List<UserResDTO>> listUser(@RequestParam(required = false) @ApiParam("状态") Integer status,
                                                   @RequestParam(required = false) @ApiParam("姓名") String userRealName,
                                                   @RequestParam(required = false) @ApiParam("组织机构id列表") List<String> deptIds) {
        return DataResponse.of(userService.listUser(status, userRealName, deptIds));
    }

    /**
     * 分页查询用户列表
     * @param status
     * @param userRealName
     * @param pageReqDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询用户列表")
    public PageResponse<UserResDTO> pageUser(@RequestParam(required = false) @ApiParam("状态") Integer status,
                                             @RequestParam(required = false) @ApiParam("姓名") String userRealName,
                                             @RequestParam(required = false) @ApiParam("组织机构id列表") List<String> deptIds,
                                             @Valid PageReqDTO pageReqDTO){
        return PageResponse.of(userService.pageUser(status, userRealName, deptIds, pageReqDTO));
    }

    @GetMapping("/post/list")
    @ApiOperation(value = "查询用户岗位列表")
    public DataResponse<List<PostResDTO>> listUserPost(@RequestParam String userId){
        return DataResponse.of(userService.listUserPost(userId));
    }

    @PostMapping("/post/modify")
    @ApiOperation(value = "修改用户岗位")
    @LogMaker(value = "pc后台-修改用户岗位")
    public DataResponse<T> modifyUserPost(@RequestBody PostReqDTO postReqDTO){
        userService.modifyUserPost(postReqDTO);
        return DataResponse.success();
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取用户信息")
    public DataResponse<UserResDTO> getUserInfo(@RequestParam String id){
        return DataResponse.of(userService.getUserInfo(id));
    }
}