package com.uav.ops.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.LoginReqDTO;
import com.uav.ops.dto.req.PasswordReqDTO;
import com.uav.ops.dto.req.PostReqDTO;
import com.uav.ops.dto.req.UserReqDTO;
import com.uav.ops.dto.res.PostResDTO;
import com.uav.ops.dto.res.UserResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.PostMapper;
import com.uav.ops.mapper.UserMapper;
import com.uav.ops.service.UserService;
import com.uav.ops.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Value("${open.api.eip-service.host}")
    private String securityInfoManageServiceHost;

    @Value("${open.api.eip-service.port}")
    private String securityInfoManageServicePort;

    @Value("${open.api.eip-service.url}")
    private String securityInfoManageServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 同步员工
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncUser() {
        String url = securityInfoManageServiceHost + ":" + securityInfoManageServicePort + securityInfoManageServiceUrl
                + "/user/sync/list";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url)
                .build()
                .expand()
                .encode();
        URI uri = uriComponents.toUri();
        JSONObject res = restTemplate.getForEntity(uri, JSONObject.class).getBody();
        if (!Constants.SUCCESS.equals(Objects.requireNonNull(res).getString(Constants.CODE))) {
            throw new CommonException(ErrorCode.SYNC_ERROR);
        }
        if (res.getJSONArray(Constants.DATA) == null) {
            return;
        }
        List<UserResDTO> list = JSONArray.parseArray(res.getJSONArray(Constants.DATA).toJSONString(), UserResDTO.class);
        if (list != null && !list.isEmpty()) {
            userMapper.syncUser(list, TokenUtil.getCurrentPersonNo());
        }
    }

    @Override
    public UserReqDTO selectUserInfo(LoginReqDTO loginReqDTO) throws Exception {
        if (Objects.isNull(loginReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        UserReqDTO userInfo = userMapper.selectUserInfo(null, loginReqDTO.getUserName());
        if (Objects.isNull(userInfo)) {
            throw new CommonException(ErrorCode.LOGIN_PASSWORD_ERROR);
        }
        if (userInfo.getStatus() == 1) {
            throw new CommonException(ErrorCode.USER_DISABLE);
        }
        if (!loginReqDTO.getPassword().equals(MyAESUtil.decrypt(userInfo.getPassword()))) {
            throw new CommonException(ErrorCode.LOGIN_PASSWORD_ERROR);
        }
        userInfo.setRoleIds(userMapper.selectUserRoles(userInfo.getId()));
        return userInfo;
    }

    @Override
    public void changePwd(PasswordReqDTO passwordReqDTO) throws Exception {
        if (Objects.isNull(passwordReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        if (!MyAESUtil.encrypt(passwordReqDTO.getOldPwd()).equals(userMapper.selectOldPassword(passwordReqDTO))) {
            throw new CommonException(ErrorCode.PWD_ERROR);
        }
        passwordReqDTO.setOldPwd(MyAESUtil.encrypt(passwordReqDTO.getOldPwd()));
        passwordReqDTO.setNewPwd(MyAESUtil.encrypt(passwordReqDTO.getNewPwd()));
        int result = userMapper.changePwd(passwordReqDTO, TokenUtil.getCurrentUserName());
        if (result < 0) {
            throw new CommonException(ErrorCode.USER_PWD_CHANGE_FAIL);
        }
    }

    @Override
    public void editUser(UserReqDTO userReqDTO) throws Exception {
        if (Objects.isNull(userReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        userReqDTO.setPassword(MyAESUtil.encrypt(userReqDTO.getPassword()));
        Integer result = userMapper.editUser(userReqDTO, TokenUtil.getCurrentUserName());
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public List<UserResDTO> listAllUser() {
        List<UserResDTO> list = userMapper.listAllUser();
        if (null == list || list.isEmpty()) {
            throw new CommonException(ErrorCode.RESOURCE_NOT_EXIST);
        }
        return list;
    }

    @Override
    public List<UserResDTO> listUser(Integer status, String userRealName, List<String> deptIds) {
        return userMapper.listUser(status, userRealName, deptIds);
    }

    @Override
    public Page<UserResDTO> pageUser(Integer status, String userRealName, List<String> deptIds, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return userMapper.pageUser(pageReqDTO.of(), status, userRealName, deptIds);
    }

    @Override
    public List<PostResDTO> listUserPost(String userId) {
        if (Objects.isNull(userId)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        return userMapper.selectPostByUserId(userId);
    }

    @Override
    public void modifyUserPost(PostReqDTO postReqDTO) {
        if (Objects.isNull(postReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = userMapper.selectUserPostIsExist(postReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        result = postMapper.addPostChangeWarn(postReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
        result = userMapper.modifyUserPost(postReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public UserResDTO getUserInfo(String id) {
        return userMapper.selectUser(id);
    }
}
