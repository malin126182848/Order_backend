package com.malin.order_backend.repository;

import com.malin.order_backend.dataobj.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    /**
     * 传入类型列表，查询包含列表中类型的所有数据
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory findByCategoryType(int i);
}
