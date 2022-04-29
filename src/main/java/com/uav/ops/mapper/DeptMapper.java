package com.uav.ops.mapper;

import com.uav.ops.dto.res.DeptTreeResDTO;
import com.uav.ops.dto.res.UserResDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface DeptMapper {

    void syncOrg(List<DeptTreeResDTO> list, String userId);

    List<DeptTreeResDTO> getBody();

    List<DeptTreeResDTO> getRoot();

    List<DeptTreeResDTO> listCompanyList();

    DeptTreeResDTO selectParent(String deptId);

    List<UserResDTO> selectDepartmentUser(String deptId, String dangerId);
}
