package com.malin.order_backend.viewobj;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情
 */
@Data
@ApiModel
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = -3895834204864685262L;

    @JsonProperty("id")
    @ApiModelProperty(value = "商品id")
    private String productId;

    @JsonProperty("name")
    @ApiModelProperty(value = "商品名称")
    private String productName;

    @JsonProperty("price")
    @ApiModelProperty(value = "价格", example = "0.0")
    private BigDecimal productPrice;

    @JsonProperty("description")
    @ApiModelProperty(value = "描述")
    private String productDescription;

    @JsonProperty("icon")
    @ApiModelProperty(value = "小图")
    private String productIcon;
}
