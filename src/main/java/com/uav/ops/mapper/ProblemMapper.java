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

    Page<ProblemTypeResDTO> listProblemType(Page<ProblemTypeResDTO> page, String name);

    List<ProblemTypeResDTO> listAllProblemType();

    ProblemTypeResDTO getProblemTypeDetail(String id);

    Integer selectProblemTypeIsExist(ProblemTypeReqDTO cruiseTypeReqDTO);

    Integer addProblemType(ProblemTypeReqDTO cruiseTypeReqDTO);

    Integer modifyProblemType(ProblemTypeReqDTO cruiseTypeReqDTO);

    Integer deleteProblemType(ProblemTypeReqDTO cruiseTypeReqDTO);

    Page<ProblemIdentifyResDTO> listProblemIdentify(Page<ProblemIdentifyResDTO> page, String name);

    Page<ProblemIdentifyResDTO> listNotBindProblemIdentify(Page<ProblemIdentifyResDTO> page, String name);

    ProblemIdentifyResDTO getProblemIdentifyDetail(String id);

    Integer addProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    Integer modifyProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    Integer deleteProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    Page<ProblemResDTO> listProblem(Page<ProblemResDTO> page, String name, String startTime, String endTime, String typeId, Integer status);

    ProblemDetailResDTO getProblemDetail(String id);

    List<ProblemIdentifyResDTO> getProblemIdentifyByProblemId(String id);

    Integer selectProblemIsExist(ProblemReqDTO problemReqDTO);

    Integer addProblem(ProblemReqDTO problemReqDTO);

    Integer modifyProblem(ProblemReqDTO problemReqDTO);

    Integer deleteProblem(ProblemReqDTO problemReqDTO);

    Integer insertProblemRe(String problemId, List<String> identifyIds, String userId);

    Integer deleteProblemRe(String problemId, List<String> identifyIds, String userId);

    Page<ProblemWarningResDTO> listProblemWarning(Page<ProblemResDTO> page, String startTime, String endTime, Integer status);

    ProblemWarningResDTO getProblemWarningDetail(String id);

    Integer addProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    Integer closeProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    Integer deleteProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    List<MonthlyProblemNumResDTO> monthlyProblemNum();

    Integer solveProblemNum();

    Integer notSolveProblemNum();

    List<MonthlyProblemNumResDTO> typeProblemNum(String typeId);
    
}
