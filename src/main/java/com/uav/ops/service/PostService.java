package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.PostReqDTO;
import com.uav.ops.dto.req.PostUserReqDTO;
import com.uav.ops.dto.res.PostResDTO;
import com.uav.ops.dto.res.PostWarnResDTO;

import java.util.List;

/**
 * @author frp
 */
public interface PostService {

    Page<PostResDTO> listPost(String name, Integer status, PageReqDTO pageReqDTO);

    List<PostResDTO> listAllPost();

    void modifyPost(PostReqDTO postReqDTO);

    void addPost(PostReqDTO postReqDTO);

    void deletePost(PostReqDTO postReqDTO);

    void bindingPost(PostUserReqDTO postUserReqDTO);

    Page<PostWarnResDTO> listPostWarn(PageReqDTO pageReqDTO);

    void handlePostWarn(String id);

}
