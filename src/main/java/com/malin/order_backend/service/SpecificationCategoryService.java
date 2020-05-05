package com.malin.order_backend.service;

import com.malin.order_backend.dataobj.SpecificationCategory;

import java.util.List;

public interface SpecificationCategoryService {

    SpecificationCategory findOne(Integer categoryId);

    List<SpecificationCategory> findAll();

    List<SpecificationCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    SpecificationCategory save(SpecificationCategory specificationCategory);

    SpecificationCategory findByCategoryType(int i);
}
