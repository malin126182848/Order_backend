package com.malin.order_backend.service.impl;

import com.malin.order_backend.dataobj.SpecificationCategory;
import com.malin.order_backend.repository.SpecificationCategoryRepository;
import com.malin.order_backend.service.SpecificationCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationCategoryServiceImpl implements SpecificationCategoryService {

    @Autowired
    private SpecificationCategoryRepository repository;

    @Override
    public SpecificationCategory findOne(Integer categoryId) {
        // 查不到返回null  .get 抛异常
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<SpecificationCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<SpecificationCategory> findByCategoryTypeIn(List<Integer> categoryTypeList){
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public SpecificationCategory findByCategoryType(int i){
        return repository.findByCategoryType(i);
    }

    @Override
    public SpecificationCategory save(SpecificationCategory productCategory) {
        return repository.save(productCategory);
    }
}
