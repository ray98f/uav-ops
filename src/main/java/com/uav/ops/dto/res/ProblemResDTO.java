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
public class ProblemResDTO {

    @ApiModelProperty(value = "问题id")
    private String id;

    @ApiModelProperty(value = "问题标题")
    private String title;

    @ApiModelProperty(value = "问题类型id")
    private String typeId;

    @ApiModelProperty(value = "问题类型名称")
    private String typeName;

    @ApiModelProperty(value = "问题等级1轻、2中、3重、4严重")
    private Integer problemLevel;

    @ApiModelProperty(value = "状态 0未解决 1已解决 9关闭")
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

    @ApiModelProperty(value = "新增时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createDate;
}
