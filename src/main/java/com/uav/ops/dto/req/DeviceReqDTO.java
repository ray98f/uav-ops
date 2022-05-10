package com.uav.ops.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frp
 */
@Data
public class DeviceReqDTO {

    @ApiModelProperty(value = "设备ID")
    private String id;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备类型 1:无人机 2:遥控 3:云台 4:其他")
    private Integer type;

    @ApiModelProperty(value = "设备编码")
    private String deviceCode;

    @ApiModelProperty(value = "设备为无人机时填")
    private String pushUrl;

    @ApiModelProperty(value = "状态:0正常,1故障,9停用")
    private Integer status;

    @ApiModelProperty(value = "连接状态:0未连接,1连接")
    private Integer isConn;

    @ApiModelProperty(value = "直播状态:0未开播,1直播中")
    private Integer isLive;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作用户id")
    private String userId;
}
