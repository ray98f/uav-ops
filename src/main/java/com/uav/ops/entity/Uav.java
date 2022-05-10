package com.uav.ops.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @author frp
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "uav")
public class Uav {

    @Id
    @ApiModelProperty(value = "无人机id")
    private String id;

    @ApiModelProperty(value = "文件名")
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String deviceName;

    @ApiModelProperty(value = "飞行数据 json")
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String info;

    @ApiModelProperty(value = "创建时间")
    @Field(type = FieldType.Keyword, index = false)
    private Long createTime;
}
