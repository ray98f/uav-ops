package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.*;
import com.uav.ops.dto.res.*;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author frp
 */
public interface ProblemService {

    List<ProblemTypeResDTO> listProblemType(String name);

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

    void exportProblem(String name, String startTime, String endTime, String typeId, Integer status, HttpServletResponse response);

    Page<ProblemResDTO> listProblem(String name, String startTime, String endTime, String typeId, Integer status, PageReqDTO pageReqDTO);

    ProblemDetailResDTO getProblemDetail(String id);

    void addProblem(ProblemReqDTO problemReqDTO);

    void modifyProblem(ProblemReqDTO problemReqDTO);

    void deleteProblem(ProblemReqDTO problemReqDTO);

    void rectifyProblem(String problemId, String rectifyMeasure, String afterPic, String remark);

    void solveProblem(String problemId, Integer status);

    void bindProblem(ProblemBindReqDTO problemBindReqDTO);

    Page<ProblemWarningResDTO> listProblemWarning(String startTime, String endTime, Integer status, PageReqDTO pageReqDTO);

    ProblemWarningResDTO getProblemWarningDetail(String id);

    void addProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    void closeProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    void deleteProblemWarning(ProblemWarningReqDTO problemWarningReqDTO);

    List<MonthlyProblemNumResDTO> monthlyProblemNum();

    Map<String, Object>  problemProportion();

    List<TypeProblemNumResDTO> typeProblemNum();

}
