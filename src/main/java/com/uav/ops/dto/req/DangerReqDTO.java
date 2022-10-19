package com.uav.ops.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author frp
 */
@Data
public class DangerReqDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "编号")
    private String no;

    @ApiModelProperty(value = "隐患区域")
    private String regionId;

    @ApiModelProperty(value = "隐患地点")
    private String address;

    @ApiModelProperty(value = "隐患问题内容")
    private String content;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "整改前图片")
    private String beforePic;

    @ApiModelProperty(value = "整改期限")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date rectifyTerm;

    @ApiModelProperty(value = "检查人id")
    private String checkUserId;

    @ApiModelProperty(value = "检查时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date checkTime;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "状态 0 未整改 1 已整改")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "可见人列表")
    private List<String> userIds;

}
