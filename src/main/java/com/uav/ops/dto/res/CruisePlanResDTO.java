package com.uav.ops.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author frp
 */
@Data
public class CruisePlanResDTO {

    @ApiModelProperty(value = "计划ID")
    private String id;

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "起飞位置")
    private String address;

    @ApiModelProperty(value = "计划类型:1:日计划 2:周计划 3:月计划")
    private Integer planType;

    @ApiModelProperty(value = "计划开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date startDay;

    @ApiModelProperty(value = "计划结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date endDay;

    @ApiModelProperty(value = "计划指定日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date specifyDay;

    @ApiModelProperty(value = "计划时间")
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(
            pattern = "HH:mm",
            timezone = "GMT+8"
    )
    private Date specifyTime;

    @ApiModelProperty(value = "巡检类型: 1通道巡检,2精细化巡检")
    private Integer cruiseType;

    @ApiModelProperty(value = "操作类型:1自动巡检,2手动巡检")
    private Integer opsType;

    @ApiModelProperty(value = "巡检路线ID")
    private String lineId;

    @ApiModelProperty(value = "巡检路线详情")
    private CruiseLineResDTO lineInfo;

    @ApiModelProperty(value = "无人机ID")
    private String deviceId;

    @ApiModelProperty(value = "无人机详情")
    private DeviceResDTO deviceInfo;

    @ApiModelProperty(value = "无信号自动退出任务标记 0否,1是")
    private Integer autoEndFlag;

    @ApiModelProperty(value = "结束动作 1悬停 2降落 3返航")
    private Integer endAction;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createDate;

    @ApiModelProperty(value = "责任人")
    private String responseUserId;

    @ApiModelProperty(value = "责任人")
    private String responseUserName;

    @ApiModelProperty(value = "提前提醒小时数")
    private Integer hour;

    @ApiModelProperty(value = "提醒时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date remindTime;


}
