package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.req.MenuReqDTO;
import com.uav.ops.dto.req.RoleReqDTO;
import com.uav.ops.dto.req.UserRoleReqDTO;
import com.uav.ops.dto.res.MenuResDTO;
import com.uav.ops.dto.res.OperationLogResDTO;
import com.uav.ops.dto.res.UserResDTO;
import com.uav.ops.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface SysMapper {

    UserResDTO selectUserByUserName(String userName);

    List<MenuResDTO> listUserRootMenu(String userId);

    List<MenuResDTO> listUserBodyMenu(String userId);

    Page<MenuResDTO> listMenu(Page<MenuResDTO> page, Integer type, String name);

    List<MenuResDTO> getMenuRoot();

    List<MenuResDTO> getMenuBody();

    Integer selectIfMenuHadChild(String id);

    Integer modifyMenu(MenuReqDTO menuReqDTO);

    Integer addMenu(MenuReqDTO menuReqDTO);

    Integer deleteMenu(MenuReqDTO menuReqDTO);

    List<Role> listAllRole();

    Page<Role> listRole(Page<Role> page, Integer status, String roleName);

    Integer selectRoleUse(String id);

    Integer deleteRole(String id);

    Integer selectRoleIsExist(RoleReqDTO role);

    Integer insertRole(RoleReqDTO role);

    Integer updateRole(RoleReqDTO role);

    List<String> selectRoleMenuIds(String roleId);

    Integer insertRoleMenu(String roleId, List<String> menuIds, String doName);

    void deleteRoleMenus(String roleId);

    Page<UserResDTO> listUserRole(Page<UserResDTO> page, String roleId);

    Integer addUserRole(UserRoleReqDTO userRoleReqDTO);

    Integer deleteUserRole(UserRoleReqDTO userRoleReqDTO);

    Integer selectIfAdmin(String userId);

    Page<OperationLogResDTO> listOperLog(Page<OperationLogResDTO> page, String startTime, String endTime, Integer type);
}
