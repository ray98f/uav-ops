package com.uav.ops.utils.task;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.Common;
import com.google.common.base.Joiner;
import com.uav.ops.dto.req.VxSendTextMsgReqDTO;
import com.uav.ops.dto.res.CruisePlanResDTO;
import com.uav.ops.dto.res.DeviceResDTO;
import com.uav.ops.mapper.CruiseMapper;
import com.uav.ops.mapper.DeviceMapper;
import com.uav.ops.service.MsgService;
import com.uav.ops.utils.SendUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class AutoTask {

    @Value("${aliyun.accessKeyId}")
    private String keyId;

    @Value("${aliyun.accessKeySecret}")
    private String keySecret;

    @Value("${aliyun.verifyCode}")
    private String verifyCode;

    @Value("${aliyun.signName}")
    private String signName;

    @Resource
    private CruiseMapper cruiseMapper;

    @Resource
    private DeviceMapper deviceMapper;

    @Autowired
    private MsgService msgService;

    @Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void planRemind() throws Exception {
        List<CruisePlanResDTO> list = cruiseMapper.listRemindPlan();
        List<String> userIds = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (CruisePlanResDTO resDTO : list) {
                if (resDTO.getResponseUserId() == null || "".equals(resDTO.getResponseUserId())) {
                    continue;
                }
                userIds.add(resDTO.getResponseUserId());
                if (resDTO.getMobile() != null && !"".equals(resDTO.getMobile())) {

                    Client client = SendUtils.createClient(keyId, keySecret);
                    SendSmsRequest request = new SendSmsRequest()
                            .setPhoneNumbers(resDTO.getMobile())
                            .setSignName(signName)
                            .setTemplateCode(verifyCode)
                            .setTemplateParam("{\"hours\":\"" + resDTO.getHour() + "\",\"title\":\"" + resDTO.getPlanName() + "\"}");
                    SendSmsResponse response = client.sendSms(request);
                    if (!Common.equalString(response.body.code, "OK")) {
                        log.error("无人机巡检任务提醒通知-短信发送失败:{}", response.body.message);
                    }
                }
                ids.add(resDTO.getId());
            }
        }
//        if (!userIds.isEmpty()) {
//            VxSendTextMsgReqDTO vxSendTextMsgReqDTO = new VxSendTextMsgReqDTO();
//            vxSendTextMsgReqDTO.setTouser(Joiner.on("|").join(userIds));
//            vxSendTextMsgReqDTO.setText(new VxSendTextMsgReqDTO.Content("您有一条巡检任务需要执行，请前往无人机运维系统查看。"));
//            msgService.sendTextMsg(vxSendTextMsgReqDTO);
//        }
        if (!ids.isEmpty()) {
            cruiseMapper.remindPlan(ids);
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    @Async
    public void deviceMaintain() {
        List<DeviceResDTO> list = deviceMapper.listMaintainDevice();
        if (list != null && !list.isEmpty()) {
            deviceMapper.addDeviceWarning(list);
        }
    }
}
