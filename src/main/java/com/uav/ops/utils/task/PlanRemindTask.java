package com.uav.ops.utils.task;

import com.google.common.base.Joiner;
import com.uav.ops.dto.req.VxSendTextMsgReqDTO;
import com.uav.ops.dto.res.CruisePlanResDTO;
import com.uav.ops.mapper.CruiseMapper;
import com.uav.ops.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author frp
 */
@Component
@Slf4j
public class PlanRemindTask {

    @Resource
    private CruiseMapper cruiseMapper;

    @Autowired
    private MsgService msgService;

    @Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void planRemind() {
        List<CruisePlanResDTO> list = cruiseMapper.listRemindPlan();
        List<String> userIds = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (CruisePlanResDTO resDTO : list) {
                if (resDTO.getResponseUserId() == null || "".equals(resDTO.getResponseUserId())) {
                    continue;
                }
                userIds.add(resDTO.getResponseUserId());
                ids.add(resDTO.getId());
            }
        }
        VxSendTextMsgReqDTO vxSendTextMsgReqDTO = new VxSendTextMsgReqDTO();
        if (!userIds.isEmpty()) {
            vxSendTextMsgReqDTO.setTouser(Joiner.on("|").join(userIds));
            vxSendTextMsgReqDTO.setText(new VxSendTextMsgReqDTO.Content("您有一条巡检任务需要执行，请前往无人机运维系统查看。"));
            msgService.sendTextMsg(vxSendTextMsgReqDTO);
        }
        if (!ids.isEmpty()) {
            cruiseMapper.remindPlan(ids);
        }
    }
}
