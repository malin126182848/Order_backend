package com.malin.order_backend.controller;

import com.malin.order_backend.viewobj.ResultVO;
import com.malin.order_backend.converter.OrderForm2OrderDTOConverter;
import com.malin.order_backend.dto.OrderDTO;
import com.malin.order_backend.enums.ResultEnum;
import com.malin.order_backend.exception.SellException;
import com.malin.order_backend.form.OrderForm;
import com.malin.order_backend.service.BuyerService;
import com.malin.order_backend.service.OrderService;
import com.malin.order_backend.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "买家订单相关接口")
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    @ApiOperation("创建订单的接口")
    public ResultVO<OrderDTO> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);


        return ResultVOUtil.success(createResult);
    }

    //订单列表
    @GetMapping("/list")
    @ApiOperation("订单列表的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "openid"),
            @ApiImplicitParam(name = "page", value = "页面号", defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "大小", defaultValue = "10")
    }
    )
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = PageRequest.of(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    @ApiOperation("订单详情的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "openid"),
            @ApiImplicitParam(name = "orderId", value = "订单编号")
    }
    )
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    @ApiOperation("取消订单的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "openid"),
            @ApiImplicitParam(name = "orderId", value = "订单编号")
    }
    )
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
