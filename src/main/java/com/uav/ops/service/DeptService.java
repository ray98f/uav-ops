package com.uav.ops.service;

import com.uav.ops.dto.res.DeptTreeResDTO;
import com.uav.ops.dto.res.UserResDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author frp
 */
public interface DeptService {

    void syncDept(String orgId);

    List<DeptTreeResDTO> listTree();

    List<DeptTreeResDTO> listFirst();

    List<UserResDTO> getDeptUser(String deptId, String dangerId);

}
