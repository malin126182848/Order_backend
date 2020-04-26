package com.malin.order_backend.service.impl;

import com.malin.order_backend.dto.OrderDTO;
import com.malin.order_backend.enums.ResultEnum;
import com.malin.order_backend.exception.SellException;
import com.malin.order_backend.service.OrderService;
import com.malin.order_backend.service.PayService;
import com.malin.order_backend.utils.JsonUtil;
import com.malin.order_backend.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "点餐订单";

/*    @Autowired
    private BestPayServiceImpl bestPayService;*/

    @Autowired
    private OrderService orderService;

    @Override
    public Map create(OrderDTO orderDTO) {
        Map<String, String> payRequest = new HashMap<>();
        payRequest.put("Openid",orderDTO.getBuyerOpenid());
        payRequest.put("OrderAmount",orderDTO.getOrderAmount().toString());
        payRequest.put("OrderId",orderDTO.getOrderId());
        payRequest.put("OrderName",ORDER_NAME);
        log.info("【支付】发起支付, request={}", JsonUtil.toJson(payRequest));

        Map<String, Object> payResponse = new HashMap<>();
        payResponse.put("PayResult","success"); //bestPayService.pay(payRequest);
        payResponse.put("Amount",orderDTO.getOrderAmount().doubleValue());
        payResponse.put("OrderId",orderDTO.getOrderId());
        log.info("【支付】发起支付, response={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public Map notify(String notifyData) {
        //1. 验证签名
        //2. 支付的状态
        //3. 支付金额
        //4. 支付人(下单人 == 支付人)

        Map<String,String> payResponse = JsonUtil.toMap(notifyData);
        log.info("【支付】异步通知, payResponse={}", JsonUtil.toJson(payResponse));

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.get("OrderId"));

        //判断订单是否存在
        if (orderDTO == null) {
            log.error("【支付】异步通知, 订单不存在, orderId={}", payResponse.get("OrderId"));
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致(0.10   0.1)
        if (!MathUtil.equals(Double.valueOf(payResponse.get("OrderAmount")), orderDTO.getOrderAmount().doubleValue())) {
            log.error("【支付】异步通知, 订单金额不一致, orderId={}, 通知金额={}, 系统金额={}",
                    payResponse.get("OrderId"),
                    payResponse.get("OrderAmount"),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单的支付状态
        orderService.paid(orderDTO);

        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public Map refund(OrderDTO orderDTO) {
        Map<String,String> refundRequest = new HashMap();
        refundRequest.put("OrderId",orderDTO.getOrderId());
        refundRequest.put("OrderAmount",orderDTO.getOrderAmount().toString());
        log.info("【退款】request={}", JsonUtil.toJson(refundRequest));

        Map<String,String> refundResponse = new HashMap();
        refundResponse.put("RefundResult", "Success");
        refundResponse.put("OrderId",orderDTO.getOrderId());
        refundResponse.put("OrderAmount",orderDTO.getOrderAmount().toString());
        log.info("【退款】response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
