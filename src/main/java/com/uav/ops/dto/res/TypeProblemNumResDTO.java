package com.uav.ops.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author frp
 */
@Data
public class TypeProblemNumResDTO {

    @ApiModelProperty(value = "类型id")
    private String typeId;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "类型问题数")
    private List<MonthlyProblemNumResDTO> monthlyProblemNum;
}
