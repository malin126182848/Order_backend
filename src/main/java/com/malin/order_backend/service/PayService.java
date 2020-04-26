package com.malin.order_backend.service;

import com.malin.order_backend.dto.OrderDTO;

import java.util.Map;


/**
 * 支付
 */
public interface PayService {

    Map create(OrderDTO orderDTO);

    Map notify(String notifyData);

    Map refund(OrderDTO orderDTO);
}
