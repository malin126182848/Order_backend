package com.malin.order_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.malin.order_backend.dataobj.OrderDetail;
import com.malin.order_backend.enums.OrderStatusEnum;
import com.malin.order_backend.enums.PayStatusEnum;
import com.malin.order_backend.utils.EnumUtil;
import com.malin.order_backend.utils.serializer.Date2LongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@ApiModel
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单id. */
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /** 买家名字. */
    @ApiModelProperty(value = "买家名字")
    private String buyerName;

    /** 买家手机号. */
    @ApiModelProperty(value = "买家手机号")
    private String buyerPhone;

    /** 买家地址. */
    @ApiModelProperty(value = "买家地址")
    private String buyerAddress;

    /** 买家Openid. */
    @ApiModelProperty(value = "买家Openid")
    private String buyerOpenid;

    /** 订单总金额. */
    @ApiModelProperty(value = "订单总金额", example = "0")
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    @ApiModelProperty(value = "订单状态, 默认为0新下单,1为完成订单,2为取消订单", example = "0")
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    @ApiModelProperty(value = "支付状态, 默认为0未支付", example = "0")
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
