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
public class DangerRectifyResDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "隐患id")
    private String dangerId;

    @ApiModelProperty(value = "整改责任人")
    private String rectifyUserId;

    @ApiModelProperty(value = "整改责任人名")
    private String rectifyUserName;

    @ApiModelProperty(value = "整改措施")
    private String rectifyMeasure;

    @ApiModelProperty(value = "整改后图片")
    private String afterPic;

    @ApiModelProperty(value = "整改后图片文件")
    private List<File> afterPicFile;

    @ApiModelProperty(value = "整改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date rectifyTime;

    @ApiModelProperty(value = "是否消项 0 否 1 是")
    private Integer isEliminate;

}
