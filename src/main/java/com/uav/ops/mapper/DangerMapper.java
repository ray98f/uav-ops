package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.req.DangerReqDTO;
import com.uav.ops.dto.req.ProblemTypeReqDTO;
import com.uav.ops.dto.res.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface DangerMapper {

    List<ProblemTypeResDTO> listProblemType(String name, String parentId);

    List<ProblemTypeResDTO> listAllProblemType(String parentId, Integer type);

    ProblemTypeResDTO getProblemTypeDetail(String id);

    Integer selectProblemTypeIsExist(ProblemTypeReqDTO problemTypeReqDTO);

    Integer addProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    Integer modifyProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    Integer deleteProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    Page<DangerResDTO> listDanger(Page<DangerResDTO> page, String regionId, String searchKey, Integer status, String userId);

    DangerRectifyResDTO getDangerRectify(String dangerId);

    DangerResDTO getDangerDetail(String id);

    Integer modifyDanger(DangerReqDTO dangerReqDTO);

    Integer addDanger(DangerReqDTO dangerReqDTO);

    Integer deleteDanger(DangerReqDTO dangerReqDTO);

    List<UserResDTO> getVisibleUser(String id);

    void bindVisibleUser(DangerReqDTO dangerReqDTO);

    Integer rectifyDanger(String dangerId, Integer isEliminate, String rectifyMeasure, String afterPic, String createBy);

    List<DangerResDTO> exportDanger(String regionId, String searchKey, Integer status, String userId);
}
