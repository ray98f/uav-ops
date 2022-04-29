package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.*;
import com.uav.ops.dto.res.MenuResDTO;
import com.uav.ops.dto.res.OperationLogResDTO;
import com.uav.ops.dto.res.UserResDTO;
import com.uav.ops.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author frp
 */
public interface SysService {

    Map<String, Object> login(LoginReqDTO loginReqDTO) throws Exception;

    List<MenuResDTO> listUserMenu(String userId);

    Page<MenuResDTO> listMenu(Integer type, String name, PageReqDTO pageReqDTO);

    List<MenuResDTO> listAllMenu();

    void modifyMenu(MenuReqDTO menuReqDTO);

    void addMenu(MenuReqDTO menuReqDTO);

    void deleteMenu(MenuReqDTO menuReqDTO);

    List<Role> listAllRole();

    Page<Role> listRole(Integer status,String roleName,PageReqDTO pageReqDTO);

    void deleteRole(RoleReqDTO role);

    void insertRole(RoleReqDTO role);

    void updateRole(RoleReqDTO role);

    List<String> selectMenuIds(String roleId);

    Page<UserResDTO> listUserRole(String roleId, PageReqDTO pageReqDTO);

    void addUserRole(UserRoleReqDTO userRoleReqDTO);

    void deleteUserRole(UserRoleReqDTO userRoleReqDTO);

    Page<OperationLogResDTO> listOperLog(String startTime, String endTime, Integer type, PageReqDTO pageReqDTO);

}
