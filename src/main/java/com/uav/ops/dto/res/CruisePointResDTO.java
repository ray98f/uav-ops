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
public class CruisePointResDTO {

    @ApiModelProperty(value = "巡航点ID")
    private String id;

    @ApiModelProperty(value = "巡航点名称")
    private String pointName;

    @ApiModelProperty(value = "所属路线ID")
    private String lineId;

    @ApiModelProperty(value = "地图坐标-经度")
    private Double lng;

    @ApiModelProperty(value = "地图坐标-纬度")
    private Double lat;

    @ApiModelProperty(value = "巡航点飞行高度 m")
    private Integer pointHeight;

    @ApiModelProperty(value = "巡航点 飞行速度 m/s")
    private Integer pointSpeed;

    @ApiModelProperty(value = "航点序号")
    private Integer sort;

    @ApiModelProperty(value = "状态:0正常,9停用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "到达巡航点动作:1:悬停 2开始拍照 3开始录像 4停止拍照 5停止录像")
    private Integer actionType;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createDate;
}
