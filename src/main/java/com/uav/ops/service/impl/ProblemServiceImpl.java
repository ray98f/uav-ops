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
import com.uav.ops.utils.ExcelPortUtil;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
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
    public List<ProblemTypeResDTO> listProblemType(String name) {
        List<ProblemTypeResDTO> list = problemMapper.listProblemType(name, null);
        if (list != null && !list.isEmpty()) {
            for (ProblemTypeResDTO resDTO : list) {
                resDTO.setChild(problemMapper.listProblemType(name, resDTO.getId()));
            }
        }
        return list;
    }

    @Override
    public List<ProblemTypeResDTO> listAllProblemType() {
        List<ProblemTypeResDTO> list =  problemMapper.listAllProblemType(null, 1);
        if (list != null && !list.isEmpty()) {
            for (ProblemTypeResDTO resDTO : list) {
                resDTO.setChild(problemMapper.listAllProblemType(resDTO.getId(), 1));
            }
        }
        return list;
    }

    @Override
    public ProblemTypeResDTO getProblemTypeDetail(String id) {
        return problemMapper.getProblemTypeDetail(id);
    }

    @Override
    public void addProblemType(ProblemTypeReqDTO problemTypeReqDTO) {
        if (Objects.isNull(problemTypeReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = problemMapper.selectProblemTypeIsExist(problemTypeReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        problemTypeReqDTO.setId(TokenUtil.getUuId());
        problemTypeReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = problemMapper.addProblemType(problemTypeReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void modifyProblemType(ProblemTypeReqDTO problemTypeReqDTO) {
        if (Objects.isNull(problemTypeReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        Integer result = problemMapper.selectProblemTypeIsExist(problemTypeReqDTO);
        if (result > 0) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        problemTypeReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        result = problemMapper.modifyProblemType(problemTypeReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteProblemType(ProblemTypeReqDTO problemTypeReqDTO) {
        if (Objects.isNull(problemTypeReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        problemTypeReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = problemMapper.deleteProblemType(problemTypeReqDTO);
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
    public void exportProblem(String name, String startTime, String endTime, String typeId, Integer status, HttpServletResponse response) {
        List<String> listName = Arrays.asList("序号", "日期", "检查人部门", "检查者", "问题单号", "类别", "位置", "问题描述", "问题照片",
                "原因及跟踪处理结果", "是否闭环", "完成整改时间", "整改人部门", "整改人", "整改后照片", "备注");
        List<ProblemResDTO> problems = problemMapper.exportProblem(name, startTime, endTime, typeId, status);
        List<Map<String, String>> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = 1;
        if (problems != null && !problems.isEmpty()) {
            for (ProblemResDTO resDTO : problems) {
                Map<String, String> map = new HashMap<>();
                map.put("序号", String.valueOf(i));
                map.put("日期", sdf.format(resDTO.getCreateDate()));
                map.put("检查人部门", resDTO.getCheckDeptName());
                map.put("检查者", resDTO.getCheckName());
                map.put("问题单号", resDTO.getId());
                map.put("类别", resDTO.getTypeName());
                map.put("位置", resDTO.getAddress());
                map.put("问题描述", resDTO.getInfo());
                map.put("问题照片", resDTO.getImageUrl());
                map.put("原因及跟踪处理结果", resDTO.getRectifyMeasure());
                map.put("是否闭环", resDTO.getStatus() == 3 ? "是" : "否");
                map.put("完成整改时间", resDTO.getRectifyTime() != null ? sdf.format(resDTO.getRectifyTime()) : "");
                map.put("整改人部门", resDTO.getRectifyDeptName());
                map.put("整改人", resDTO.getRectifyUserName());
                map.put("整改后照片", resDTO.getAfterPic());
                map.put("备注", resDTO.getRemark());
                list.add(map);
                i++;
            }
        }
        ExcelPortUtil.excelPort("问题信息", listName, list, null, response);
    }

    @Override
    public Page<ProblemResDTO> listProblem(String name, String startTime, String endTime, String typeId, Integer status, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        Page<ProblemResDTO> page = problemMapper.listProblem(pageReqDTO.of(), name, startTime, endTime, typeId, status, TokenUtil.getCurrentPersonNo());
        List<ProblemResDTO> list = page.getRecords();
        if (list != null && !list.isEmpty()) {
            for (ProblemResDTO res : list) {
                if (res.getStatus() == 0) {
                    res.setUserStatus(problemMapper.selectRectifyUserStatus(res.getId(), TokenUtil.getCurrentPersonNo()) == 0 ? 1 : 0);
                } else if (res.getStatus() == 1) {
                    res.setUserStatus(problemMapper.selectCheckUserStatus(res.getId(), TokenUtil.getCurrentPersonNo()) == 0 ? 1 : 0);
                } else {
                    res.setUserStatus(1);
                }
            }
        }
        page.setRecords(list);
        return page;
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
    public void rectifyProblem(String problemId, String rectifyMeasure, String afterPic, String remark) {
        Integer result = problemMapper.rectifyProblem(problemId, rectifyMeasure, afterPic, remark, TokenUtil.getCurrentPersonNo());
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void solveProblem(String problemId, Integer status) {
        Integer result = problemMapper.solveProblem(problemId, status, TokenUtil.getCurrentPersonNo());
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
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
    public Page<ProblemWarningResDTO> listProblemWarning(String startTime, String endTime, Integer status, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return problemMapper.listProblemWarning(pageReqDTO.of(), startTime, endTime, status);
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
    public List<MonthlyProblemNumResDTO> monthlyProblemNum(String year) {
        return problemMapper.monthlyProblemNum(year);
    }

    @Override
    public Map<String, Object> problemProportion(String month, String typeId) {
        Map<String, Object> data = new HashMap<>();
        Integer solve = problemMapper.solveProblemNum(month, typeId);
        Integer notSolve = problemMapper.notSolveProblemNum(month, typeId);
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
    public List<TypeProblemNumResDTO> typeProblemNum(String year) {
        List<TypeProblemNumResDTO> resList = new ArrayList<>();
        List<ProblemTypeResDTO> list = problemMapper.listAllProblemType(null, 2);
        if (list != null && !list.isEmpty()) {
            for (ProblemTypeResDTO problemTypeResDTO : list) {
                TypeProblemNumResDTO resDTO = new TypeProblemNumResDTO();
                resDTO.setTypeId(problemTypeResDTO.getId());
                resDTO.setTypeName(problemTypeResDTO.getTypeName());
                resDTO.setMonthlyProblemNum(problemMapper.typeProblemNum(problemTypeResDTO.getId(), year));
                resList.add(resDTO);
            }
        }
        return resList;
    }
}
