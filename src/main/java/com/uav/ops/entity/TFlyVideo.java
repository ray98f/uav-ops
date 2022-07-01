package com.uav.ops.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangxin
 */
@Data
@ApiModel
public class TFlyVideo {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "飞行记录ID")
    private String flyHistoryId;

    @ApiModelProperty(value = "视频链接")
    private String videoUrl;


}
