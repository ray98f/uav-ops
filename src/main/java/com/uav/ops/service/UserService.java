package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.LoginReqDTO;
import com.uav.ops.dto.req.PasswordReqDTO;
import com.uav.ops.dto.req.PostReqDTO;
import com.uav.ops.dto.req.UserReqDTO;
import com.uav.ops.dto.res.PostResDTO;
import com.uav.ops.dto.res.UserResDTO;
import com.uav.ops.entity.User;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    /**
     * 同步员工
     *
     * @return
     */
    void syncUser();

    /**
     * 获取用户信息
     *
     * @param loginReqDTO
     * @return
     */
    UserReqDTO selectUserInfo(LoginReqDTO loginReqDTO) throws Exception;

    /**
     * 修改密码
     *
     * @param passwordReqDTO
     */
    void changePwd(PasswordReqDTO passwordReqDTO) throws Exception;

    /**
     * 编辑用户
     *
     * @param userReqDTO
     */
    void editUser(UserReqDTO userReqDTO) throws Exception;

    /**
     * 获取所有用户列表
     *
     * @return
     */
    List<UserResDTO> listAllUser();

    /**
     * 查询用户列表
     *
     * @param status
     * @param userRealName
     * @return
     */
    List<UserResDTO> listUser(Integer status, String userRealName, List<String> deptIds);

    /**
     * 查询用户列表
     *
     * @param status
     * @param userRealName
     * @param pageReqDTO
     * @return
     */
    Page<UserResDTO> pageUser(Integer status, String userRealName, List<String> deptIds, PageReqDTO pageReqDTO);

    List<PostResDTO> listUserPost(String userId);

    void modifyUserPost(PostReqDTO postReqDTO);

    UserResDTO getUserInfo(String id);
}
