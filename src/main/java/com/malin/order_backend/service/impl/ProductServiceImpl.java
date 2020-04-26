package com.malin.order_backend.service.impl;

import com.malin.order_backend.dataobj.ProductInfo;
import com.malin.order_backend.config.ImgHostConfig;
import com.malin.order_backend.dto.CartDTO;
import com.malin.order_backend.enums.ProductStatusEnum;
import com.malin.order_backend.enums.ResultEnum;
import com.malin.order_backend.exception.SellException;
import com.malin.order_backend.repository.ProductInfoRepository;
import com.malin.order_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
//@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    //@Autowired
    private ImgHostConfig imgHostConfig = new ImgHostConfig();

    @Override
//    @Cacheable(key = "123")
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> productInfoOptional = repository.findById(productId);
/*        if (productInfoOptional.isPresent()) {
            return productInfoOptional.get().addImageHost(imgHostConfig.getImageHost());
        }
        return null;*/

        productInfoOptional.ifPresent(e -> e.addImageHost(imgHostConfig.getImageHost()));
        return productInfoOptional.orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode()).stream()
                .map(e -> e.addImageHost(imgHostConfig.getImageHost()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        Page<ProductInfo> productInfoPage = repository.findAll(pageable);
        productInfoPage.getContent()
                .forEach(e -> e.addImageHost(imgHostConfig.getImageHost()));
        return productInfoPage;
    }

    @Override
//    @CachePut(key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        Optional<ProductInfo> productInfoOptional = repository.findById(productInfo.getProductId());
        if (productInfoOptional.isPresent()) {
            productInfo.setCreateTime(productInfoOptional.get().getCreateTime());
            productInfo.setUpdateTime(productInfoOptional.get().getUpdateTime());
        }
        return repository.save(productInfo).addImageHost(imgHostConfig.getImageHost());
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
