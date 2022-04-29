package com.uav.ops.dto.res;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author frp
 */
@Data
@ApiModel
public class VxDeptResDTO {

    private Long id;

    private String name;

    private String name_en;

    private List<String> department_leader;

    private String department_leaders;

    private Long parentid;

    private Integer order;
}
