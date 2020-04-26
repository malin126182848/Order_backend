package com.malin.order_backend.service;

import com.malin.order_backend.dataobj.ProductCategory;

import java.util.List;

/**
 * 类目
 */

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

    ProductCategory findByCategoryType(int i);
}
