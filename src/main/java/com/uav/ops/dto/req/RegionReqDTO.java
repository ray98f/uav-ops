package com.uav.ops.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frp
 */
@Data
public class RegionReqDTO {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "类型id")
    private String typeId;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "上级区域id")
    private String parentId;

    @ApiModelProperty(value = "区域路径id")
    private String parentIds;

    @ApiModelProperty(value = "区域路径")
    private String parentNames;

    @ApiModelProperty(value = "区域名称")
    private String name;

    @ApiModelProperty(value = "地图坐标-纬度")
    private Double lng;

    @ApiModelProperty(value = "地图坐标-经度")
    private Double lat;

    @ApiModelProperty(value = "区域层级")
    private Integer level;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "主要风险")
    private String mainRisk;

    @ApiModelProperty(value = "主要防控措施")
    private String mainPrevent;

    @ApiModelProperty(value = "四色图")
    private String pic;

    @ApiModelProperty(value = "二维码")
    private String qrCode;

    @ApiModelProperty(value = "责任单位")
    private String responseUnit;

    @ApiModelProperty(value = "责任工区")
    private String responseWorkArea;

    @ApiModelProperty(value = "状态 0 启用 1 停用")
    private Integer status;

    @ApiModelProperty(value = "创建人id")
    private String userId;

}
