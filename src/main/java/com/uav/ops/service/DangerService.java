package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.DangerReqDTO;
import com.uav.ops.dto.req.ProblemTypeReqDTO;
import com.uav.ops.dto.res.*;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author frp
 */
public interface DangerService {

    List<ProblemTypeResDTO> listProblemType(String name);

    List<ProblemTypeResDTO> listAllProblemType();

    ProblemTypeResDTO getProblemTypeDetail(String id);

    void addProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    void modifyProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    void deleteProblemType(ProblemTypeReqDTO problemTypeReqDTO);

    Page<DangerResDTO> listDanger(String regionId, String searchKey, Integer status, PageReqDTO pageReqDTO);

    DangerResDTO getDangerDetail(String id);

    void modifyDanger(DangerReqDTO dangerReqDTO);

    void addDanger(DangerReqDTO dangerReqDTO);

    void deleteDanger(DangerReqDTO dangerReqDTO);

    List<UserResDTO> getVisibleUser(String id);

    void bindVisibleUser(DangerReqDTO dangerReqDTO);

    void rectifyDanger(String dangerId, Integer isEliminate, String rectifyMeasure, String afterPic);

    void exportDanger(String regionId, String searchKey, Integer status, HttpServletResponse response);
}
