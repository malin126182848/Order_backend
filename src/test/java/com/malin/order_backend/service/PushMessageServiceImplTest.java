package com.malin.order_backend.service;

import com.malin.order_backend.dto.OrderDTO;
import com.malin.order_backend.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

//    @Autowired
//    private PushMessageServiceImpl pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1499097346204378750");
       // pushMessageService.orderStatus(orderDTO);
    }

}