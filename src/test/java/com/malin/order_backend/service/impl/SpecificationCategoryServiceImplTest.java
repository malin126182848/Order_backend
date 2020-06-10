package com.malin.order_backend.service.impl;

import com.malin.order_backend.dataobj.SpecificationCategory;
import com.malin.order_backend.service.SpecificationCategoryService;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpecificationCategoryServiceImplTest {

    @Autowired
    private SpecificationCategoryService categoryService;

    @Test
    public void findOne() throws Exception {
        SpecificationCategory specificationCategory = categoryService.findOne(1);
        Assert.assertEquals(Integer.valueOf(1), specificationCategory.getCategoryId());
    }

    @Test
    public void findAll() throws Exception {
        List<SpecificationCategory> specificationCategoryList = categoryService.findAll();
        Assert.assertNotEquals(1, specificationCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<SpecificationCategory> specificationCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0, specificationCategoryList.size());
    }

    @Test
    public void findByCategoryType() throws Exception {
        SpecificationCategory specificationCategory = categoryService.findByCategoryType(2);
        Assert.assertNotEquals("", specificationCategory.getCategoryName());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        SpecificationCategory specificationCategory = new SpecificationCategory("吃货专享", 10);
        SpecificationCategory result = categoryService.save(specificationCategory);
        Assert.assertNotNull(result);
    }
}