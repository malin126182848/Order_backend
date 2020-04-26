package com.malin.order_backend.repository;

import com.malin.order_backend.dataobj.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

/*    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findById(123456).orElse(null);
        //Assert.assertNotEquals(0, result.size());
        System.out.println(productCategory.toString());
    }*/

    @Test
    @Transactional
    public void saveTest() {
        ProductCategory productCategory1 = new ProductCategory("外卖推荐", 1);
        ProductCategory result1 = repository.save(productCategory1);
        Assert.assertNotNull(result1);
//        Assert.assertNotEquals(null, result);
        ProductCategory productCategory2 = new ProductCategory("主食", 2);
        ProductCategory result2 = repository.save(productCategory2);
        Assert.assertNotNull(result2);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(2,3,4);

        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void updateTest() {
        List<Integer> list = Arrays.asList(4);
        ProductCategory productCategory = repository.findByCategoryType(4);
        Assert.assertEquals(new String("男生最爱"), productCategory.getCategoryName());
        productCategory.setCategoryName("美食拼盘");
        //ProductCategory productCategory = new ProductCategory("美食拼盘", 4);
        ProductCategory result = repository.save(productCategory);
        Assert.assertEquals(new String("美食拼盘"), result.getCategoryName());
    }
}