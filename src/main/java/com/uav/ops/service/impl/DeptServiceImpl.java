package com.uav.ops.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uav.ops.dto.res.DeptTreeResDTO;
import com.uav.ops.dto.res.UserResDTO;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.DeptMapper;
import com.uav.ops.service.DeptService;
import com.uav.ops.utils.treeTool.DeptTreeToolUtils;
import com.uav.ops.utils.Constants;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * @author frp
 */
@Service
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Value("${open.api.eip-service.host}")
    private String securityInfoManageServiceHost;

    @Value("${open.api.eip-service.port}")
    private String securityInfoManageServicePort;

    @Value("${open.api.eip-service.url}")
    private String securityInfoManageServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncDept() {
        String url = securityInfoManageServiceHost + ":" + securityInfoManageServicePort + securityInfoManageServiceUrl
                + "/dept/sync/list";
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
        List<DeptTreeResDTO> list = JSONArray.parseArray(res.getJSONArray(Constants.DATA).toJSONString(), DeptTreeResDTO.class);
        if (list != null && !list.isEmpty()) {
            deptMapper.syncOrg(list, TokenUtil.getCurrentPersonNo());
        }
    }

    @Override
    public List<DeptTreeResDTO> listTree() {
        List<DeptTreeResDTO> extraRootList = deptMapper.getRoot();
        if (Objects.isNull(extraRootList)) {
            throw new CommonException(ErrorCode.RESOURCE_NOT_EXIST);
        }
        List<DeptTreeResDTO> extraBodyList = deptMapper.getBody();
        DeptTreeToolUtils extraTree = new DeptTreeToolUtils(extraRootList, extraBodyList);
        return extraTree.getTree();
    }

    @Override
    public List<DeptTreeResDTO> listFirst() {
        return deptMapper.listCompanyList();
    }

    @Override
    public List<UserResDTO> getDeptUser(String deptId, String dangerId) {
        DeptTreeResDTO res = deptMapper.selectParent(deptId);
        if (!Objects.isNull(res) && !"1".equals(res.getId())) {
            while (!"1".equals(res.getParentId())) {
                res = deptMapper.selectParent(res.getId());
                if (Objects.isNull(res)) {
                    break;
                }
            }
            return deptMapper.selectDepartmentUser(res.getId(), dangerId);
        } else if ("1".equals(res.getId())) {
            return deptMapper.selectDepartmentUser(deptId, dangerId);
        } else {
            throw new CommonException(ErrorCode.RESOURCE_NOT_EXIST);
        }
    }
}
