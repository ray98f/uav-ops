package com.uav.ops.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.uav.ops.config.repository.UavEsRepository;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.req.FlyHistoryReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.dto.res.FlyHistoryDataResDTO;
import com.uav.ops.dto.res.FlyHistoryResDTO;
import com.uav.ops.entity.Uav;
import com.uav.ops.enums.ErrorCode;
import com.uav.ops.exception.CommonException;
import com.uav.ops.mapper.CruiseMapper;
import com.uav.ops.mapper.FlyHistoryMapper;
import com.uav.ops.service.CruiseService;
import com.uav.ops.service.FlyHistoryService;
import com.uav.ops.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author frp
 */
@Service
@Slf4j
public class FlyHistoryServiceImpl implements FlyHistoryService {

    @Value("${video.srs-path}")
    private String srsPath;

    @Value("${video.minio-path}")
    private String minioPath;

    @Autowired
    private FlyHistoryMapper flyHistoryMapper;

    @Autowired
    private UavEsRepository repository;

    @Override
    public Page<FlyHistoryResDTO> listFlyHistory(String name, PageReqDTO pageReqDTO) {
        PageHelper.startPage(pageReqDTO.getPageNo(), pageReqDTO.getPageSize());
        return flyHistoryMapper.listFlyHistory(pageReqDTO.of(), name);
    }

    @Override
    public FlyHistoryResDTO getFlyHistoryDetail(String id) {
        return flyHistoryMapper.getFlyHistoryDetail(id);
    }

    @Override
    public void deleteFlyHistory(FlyHistoryReqDTO flyHistoryReqDTO) {
        if (Objects.isNull(flyHistoryReqDTO.getId())) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        flyHistoryReqDTO.setUserId(TokenUtil.getCurrentPersonNo());
        Integer result = flyHistoryMapper.deleteFlyHistory(flyHistoryReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.DELETE_ERROR);
        }
    }

    @Override
    public List<FlyHistoryDataResDTO> listFlyHistoryDataList(String startTime, String endTime, String deviceId) throws ParseException {
        // todo es搜索时间段内无人机飞行数据
        List<FlyHistoryDataResDTO> list = new ArrayList<>();
        Iterable<Uav> items = repository.search(getFlyHistoryDataBoolQueryBuilder(startTime, endTime, deviceId));
        for (Uav item : items) {
            FlyHistoryDataResDTO res = JSONObject.parseObject(item.getInfo(), FlyHistoryDataResDTO.class);
            list.add(res);
        }
        return list;
    }

    @Override
    public void startFly(FlyHistoryReqDTO flyHistoryReqDTO) {
        if (Objects.isNull(flyHistoryReqDTO)) {
            throw new CommonException(ErrorCode.PARAM_NULL_ERROR);
        }
        FlyHistoryResDTO res = flyHistoryMapper.getFlyHistoryDetail(flyHistoryReqDTO.getId());
        if (res != null && res.getId() != null) {
            throw new CommonException(ErrorCode.DATA_EXIST);
        }
        Integer result = flyHistoryMapper.addFlyHistory(flyHistoryReqDTO);
        if (result < 0) {
            throw new CommonException(ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void closeFly(FlyHistoryReqDTO flyHistoryReqDTO) {
        String flyId = flyHistoryReqDTO.getId();
        File file = new File(srsPath);
        File targetFile = new File(minioPath);
        File[] fileList = file.listFiles();

        if(fileList != null && fileList.length > 0){
            if(fileList[0].isFile()){
                try{
                    FileUtils.moveFileToDirectory(fileList[0],
                            targetFile,false);
                    flyHistoryReqDTO.setFlyVideo(minioPath + FilenameUtils.getName(fileList[0].getName()));
                    flyHistoryReqDTO.setLandTime(new Date());
                    flyHistoryMapper.updateFlyVideo(flyHistoryReqDTO);
                }catch (Exception e){
                    throw new CommonException(ErrorCode.UPDATE_ERROR);
                }
            }

        }

    }

    public static BoolQueryBuilder getFlyHistoryDataBoolQueryBuilder(String startTime, String endTime, String deviceId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery();
        queryBuilders.must(QueryBuilders.existsQuery("deviceId"));
        queryBuilders.must(QueryBuilders.existsQuery("createTime"));
        queryBuilders.must(QueryBuilders.matchQuery("deviceId", deviceId));
        queryBuilders.must(QueryBuilders.rangeQuery("createTime")
                .from(sdf.parse(startTime).getTime())
                .to(sdf.parse(endTime).getTime())
                .includeLower(true)
                .includeUpper(true)
                .format("yyyy-MM-dd HH:mm:ss||yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println(queryBuilders.toString());
        return queryBuilders;
    }
}
