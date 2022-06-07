package com.uav.ops.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.*;
import com.uav.ops.dto.res.*;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.ProblemMapper;
import com.uav.ops.service.ProblemService;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author frp
 */
@Service
@Slf4j
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Override
    public Page<ProblemTypeResDTO> listProblemType(String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return problemMapper.listProblemType(pageReqDTO.of(), name);
    }

    @Override
    public List<ProblemTypeResDTO> listAllProblemType() {
        return problemMapper.listAllProblemType();
    }

    @Override
    public ProblemTypeResDTO getProblemTypeDetail(String id) {
        return problemMapper.getProblemTypeDetail(id);
    }

    @Override
    public void addProblemType(ProblemTypeReqDTO cruiseTypeReqDTO) {
        if (Objects.isNull(cruiseTypeReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = problemMapper.selectProblemTypeIsExist(cruiseTypeReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        cruiseTypeReqDTO.setId(TokenUtil.getUuId());
        cruiseTypeReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = problemMapper.addProblemType(cruiseTypeReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void modifyProblemType(ProblemTypeReqDTO cruiseTypeReqDTO) {
        if (Objects.isNull(cruiseTypeReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = problemMapper.selectProblemTypeIsExist(cruiseTypeReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        cruiseTypeReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = problemMapper.modifyProblemType(cruiseTypeReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteProblemType(ProblemTypeReqDTO cruiseTypeReqDTO) {
        if (Objects.isNull(cruiseTypeReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        cruiseTypeReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.deleteProblemType(cruiseTypeReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public Page<ProblemIdentifyResDTO> listProblemIdentify(String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return problemMapper.listProblemIdentify(pageReqDTO.of(), name);
    }

    @Override
    public Page<ProblemIdentifyResDTO> listNotBindProblemIdentify(String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return problemMapper.listNotBindProblemIdentify(pageReqDTO.of(), name);
    }

    @Override
    public ProblemIdentifyResDTO getProblemIdentifyDetail(String id) {
        return problemMapper.getProblemIdentifyDetail(id);
    }

    @Override
    public void addProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        if (Objects.isNull(problemIdentifyReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        problemIdentifyReqDTO.setId(TokenUtil.getUuId());
        problemIdentifyReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.addProblemIdentify(problemIdentifyReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void modifyProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        if (Objects.isNull(problemIdentifyReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        problemIdentifyReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.modifyProblemIdentify(problemIdentifyReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteProblemIdentify(ProblemIdentifyReqDTO problemIdentifyReqDTO) {
        if (Objects.isNull(problemIdentifyReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        problemIdentifyReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.deleteProblemIdentify(problemIdentifyReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public Page<ProblemResDTO> listProblem(String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return problemMapper.listProblem(pageReqDTO.of(), name);
    }

    @Override
    public ProblemDetailResDTO getProblemDetail(String id) {
        ProblemDetailResDTO resDTO = problemMapper.getProblemDetail(id);
        if (!Objects.isNull(resDTO)) {
            resDTO.setIdentifies(problemMapper.getProblemIdentifyByProblemId(id));
        }
        return resDTO;
    }

    @Override
    public void addProblem(ProblemReqDTO problemReqDTO) {
        if (Objects.isNull(problemReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = problemMapper.selectProblemIsExist(problemReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        problemReqDTO.setId(TokenUtil.getUuId());
        problemReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = problemMapper.addProblem(problemReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
        if (problemReqDTO.getIdentifyId() != null && !"".equals(problemReqDTO.getIdentifyId())) {
            result = problemMapper.insertProblemRe(problemReqDTO.getId(),
                    new ArrayList<String>() {{
                        add(problemReqDTO.getIdentifyId());
                    }}, TokenUtil.getCurrentPersonNo());
            if (result < 0) {
                throw new CommonException(ErrorCode.INSERT_ERROR);
            }
        }
    }

    @Override
    public void modifyProblem(ProblemReqDTO problemReqDTO) {
        if (Objects.isNull(problemReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = problemMapper.selectProblemIsExist(problemReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        problemReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = problemMapper.modifyProblem(problemReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteProblem(ProblemReqDTO problemReqDTO) {
        if (Objects.isNull(problemReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        List<ProblemIdentifyResDTO> list = problemMapper.getProblemIdentifyByProblemId(problemReqDTO.getId());
        List<String> identifyIds = list.stream().map(ProblemIdentifyResDTO::getId).collect(Collectors.toList());
        if (!identifyIds.isEmpty()) {
            Integer result = problemMapper.deleteProblemRe(problemReqDTO.getId(), identifyIds, TokenUtil.getCurrentPersonNo());
            if (result < 0) {
                throw new CommonException(ErrorCode.DELETE_ERROR);
            }
        }
        problemReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.deleteProblem(problemReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public void bindProblem(ProblemBindReqDTO problemBindReqDTO) {
        if (problemBindReqDTO.getType() == 1 && !problemBindReqDTO.getIdentifyIds().isEmpty()) {
            Integer result = problemMapper.insertProblemRe(problemBindReqDTO.getProblemId(), problemBindReqDTO.getIdentifyIds(), TokenUtil.getCurrentPersonNo());
            if (result < 0) {
                throw new CommonException(ErrorCode.INSERT_ERROR);
            }
        } else if (problemBindReqDTO.getType() == 2 && !problemBindReqDTO.getIdentifyIds().isEmpty()) {
            Integer result = problemMapper.deleteProblemRe(problemBindReqDTO.getProblemId(), problemBindReqDTO.getIdentifyIds(), TokenUtil.getCurrentPersonNo());
            if (result < 0) {
                throw new CommonException(ErrorCode.DELETE_ERROR);
            }
        }
    }

    @Override
    public Page<ProblemWarningResDTO> listProblemWarning(PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return problemMapper.listProblemWarning(pageReqDTO.of());
    }

    @Override
    public ProblemWarningResDTO getProblemWarningDetail(String id) {
        return problemMapper.getProblemWarningDetail(id);
    }

    @Override
    public void addProblemWarning(ProblemWarningReqDTO problemWarningReqDTO) {
        if (Objects.isNull(problemWarningReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        problemWarningReqDTO.setId(TokenUtil.getUuId());
        problemWarningReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.addProblemWarning(problemWarningReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void closeProblemWarning(ProblemWarningReqDTO problemWarningReqDTO) {
        if (Objects.isNull(problemWarningReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        problemWarningReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.closeProblemWarning(problemWarningReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteProblemWarning(ProblemWarningReqDTO problemWarningReqDTO) {
        if (Objects.isNull(problemWarningReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        problemWarningReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.deleteProblemWarning(problemWarningReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public List<MonthlyProblemNumResDTO> monthlyProblemNum() {
        return problemMapper.monthlyProblemNum();
    }

    @Override
    public Map<String, Object> problemProportion() {
        Map<String, Object> data = new HashMap<>();
        Integer solve = problemMapper.solveProblemNum();
        Integer notSolve = problemMapper.notSolveProblemNum();
        if (solve == 0 && notSolve == 0) {
            data.put("solveNum", 0);
            data.put("notSolveNum", 0);
        } else {
            data.put("solveNum", solve);
            data.put("notSolveNum", notSolve);
        }
        return data;
    }

    @Override
    public List<TypeProblemNumResDTO> typeProblemNum() {
        List<TypeProblemNumResDTO> resList = new ArrayList<>();
        List<ProblemTypeResDTO> list = problemMapper.listAllProblemType();
        if (list != null && !list.isEmpty()) {
            for (ProblemTypeResDTO problemTypeResDTO : list) {
                TypeProblemNumResDTO resDTO = new TypeProblemNumResDTO();
                resDTO.setTypeId(problemTypeResDTO.getId());
                resDTO.setTypeName(problemTypeResDTO.getTypeName());
                resDTO.setMonthlyProblemNum(problemMapper.typeProblemNum(problemTypeResDTO.getId()));
                resList.add(resDTO);
            }
        }
        return resList;
    }
}
