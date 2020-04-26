package com.malin.order_backend.controller;

import com.malin.order_backend.dto.OrderDTO;
import com.malin.order_backend.enums.ResultEnum;
import com.malin.order_backend.exception.SellException;
import com.malin.order_backend.service.OrderService;
import com.malin.order_backend.service.PayService;
import com.malin.order_backend.utils.JsonUtil;
import com.malin.order_backend.utils.ResultVOUtil;
import com.malin.order_backend.viewobj.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付
 */
@RestController
@Api(tags = "支付相关接口")
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @PostMapping("/create")
    @ApiOperation("创建交易的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号"),
            @ApiImplicitParam(name = "returnUrl", value = "支付平台api")
    }
    )
    public ResultVO create(@RequestParam("orderId") String orderId,
                           @RequestParam("returnUrl") String returnUrl) {
        Map<String, Object> map = new HashMap<>();
        //1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2. 发起支付
        Map payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return ResultVOUtil.success(map);
    }

    /**
     * 通知
     * @param notifyData
     */
    @PostMapping("/notify")
    @ApiOperation("支付通知的接口")
    @ApiImplicitParam(name = "notifyData", value = "通知数据")
    public ResultVO notify(@RequestBody String notifyData) {
        payService.notify(notifyData);

        //返回给处理结果
        return ResultVOUtil.success();
    }
}
