package com.uav.ops.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author frp
 */
@Data
public class CruiseLineResDTO {

    @ApiModelProperty(value = "巡航路线ID")
    private String id;

    @ApiModelProperty(value = "路线名称")
    private String lineName;

    @ApiModelProperty(value = "巡航点数量")
    private Integer pointCount;

    @ApiModelProperty(value = "公里数 km")
    private Integer mileage;

    @ApiModelProperty(value = "飞行高度 m")
    private Integer flyHeight;

    @ApiModelProperty(value = "飞行速度 m/s")
    private Integer flySpeed;

    @ApiModelProperty(value = "预计飞行时间 秒")
    private Integer estimatedTime;

    @ApiModelProperty(value = "状态:0正常,9停用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createDate;
}
