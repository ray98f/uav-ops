package com.uav.ops.dto.req;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * @author frp
 */
@Data
public class ProblemBindReqDTO {

    @ApiModelProperty(value = "问题id")
    private String problemId;

    @ApiModelProperty(value = "问题识别记录ids")
    private List<String> identifyIds;

    @ApiModelProperty(value = "类型 1 绑定 2 解绑")
    private Integer type;
}
