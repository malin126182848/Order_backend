package com.malin.order_backend.repository;

import com.malin.order_backend.dataobj.SpecificationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecificationCategoryRepository extends JpaRepository<SpecificationCategory, Integer> {
    List<SpecificationCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    SpecificationCategory findByCategoryType(int i);
}
