package com.uav.ops.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frp
 */
@Data
public class FlyHistoryDataResDTO {

    @ApiModelProperty(value = "巡航点ID")
    private String id;

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "飞行高度")
    private Integer height;

    @ApiModelProperty(value = "飞行速度 m/s")
    private Integer speed;

    @ApiModelProperty(value = "经度")
    private Double lng;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "电量")
    private Integer electricity;
}
