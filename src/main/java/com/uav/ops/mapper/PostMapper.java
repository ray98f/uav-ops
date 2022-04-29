package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.PostReqDTO;
import com.uav.ops.dto.req.PostUserReqDTO;
import com.uav.ops.dto.res.PostResDTO;
import com.uav.ops.dto.res.PostWarnResDTO;
import com.uav.ops.dto.res.VxUserResDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface PostMapper {

    Page<PostResDTO> listPost(Page<PostResDTO> page, String name, Integer status);

    List<PostResDTO> listAllPost();

    Integer modifyPost(PostReqDTO postReqDTO);

    Integer addPost(PostReqDTO postReqDTO);

    Integer deletePost(PostReqDTO postReqDTO);

    Integer bindingPost(PostUserReqDTO postUserReqDTO);

    void insertPost(List<VxUserResDTO> list, String doName);

    void insertUserPost(List<VxUserResDTO> list);

    Integer addPostChangeWarn(PostReqDTO postReqDTO);

    Page<PostWarnResDTO> listPostWarn(Page<PostWarnResDTO> page);

    Integer handlePostWarn(String id);

}
