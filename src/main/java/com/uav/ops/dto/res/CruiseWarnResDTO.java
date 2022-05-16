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
public class CruiseWarnResDTO {

    @ApiModelProperty(value = "预警ID")
    private String id;

    @ApiModelProperty(value = "预警类型:1:未巡检 2:巡检路线异常 3:起飞异常 4 低电量 5 设备故障")
    private Integer warningType;

    @ApiModelProperty(value = "预警信息")
    private String warningInfo;

    @ApiModelProperty(value = "关联计划id")
    private String planId;

    @ApiModelProperty(value = "关联计划名称")
    private String planName;

    @ApiModelProperty(value = "关联故障id")
    private String faultId;

    @ApiModelProperty(value = "关联故障信息")
    private String faultInfo;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createDate;

    @ApiModelProperty(value = "状态 0未解决 1已解决 9关闭")
    private Integer status;
}
