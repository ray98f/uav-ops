package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.req.PasswordReqDTO;
import com.uav.ops.dto.req.PostReqDTO;
import com.uav.ops.dto.req.UserReqDTO;
import com.uav.ops.dto.res.PostResDTO;
import com.uav.ops.dto.res.UserResDTO;
import com.uav.ops.dto.res.VxUserResDTO;
import com.uav.ops.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    UserReqDTO selectUserInfo(String id, String userName);

    /**
     * 获取用户权限id
     *
     * @param userId
     * @return
     */
    List<String> selectUserRoles(String userId);

    /**
     * 新增用户
     *
     * @param list
     * @param doName
     * @return
     */
    void insertUser(List<VxUserResDTO> list, String doName);

    List<String> selectUserIds();

    void deleteUser(List<String> list, String doName);

    String selectOldPassword(PasswordReqDTO passwordReqDTO);

    /**
     * 修改密码
     *
     * @param passwordReqDTO
     * @param updateBy
     * @return
     */
    Integer changePwd(PasswordReqDTO passwordReqDTO, String updateBy);

    /**
     * 修改用户
     *
     * @param userReqDTO
     * @param updateBy
     * @return
     */
    Integer editUser(UserReqDTO userReqDTO, String updateBy);

    /**
     * 获取所有用户列表
     *
     * @return
     */
    List<UserResDTO> listAllUser();

    /**
     * 查询用户列表
     * @param status
     * @param name
     * @return
     */
    List<UserResDTO> listUser(Integer status, String name, List<String> deptIds);

    /**
     * 查询用户列表
     * @param page
     * @param status
     * @param name
     * @return
     */
    Page<UserResDTO> pageUser(Page<UserResDTO> page, Integer status, String name, List<String> deptIds);

    /**
     * 根据用户名获取用户id
     * @param userName
     * @return
     */
    Long selectUserId(String userName);

    UserResDTO selectUser(String id);

    List<PostResDTO> selectPostByUserId(String userId);

    Integer selectUserPostIsExist(PostReqDTO postReqDTO);

    Integer modifyUserPost(PostReqDTO postReqDTO);
}
