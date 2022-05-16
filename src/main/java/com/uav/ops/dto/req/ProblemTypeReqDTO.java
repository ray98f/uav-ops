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
public class ProblemTypeReqDTO {

    @ApiModelProperty(value = "类型ID")
    private String id;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "类型编码")
    private String typeCode;

    @ApiModelProperty(value = "状态 0正常9停用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private String userId;
}
