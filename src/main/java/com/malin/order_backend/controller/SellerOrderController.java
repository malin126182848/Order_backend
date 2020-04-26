package com.malin.order_backend.controller;

import com.malin.order_backend.dto.OrderDTO;
import com.malin.order_backend.enums.ResultEnum;
import com.malin.order_backend.exception.SellException;
import com.malin.order_backend.service.OrderService;
import com.malin.order_backend.utils.ResultVOUtil;
import com.malin.order_backend.viewobj.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 卖家端订单
 */
@RestController
@Api(tags = "卖家订单管理相关接口")
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 第几页, 从1页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("全部订单列表的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面号", defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "大小", defaultValue = "10")
    }
    )
    public ResultVO<List<OrderDTO>> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest request = PageRequest.of(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
//        map.put("orderDTOPage", orderDTOPage);
//        map.put("currentPage", page);
//        map.put("size", size);
//        orderDTOPage.getTotalPages()
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    @ApiOperation("取消订单的接口")
    @ApiImplicitParam(name = "orderId", value = "订单编号")
    public ResultVO cancel(@RequestParam("orderId") String orderId/*,
                           Map<String, Object> map*/) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端取消订单】发生异常{}", e);
//            map.put("msg", e.getMessage());
//            map.put("url", "/sell/seller/order/list");
            return ResultVOUtil.error(4, e.getMessage());
        }

//        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
//        map.put("url", "/sell/seller/order/list");
        return ResultVOUtil.success(null, ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation("查看当订单详情的接口")
    @ApiImplicitParam(name = "orderId", value = "订单编号")
    public ResultVO<OrderDTO> detail(@RequestParam("orderId") String orderId/*,
                               Map<String, Object> map*/) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
//            map.put("msg", e.getMessage());
//            map.put("url", "/sell/seller/order/list");
            return ResultVOUtil.error(5, e.getMessage() );
        }

//        map.put("orderDTO", orderDTO);
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @PostMapping("/finish")
    @ApiOperation("完结订单的接口")
    @ApiImplicitParam(name = "orderId", value = "订单编号")
    public ResultVO finished(@RequestParam("orderId") String orderId/*,
                                 Map<String, Object> map*/) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端完结订单】发生异常{}", e);
//            map.put("msg", e.getMessage());
//            map.put("url", "/sell/seller/order/list");
            return ResultVOUtil.error(5, e.getMessage() );
        }

//        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
//        map.put("url", "/sell/seller/order/list");
        return ResultVOUtil.success(null, ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
    }
}
