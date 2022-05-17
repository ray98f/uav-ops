package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.ProblemIdentifyReqDTO;
import com.uav.ops.dto.req.ProblemReqDTO;
import com.uav.ops.dto.req.ProblemTypeReqDTO;
import com.uav.ops.dto.req.ProblemWarningReqDTO;
import com.uav.ops.dto.res.*;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author frp
 */
public interface ProblemService {

    Page<ProblemTypeResDTO> listProblemType(String name, PageReqDTO pageReqDTO);

    List<ProblemTypeResDTO> listAllProblemType();

    ProblemTypeResDTO getProblemTypeDetail(String id);

    void addProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    void modifyProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    void deleteProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    Page<ProblemIdentifyResDTO> listProblemIdentify(String name, PageReqDTO pageReqDTO);

    Page<ProblemIdentifyResDTO> listNotBindProblemIdentify(String name, PageReqDTO pageReqDTO);

    ProblemIdentifyResDTO getProblemIdentifyDetail(String id);

    void addProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    void modifyProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    void deleteProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO);

    Page<ProblemResDTO> listProblem(String name, PageReqDTO pageReqDTO);

    ProblemDetailResDTO getProblemDetail(String id);

    void addProblem(ProblemReqDTO problemReqDTO);

    void modifyProblem(ProblemReqDTO problemReqDTO);

    void deleteProblem(ProblemReqDTO problemReqDTO);

    void bindProblem(String problemId, List<String> identifyIds, Integer type);

    Page<ProblemWarningResDTO> listProblemWarning(PageReqDTO pageReqDTO);

    ProblemWarningResDTO getProblemWarningDetail(String id);

    void addProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    void closeProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    void deleteProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    List<MonthlyProblemNumResDTO> monthlyProblemNum();

    Map<String, Object>  problemProportion();

    List<TypeProblemNumResDTO> typeProblemNum();

}