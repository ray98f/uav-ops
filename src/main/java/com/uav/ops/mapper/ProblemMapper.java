package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.req.ProblemIdentifyReqDTO;
import com.uav.ops.dto.req.ProblemReqDTO;
import com.uav.ops.dto.req.ProblemTypeReqDTO;
import com.uav.ops.dto.req.ProblemWarningReqDTO;
import com.uav.ops.dto.res.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author frp
 */
@Mapper
@Repository
public interface ProblemMapper {

    List<ProblemTypeResDTO> listProblemType(String name, String parentId);

    List<ProblemTypeResDTO> listAllProblemType(String parentId, Integer type);

    List<ProblemTypeResDTO> listNoChildProblemType();

    List<ProblemTypeResDTO>  listTypeById(List<String> list);

    ProblemTypeResDTO getProblemTypeDetail(String id);

    Integer selectProblemTypeIsExist(ProblemTypeReqDTO problemTypeReqDTO);

    Integer addProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    Integer modifyProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    Integer deleteProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    Page<ProblemIdentifyResDTO> listProblemIdentify(Page<ProblemIdentifyResDTO> page, String name, Integer isChecked, Integer status, String startTime, String endTime);

    Page<ProblemIdentifyResDTO> listNotBindProblemIdentify(Page<ProblemIdentifyResDTO> page, String name);

    ProblemIdentifyResDTO getProblemIdentifyDetail(String id);

    Integer addProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    Integer modifyProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    Integer deleteProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    List<ProblemResDTO> exportProblem(String name, String startTime, String endTime, String typeId, Integer status);

    Page<ProblemResDTO> listProblem(Page<ProblemResDTO> page, String name, String startTime, String endTime, String typeId, Integer status, String userId);

    ProblemDetailResDTO getProblemDetail(String id);

    List<ProblemIdentifyResDTO> getProblemIdentifyByProblemId(String id);

    Integer selectProblemIsExist(ProblemReqDTO problemReqDTO);

    Integer addProblem(ProblemReqDTO problemReqDTO);

    Integer modifyProblem(ProblemReqDTO problemReqDTO);

    Integer deleteProblem(ProblemReqDTO problemReqDTO);

    Integer rectifyProblem(String problemId, String rectifyMeasure, String afterPic, String remark, String createBy);

    Integer solveProblem(String problemId, Integer status, String createBy);

    Integer insertProblemRe(String problemId, List<String> identifyIds, String userId);

    Integer deleteProblemRe(String problemId, List<String> identifyIds, String userId);

    Page<ProblemWarningResDTO> listProblemWarning(Page<ProblemResDTO> page, String startTime, String endTime, Integer status);

    ProblemWarningResDTO getProblemWarningDetail(String id);

    Integer addProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    Integer closeProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    Integer deleteProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    List<MonthlyProblemNumResDTO> monthlyProblemNum(String year);

    Integer problemNum(String startTime, String endTime, String typeId);

    Integer solveProblemNum(String month, String typeId);

    Integer notSolveProblemNum(String month, String typeId);

    List<MonthlyProblemNumResDTO> typeProblemNum(String typeId, String year);

    Integer selectRectifyUserStatus(String problemId, String userId);

    Integer selectCheckUserStatus(String problemId, String userId);
    
}
