package com.uav.ops.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author frp
 */
@Data
public class FlyHistoryReqDTO {

    @ApiModelProperty(value = "记录ID")
    private String id;

    @ApiModelProperty(value = "无人机ID")
    private String deviceId;

    @ApiModelProperty(value = "无人机名称")
    private String deviceName;

    @ApiModelProperty(value = "飞行类型:1:手动控制 2:自动巡航")
    private Integer flyType;

    @ApiModelProperty(value = "飞行起点地址")
    private String flyStart;

    @ApiModelProperty(value = "飞行终点地址")
    private String flyEnd;

    @ApiModelProperty(value = "起飞时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date takeoffTime;

    @ApiModelProperty(value = "降落时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date landTime;

    @ApiModelProperty(value = "巡航路线id(自动巡航类型)")
    private String lineId;

    @ApiModelProperty(value = "巡航路线名称(自动巡航类型)")
    private String lineName;

    @ApiModelProperty(value = "飞行里程 km")
    private Integer mileage;

    @ApiModelProperty(value = "飞行时间 s")
    private Long flyTime;

    @ApiModelProperty(value = "视频地址")
    private String flyVideo;

    @ApiModelProperty(value = "创建时间")
    private String userId;
}
