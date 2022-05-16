package com.uav.ops.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frp
 */
@Data
public class ProblemWarningReqDTO {

    @ApiModelProperty(value = "问题预警ID")
    private String id;

    @ApiModelProperty(value = "预警信息")
    private String warningInfo;

    @ApiModelProperty(value = "关联问题ID")
    private String problemId;

    @ApiModelProperty(value = "状态 0正常 9关闭")
    private Integer status;

    @ApiModelProperty(value = "操作人id")
    private String userId;
}
