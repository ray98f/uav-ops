package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.CruiseLineReqDTO;
import com.uav.ops.dto.req.CruisePointReqDTO;
import com.uav.ops.dto.req.FlyHistoryReqDTO;
import com.uav.ops.dto.res.CruiseLineResDTO;
import com.uav.ops.dto.res.CruisePointResDTO;
import com.uav.ops.dto.res.FlyHistoryDataResDTO;
import com.uav.ops.dto.res.FlyHistoryResDTO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

/**
 * @author frp
 */
public interface FlyHistoryService {

    Page<FlyHistoryResDTO> listFlyHistory(String name, PageReqDTO pageReqDTO);

    FlyHistoryResDTO getFlyHistoryDetail(String id);

    void deleteFlyHistory(FlyHistoryReqDTO flyHistoryReqDTO);

    List<FlyHistoryDataResDTO> listFlyHistoryDataList(String startTime, String endTime, String deviceId) throws ParseException;

    void startFly(FlyHistoryReqDTO flyHistoryReqDTO);

    void closeFly(FlyHistoryReqDTO flyHistoryReqDTO);

}
