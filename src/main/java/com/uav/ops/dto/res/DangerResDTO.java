package com.uav.ops.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uav.ops.entity.File;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author frp
 */
@Data
public class DangerResDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "编号")
    private String no;

    @ApiModelProperty(value = "区域类型id")
    private String regionTypeId;

    @ApiModelProperty(value = "隐患区域id")
    private String regionId;

    @ApiModelProperty(value = "隐患区域名称")
    private String regionName;

    @ApiModelProperty(value = "隐患地点")
    private String address;

    @ApiModelProperty(value = "隐患问题内容")
    private String content;

    @ApiModelProperty(value = "整改前图片")
    private String beforePic;

    @ApiModelProperty(value = "整改前图片文件")
    private List<File> beforePicFile;

    @ApiModelProperty(value = "整改期限")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date rectifyTerm;

    @ApiModelProperty(value = "检查人id")
    private String checkUserId;

    @ApiModelProperty(value = "检查人名称")
    private String checkUserName;

    @ApiModelProperty(value = "检查时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date checkTime;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "状态 0 未整改 1 已整改")
    private Integer status;

    @ApiModelProperty(value = "隐患整改流程")
    private DangerRectifyResDTO dangerRectify;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createDate;

}
