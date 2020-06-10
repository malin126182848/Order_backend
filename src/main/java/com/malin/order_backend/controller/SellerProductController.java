package com.malin.order_backend.controller;

import com.malin.order_backend.dataobj.ProductInfo;
import com.malin.order_backend.exception.SellException;
import com.malin.order_backend.form.ProductForm;
import com.malin.order_backend.service.CategoryService;
import com.malin.order_backend.service.ProductService;
import com.malin.order_backend.utils.KeyUtil;
import com.malin.order_backend.utils.ResultVOUtil;
import com.malin.order_backend.viewobj.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 卖家端商品
 */
@RestController
@Api(tags = "卖家商品管理相关接口")
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("全部商品列表的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面号", defaultValue = "0"),
            @ApiImplicitParam(name = "size", value = "大小", defaultValue = "10")
    }
    )
    public ResultVO<List<ProductInfo>> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "100") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);

        return ResultVOUtil.success(productInfoPage.getContent());
    }

    /**
     * 商品上架
     * @param productId
     */
    @PostMapping("/on_sale")
    @ApiOperation("商品上架的接口")
    @ApiImplicitParam(name = "productId", value = "商品编号")
    public ResultVO onSale(@RequestParam("productId") String productId) {
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            return ResultVOUtil.error(6,e.getMessage());
        }
        return ResultVOUtil.success();
    }
    /**
     * 商品下架
     * @param productId
     */
    @PostMapping("/off_sale")
    @ApiOperation("商品下架的接口")
    @ApiImplicitParam(name = "productId", value = "商品编号")
    public ResultVO offSale(@RequestParam("productId") String productId) {
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            return ResultVOUtil.error(7,e.getMessage());
        }
        return ResultVOUtil.success();
    }

    @GetMapping("/show")
    @ApiOperation("商品展示接口")
    @ApiImplicitParam(name = "productId", value = "商品编号")
    public ResultVO<ProductInfo> show(@RequestParam(value = "productId", required = false) String productId) {
        if (StringUtils.isEmpty(productId)) {
            return ResultVOUtil.error(8, "编号为空");
        }
        ProductInfo productInfo = productService.findOne(productId);
        return  ResultVOUtil.success(productInfo);
    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("商品保存/更新的接口")
//    @Cacheable(cacheNames = "product", key = "123")
//    @Cacheable(cacheNames = "product", key = "456")
//    @CachePut(cacheNames = "product", key = "123")
    @CacheEvict(cacheNames = "product", allEntries = true, beforeInvocation = true)
    public ResultVO save(@Valid ProductForm form,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(9, bindingResult.getFieldError().getDefaultMessage());
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            //如果productId为空, 说明是新增
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productService.findOne(form.getProductId());
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            return ResultVOUtil.error(10, e.getMessage());
        }

        return ResultVOUtil.success();
    }
}
