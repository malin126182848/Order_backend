package com.malin.order_backend.repository;

import com.malin.order_backend.dataobj.ProductInfo;
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

class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    @Transactional
    public void add(){
/*        ProductInfo info = new ProductInfo();
        info.setProductId("233");
        info.setProductName("冰霜汉堡");
        info.setProductPrice(new BigDecimal(36));
        info.setProductDescription("好吃的冰霜小麦加秘制烤肉串");
        info.setProductIcon("http://xxxx.jpg");
        info.setProductStatus(0);//商品状态,0正常1下架
        info.setProductStock(10);//商品库存
        info.setCategoryType(1);
        ProductInfo result = repository.save(info);
        Assert.assertNotNull(result);*/

        ProductInfo info = new ProductInfo();
        info.setProductId("666");
        info.setProductName("海陆双拼");
        info.setProductPrice(new BigDecimal(24));
        info.setProductDescription("美味的库帕鱼肉加秘制烤肉串");
        info.setProductIcon("http://xxxx.jpg");
        info.setProductStatus(0);//商品状态,0正常1下架
        info.setProductStock(30);//商品库存
        info.setCategoryType(2);
        ProductInfo result = repository.save(info);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne(){
        ProductInfo info = repository.findById("haha123").get();
        Assert.assertEquals("haha123", info.getProductId());
    }

    @Test
    void findByProductStatus() {
        List<ProductInfo> list = repository.findByProductStatus(0);
        Assert.assertNotEquals(0, list.size());
    }
    @Test
    void findAll() {
        List<ProductInfo> list = repository.findAll();
        Assert.assertNotEquals(1, list.size());
    }
}