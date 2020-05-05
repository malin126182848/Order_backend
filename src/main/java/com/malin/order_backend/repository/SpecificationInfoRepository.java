package com.malin.order_backend.repository;

import com.malin.order_backend.dataobj.ProductInfo;
import com.malin.order_backend.dataobj.SpecificationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecificationInfoRepository extends JpaRepository<SpecificationInfo, String> {
    List<ProductInfo> findByProductId(String productId);
}
