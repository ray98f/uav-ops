package com.uav.ops.service;

import com.uav.ops.dto.req.VxSendTextMsgReqDTO;

/**
 * @author frp
 */
public interface MsgService {

    void sendTextMsg(VxSendTextMsgReqDTO vxSendTextMsgReqDTO);

}
