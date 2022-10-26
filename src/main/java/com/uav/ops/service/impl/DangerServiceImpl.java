package com.uav.ops.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.DangerReqDTO;
import com.uav.ops.dto.req.VxSendTextMsgReqDTO;
import com.uav.ops.dto.res.*;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.DangerMapper;
import com.uav.ops.mapper.DeptMapper;
import com.uav.ops.mapper.FileMapper;
import com.uav.ops.mapper.SysMapper;
import com.uav.ops.service.DangerService;
import com.uav.ops.service.DeptService;
import com.uav.ops.service.MsgService;
import com.uav.ops.utils.ExcelPortUtil;
import com.uav.ops.utils.ObjectUtils;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author frp
 */
@Service
@Slf4j
public class DangerServiceImpl implements DangerService {

    public static final String SERIAL_NUMBER_WT_DANGER = ":no:num:danger:wt";
    public static final String WT_DANGER_NO = "ZTT-WRJ-WT-";

    @Autowired
    private DangerMapper dangerMapper;

    @Autowired
    private SysMapper sysMapper;

    @Autowired
    private FileMapper fileMapper;

    @Value("${pro.name}")
    private String proName;

    @Value("${spring.redis.key-prefix}")
    private String keyPrefix;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Page<DangerResDTO> listDanger(String regionId, String searchKey, Integer status, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        Page<DangerResDTO> page;
        if (sysMapper.selectIfAdmin(TokenUtil.getCurrentPersonNo()) == 1) {
            page = dangerMapper.listDanger(pageReqDTO.of(), regionId, searchKey, status, null);
        } else {
            page = dangerMapper.listDanger(pageReqDTO.of(), regionId, searchKey, status, TokenUtil.getCurrentPersonNo());
        }
        List<DangerResDTO> list = page.getRecords();
        if (list != null && !list.isEmpty()) {
            for (DangerResDTO resDTO : list) {
                if (resDTO.getBeforePic() != null && !"".equals(resDTO.getBeforePic())) {
                    resDTO.setBeforePicFile(fileMapper.selectFileInfo(Arrays.asList(resDTO.getBeforePic().split(","))));
                }
                DangerRectifyResDTO dangerRectify = dangerMapper.getDangerRectify(resDTO.getId());
                if (!Objects.isNull(dangerRectify)) {
                    if (dangerRectify.getAfterPic() != null && !"".equals(dangerRectify.getAfterPic())) {
                        dangerRectify.setAfterPicFile(fileMapper.selectFileInfo(Arrays.asList(dangerRectify.getAfterPic().split(","))));
                    }
                }
                resDTO.setDangerRectify(dangerRectify);
            }
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public DangerResDTO getDangerDetail(String id) {
        DangerResDTO res = dangerMapper.getDangerDetail(id);
        if (Objects.isNull(res)) {
            throw new CommonException(ErrorCode.RESOURCE_NOT_EXIST);
        }
        if (res.getBeforePic() != null && !"".equals(res.getBeforePic())) {
            res.setBeforePicFile(fileMapper.selectFileInfo(Arrays.asList(res.getBeforePic().split(","))));
        }
        DangerRectifyResDTO dangerRectify = dangerMapper.getDangerRectify(id);
        if (!Objects.isNull(dangerRectify)) {
            if (dangerRectify.getAfterPic() != null && !"".equals(dangerRectify.getAfterPic())) {
                dangerRectify.setAfterPicFile(fileMapper.selectFileInfo(Arrays.asList(dangerRectify.getAfterPic().split(","))));
            }
        }
        res.setDangerRectify(dangerRectify);
        return res;
    }

    @Override
    public void modifyDanger(DangerReqDTO dangerReqDTO) {
        if (Objects.isNull(dangerReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        dangerReqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        if (dangerReqDTO.getCheckUserId() == null || "".equals(dangerReqDTO.getCheckUserId())) {
            dangerReqDTO.setCheckUserId(TokenUtil.getCurrentPersonNo());
            dangerReqDTO.setCheckTime(new Date(System.currentTimeMillis()));
        }
        Integer result = dangerMapper.modifyDanger(dangerReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void addDanger(DangerReqDTO dangerReqDTO) {
        if (Objects.isNull(dangerReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        dangerReqDTO.setId(TokenUtil.getUuId());
        //流水号生成
        String no;
        no = proName + keyPrefix + SERIAL_NUMBER_WT_DANGER;
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(no))) {
            try {
                stringRedisTemplate.opsForValue().set(no, "1", 25, TimeUnit.HOURS);
            } catch (Exception e) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(out));
                throw new CommonException(ErrorCode.CACHE_ERROR, no, out.toString());
            }
        }
        int num = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(no)));
        String numStr = String.valueOf(num);
        StringBuilder str = new StringBuilder(numStr);
        for (int i = 0; i < 4 - numStr.length(); i++) {
            str.insert(0, "0");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date(System.currentTimeMillis()));
        dangerReqDTO.setNo(WT_DANGER_NO + today + "-" + str);
        stringRedisTemplate.opsForValue().set(no, String.valueOf(num + 1), 0);
        dangerReqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        if (dangerReqDTO.getCheckUserId() == null || "".equals(dangerReqDTO.getCheckUserId())) {
            dangerReqDTO.setCheckUserId(TokenUtil.getCurrentPersonNo());
            dangerReqDTO.setCheckTime(new Date(System.currentTimeMillis()));
        }
        Integer result = dangerMapper.addDanger(dangerReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void deleteDanger(DangerReqDTO dangerReqDTO) {
        if (Objects.isNull(dangerReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        dangerReqDTO.setCreateBy(TokenUtil.getCurrentPersonNo());
        Integer result = dangerMapper.deleteDanger(dangerReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public List<UserResDTO> getVisibleUser(String id) {
        return dangerMapper.getVisibleUser(id);
    }

    @Override
    public void bindVisibleUser(DangerReqDTO dangerReqDTO) {
        dangerMapper.bindVisibleUser(dangerReqDTO);
    }

    @Override
    public void rectifyDanger(String dangerId, Integer isEliminate, String rectifyMeasure, String afterPic) {
        Integer result = dangerMapper.rectifyDanger(dangerId, isEliminate, rectifyMeasure, afterPic, TokenUtil.getCurrentPersonNo());
        if (result < 0) {
            throw new CommonException(ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void exportDanger(String regionId, String searchKey, Integer status, HttpServletResponse response) {
        List<String> listName = Arrays.asList("编号", "线路", "区间", "车站", "具体位置(桥隧需注明上下行及里程)", "设备名称",
                "问题描述", "单位", "数量", "计划处理日期", "检查日期", "检查人", "照片编号", "销号日期", "销号人", "备注");
        List<DangerResDTO> exportDanger;
        if (sysMapper.selectIfAdmin(TokenUtil.getCurrentPersonNo()) == 1) {
            exportDanger = dangerMapper.exportDanger(regionId, searchKey, status, null);
        } else {
            exportDanger = dangerMapper.exportDanger(regionId, searchKey, status, TokenUtil.getCurrentPersonNo());
        }
        if (exportDanger != null && !exportDanger.isEmpty()) {
            for (DangerResDTO resDTO : exportDanger) {
                if (resDTO.getBeforePic() != null && !"".equals(resDTO.getBeforePic())) {
                    resDTO.setBeforePicFile(fileMapper.selectFileInfo(Arrays.asList(resDTO.getBeforePic().split(","))));
                }
                DangerRectifyResDTO dangerRectify = dangerMapper.getDangerRectify(resDTO.getId());
                if (!Objects.isNull(dangerRectify)) {
                    if (dangerRectify.getAfterPic() != null && !"".equals(dangerRectify.getAfterPic())) {
                        dangerRectify.setAfterPicFile(fileMapper.selectFileInfo(Arrays.asList(dangerRectify.getAfterPic().split(","))));
                    }
                }
                resDTO.setDangerRectify(dangerRectify);
            }
        }
        List<Map<String, String>> list = new ArrayList<>();
        if (exportDanger != null && !exportDanger.isEmpty()) {
            for (DangerResDTO resDTO : exportDanger) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Map<String, String> map = new HashMap<>();
                map.put("编号", resDTO.getNo());
                map.put("线路", resDTO.getLine());
                if ("1".equals(resDTO.getRegionTypeId())) {
                    map.put("区间", resDTO.getRegionName());
                    map.put("车站", "");
                } else if ("2".equals(resDTO.getRegionTypeId())) {
                    map.put("区间", "");
                    map.put("车站", resDTO.getRegionName());
                } else {
                    map.put("区间", "");
                    map.put("车站", "");
                }
                map.put("具体位置(桥隧需注明上下行及里程)", resDTO.getAddress());
                map.put("设备名称", resDTO.getDevice());
                map.put("问题描述", resDTO.getContent());
                map.put("单位", resDTO.getUnit());
                map.put("数量", resDTO.getNum() == null ? "" : String.valueOf(resDTO.getNum()));
                map.put("计划处理日期", resDTO.getRectifyTerm() == null ? "" : sdf.format(resDTO.getRectifyTerm()));
                map.put("检查日期", resDTO.getCheckTime() == null ? "" : sdf.format(resDTO.getCheckTime()));
                map.put("检查人", resDTO.getCheckUserName());
                map.put("照片编号", resDTO.getBeforePic());
                if (resDTO.getDangerRectify() != null) {
                    map.put("销号日期", resDTO.getDangerRectify().getRectifyTime() == null ? "" : sdf.format(resDTO.getDangerRectify().getRectifyTime()));
                    map.put("销号人", resDTO.getDangerRectify().getRectifyUserName());
                    map.put("备注", resDTO.getDangerRectify().getRectifyMeasure());
                } else {
                    map.put("销号日期", "");
                    map.put("销号人", "");
                    map.put("备注", "");
                }
                list.add(map);
            }
        }
        ExcelPortUtil.excelPort("问题巡检汇总表", listName, list, null, response);
    }
}
