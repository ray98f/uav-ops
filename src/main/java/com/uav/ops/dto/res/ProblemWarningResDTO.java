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
public class ProblemWarningResDTO {

    @ApiModelProperty(value = "问题预警ID")
    private String id;

    @ApiModelProperty(value = "预警信息")
    private String warningInfo;

    @ApiModelProperty(value = "关联问题ID")
    private String problemId;

    @ApiModelProperty(value = "问题标题")
    private String problemTitle;

    @ApiModelProperty(value = "状态 0正常 9关闭")
    private Integer status;

    @ApiModelProperty(value = "新增时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createDate;
}
