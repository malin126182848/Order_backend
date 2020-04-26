package com.malin.order_backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
@ApiModel
public class ProductForm {

    @ApiModelProperty(value = "商品id")
    private String productId;

    /** 名字. */
    @ApiModelProperty(value = "商品名字")
    private String productName;

    /** 单价. */
    @ApiModelProperty(value = "单价" ,example = "123")
    private BigDecimal productPrice;

    /** 库存. */
    @ApiModelProperty(value = "库存", example = "123")
    private Integer productStock;

    /** 描述. */
    @ApiModelProperty(value = "描述")
    private String productDescription;

    /** 小图. */
    @ApiModelProperty(value = "小图")
    private String productIcon;

    /** 类目编号. */
    @ApiModelProperty(value = "类目编号", example = "123")
    private Integer categoryType;
}
