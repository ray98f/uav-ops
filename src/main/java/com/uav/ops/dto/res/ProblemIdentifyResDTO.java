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
public class ProblemIdentifyResDTO {

    @ApiModelProperty(value = "识别记录ID")
    private String id;

    @ApiModelProperty(value = "地图坐标-经度")
    private Double lng;

    @ApiModelProperty(value = "地图坐标-纬度")
    private Double lat;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "问题描述")
    private String info;

    @ApiModelProperty(value = "问题照片")
    private String imageUrl;

    @ApiModelProperty(value = "已识别标识 0未识别，1已识别")
    private Integer isChecked;

    @ApiModelProperty(value = "状态 0正常9停用")
    private Integer status;

    @ApiModelProperty(value = "新增时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createDate;
}
