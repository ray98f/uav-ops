package com.uav.ops.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.PostReqDTO;
import com.uav.ops.dto.req.PostUserReqDTO;
import com.uav.ops.dto.res.PostResDTO;
import com.uav.ops.dto.res.PostWarnResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.PostMapper;
import com.uav.ops.service.PostService;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author frp
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public Page<PostResDTO> listPost(String name, Integer status, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return postMapper.listPost(pageReqDTO.of(), name, status);
    }

    @Override
    public List<PostResDTO> listAllPost() {
        return postMapper.listAllPost();
    }

    @Override
    public void modifyPost(PostReqDTO postReqDTO) {
        if (Objects.isNull(postReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        postReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = postMapper.modifyPost(postReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void addPost(PostReqDTO postReqDTO) {
        if (Objects.isNull(postReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        postReqDTO.setId(TokenUtil.getUuId());
        postReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = postMapper.addPost(postReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void deletePost(PostReqDTO postReqDTO) {
        if (Objects.isNull(postReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        postReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = postMapper.deletePost(postReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public void bindingPost(PostUserReqDTO postUserReqDTO) {
        if (Objects.isNull(postUserReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = postMapper.bindingPost(postUserReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public Page<PostWarnResDTO> listPostWarn(PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return postMapper.listPostWarn(pageReqDTO.of());
    }

    @Override
    public void handlePostWarn(String id) {
        if (Objects.isNull(id)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = postMapper.handlePostWarn(id);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

}
