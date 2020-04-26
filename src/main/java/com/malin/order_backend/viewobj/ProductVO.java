package com.malin.order_backend.viewobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品(包含类目)
 */
@Data
@ApiModel
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 7097863777546530545L;

    @JsonProperty("name")
    @ApiModelProperty(value = "类目名称")
    private String categoryName;

    @JsonProperty("type")
    @ApiModelProperty(value = "类目编号")
    private Integer categoryType;

    @JsonProperty("foods")
    @ApiModelProperty(value = "食物列表")
    private List<ProductInfoVO> productInfoVOList;
}
