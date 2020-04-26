package com.malin.order_backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
@ApiModel
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    @ApiModelProperty(value = "买家姓名")
    private String name;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "手机号必填")
    @ApiModelProperty(value = "买家手机号")
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "地址必填")
    @ApiModelProperty(value = "买家地址")
    private String address;

    /**
     * 买家openid
     */
    @NotEmpty(message = "openid必填")
    @ApiModelProperty(value = "买家openid")
    private String openid;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    @ApiModelProperty(value = "购物车")
    private String items;
}
