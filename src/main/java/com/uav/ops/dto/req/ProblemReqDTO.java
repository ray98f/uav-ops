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
public class ProblemReqDTO {

    @ApiModelProperty(value = "问题id")
    private String id;

    @ApiModelProperty(value = "问题标题")
    private String title;

    @ApiModelProperty(value = "问题描述")
    private String info;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "问题照片")
    private String imageUrl;

    @ApiModelProperty(value = "问题类型id")
    private String typeId;

    @ApiModelProperty(value = "问题等级1轻、2中、3重、4严重")
    private Integer problemLevel;

    @ApiModelProperty(value = "状态 0未解决 1整改完成待审核  2已解决  9关闭")
    private Integer status;

    @ApiModelProperty(value = "解决时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date solveTime;

    @ApiModelProperty(value = "解决时长 小时")
    private Integer solveDuration;

    @ApiModelProperty(value = "整改责任人")
    private String rectifyUserId;

    @ApiModelProperty(value = "整改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date rectifyTime;

    @ApiModelProperty(value = "整改措施")
    private String rectifyMeasure;

    @ApiModelProperty(value = "整改后图片")
    private String afterPic;

    @ApiModelProperty(value = "是否暂存 0 否 1 是")
    private Integer isUse;

    @ApiModelProperty(value = "问题识别id")
    private String identifyId;

    @ApiModelProperty(value = "操作人id")
    private String userId;
}
