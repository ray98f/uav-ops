package com.uav.ops.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frp
 */
@Data
public class MonthlyProblemNumResDTO {

    @ApiModelProperty(value = "月份")
    private String month;

    @ApiModelProperty(value = "数量")
    private String num;
}
