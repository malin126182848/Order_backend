package com.malin.order_backend.repository;

import com.malin.order_backend.dataobj.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest

class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    @Transactional
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("100010001000");
        orderDetail.setOrderId("123123123");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductId("12121212");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(5.5));
        orderDetail.setProductQuantity(2);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetailList = repository.findByOrderId("123123123");
        Assert.assertNotEquals(0, orderDetailList.size());
    }
}