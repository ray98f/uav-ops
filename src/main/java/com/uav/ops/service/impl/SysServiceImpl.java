package com.uav.ops.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.VxAccessToken;
import com.uav.ops.dto.req.*;
import com.uav.ops.dto.res.*;
import com.uav.ops.entity.Role;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.SysMapper;
import com.uav.ops.service.SysService;
import com.uav.ops.utils.*;
import com.uav.ops.utils.treeTool.MenuTreeToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author frp
 */
@Service
@Slf4j
public class SysServiceImpl implements SysService {

    public static final String TOKEN = "token";

    @Autowired
    private SysMapper sysMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<String, Object> login(LoginReqDTO loginReqDTO) throws Exception {
        if (Objects.isNull(loginReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        UserResDTO resDTO = sysMapper.selectUserByUserName(loginReqDTO.getUserName());
        if (Objects.isNull(resDTO) || Objects.isNull(resDTO.getId()) || !MyAESUtil.decrypt(loginReqDTO.getPassword()).equals(MyAESUtil.decrypt(resDTO.getPassword()))) {
            throw new CommonException(ErrorCode.LOGIN_PASSWORD_ERROR);
        }
        Map<String, Object> data = new HashMap<>(16);
        data.put("userInfo", resDTO);
        data.put(TOKEN, TokenUtil.createSimpleToken(resDTO));
        return data;
    }

    @Override
    public List<MenuResDTO> listUserMenu(String userId) {
        if (Objects.isNull(userId)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        List<MenuResDTO> extraRootList = sysMapper.listUserRootMenu(userId);
        if (Objects.isNull(extraRootList)) {
            throw new CommonException(ErrorCode.RESOURCE_NOT_EXIST);
        }
        List<MenuResDTO> extraBodyList = sysMapper.listUserBodyMenu(userId);
        MenuTreeToolUtils extraTree = new MenuTreeToolUtils(extraRootList, extraBodyList);
        return extraTree.getTree();
    }

    @Override
    public Page<MenuResDTO> listMenu(Integer type, String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        Page<MenuResDTO> page = sysMapper.listMenu(pageReqDTO.of(), type, name);
        List<MenuResDTO> list = page.getRecords();
        if (list != null && !list.isEmpty()) {
            for (MenuResDTO res : list) {
                res.setParentNames(res.getParentNames().replace("root", "").replaceAll(",", "/") + "/" + res.getMenuName());
            }
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public List<MenuResDTO> listAllMenu() {
        List<MenuResDTO> extraRootList = sysMapper.getMenuRoot();
        if (Objects.isNull(extraRootList)) {
            throw new CommonException(ErrorCode.RESOURCE_NOT_EXIST);
        }
        List<MenuResDTO> extraBodyList = sysMapper.getMenuBody();
        MenuTreeToolUtils extraTree = new MenuTreeToolUtils(extraRootList, extraBodyList);
        return extraTree.getTree();
    }

    @Override
    public void modifyMenu(MenuReqDTO menuReqDTO) {
        if (Objects.isNull(menuReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        if (menuReqDTO.getStatus() == 1) {
            Integer result = sysMapper.selectIfMenuHadChild(menuReqDTO.getId());
            if (result > 0) {
                throw new CommonException(ErrorCode.CANT_UPDATE_HAD_CHILD);
            }
        }
        menuReqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        Integer result = sysMapper.modifyMenu(menuReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void addMenu(MenuReqDTO menuReqDTO) {
        if (Objects.isNull(menuReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        menuReqDTO.setId(TokenUtil.getUuId());
        menuReqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        Integer result = sysMapper.addMenu(menuReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void deleteMenu(MenuReqDTO menuReqDTO) {
        if (Objects.isNull(menuReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = sysMapper.selectIfMenuHadChild(menuReqDTO.getId());
        if (result > 0) {
            throw new CommonException(ErrorCode.CANT_DELETE_HAD_CHILD);
        }
        menuReqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        result = sysMapper.deleteMenu(menuReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public List<Role> listAllRole() {
        return sysMapper.listAllRole();
    }

    @Override
    public Page<Role> listRole(Integer status, String roleName, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return sysMapper.listRole(pageReqDTO.of(), status, roleName);
    }

    @Override
    public void deleteRole(RoleReqDTO role) {
        Integer result = sysMapper.selectRoleUse(role.getId());
        if (result > 0) {
            throw new CommonException(ErrorCode.ROLE_USE_CANT_DELETE);
        }
        result = sysMapper.deleteRole(role.getId());
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public void insertRole(RoleReqDTO role) {
        if (Objects.isNull(role)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = sysMapper.selectRoleIsExist(role);
        if (result > 0) {
            throw new CommonException(ErrorCode.ROLE_EXIST);
        }
        role.setId(TokenUtil.getUuId());
        role.setCreateBy(TokenUtil.getCurrentPersonNo());
        result = sysMapper.insertRole(role);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
        if (null != role.getMenuIds() && !role.getMenuIds().isEmpty()) {
            result = sysMapper.insertRoleMenu(role.getId(), role.getMenuIds(), role.getCreateBy());
            if (result < 0) {
                throw new CommonException(ErrorCode.INSERT_ERROR);
            }
        }
    }

    @Override
    public void updateRole(RoleReqDTO role) {
        if (Objects.isNull(role)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = sysMapper.selectRoleIsExist(role);
        if (result > 0) {
            throw new CommonException(ErrorCode.ROLE_EXIST);
        }
        role.setCreateBy(TokenUtil.getCurrentPersonNo());
        result = sysMapper.updateRole(role);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
        sysMapper.deleteRoleMenus(role.getId());
        if (null != role.getMenuIds() && !role.getMenuIds().isEmpty()) {
            result = sysMapper.insertRoleMenu(role.getId(), role.getMenuIds(), role.getCreateBy());
            if (result < 0) {
                throw new CommonException(ErrorCode.INSERT_ERROR);
            }
        }
    }

    @Override
    public List<String> selectMenuIds(String roleId) {
        if (null == roleId || "".equals(roleId)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        return sysMapper.selectRoleMenuIds(roleId);
    }

    @Override
    public Page<UserResDTO> listUserRole(String roleId, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return sysMapper.listUserRole(pageReqDTO.of(), roleId);
    }

    @Override
    public void addUserRole(UserRoleReqDTO userRoleReqDTO) {
        if (Objects.isNull(userRoleReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = sysMapper.addUserRole(userRoleReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteUserRole(UserRoleReqDTO userRoleReqDTO) {
        if (Objects.isNull(userRoleReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = sysMapper.deleteUserRole(userRoleReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public Page<OperationLogResDTO> listOperLog(String startTime, String endTime, String type, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return sysMapper.listOperLog(pageReqDTO.of(), startTime, endTime, type);
    }

    @Override
    public void menu() {
        List<MenuResDTO> list = sysMapper.selectMenu();
        if (list != null && !list.isEmpty()) {
            sysMapper.addButton(list);
        }
    }
}
