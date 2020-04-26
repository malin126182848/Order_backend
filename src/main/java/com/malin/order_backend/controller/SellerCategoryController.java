package com.malin.order_backend.controller;

import com.malin.order_backend.dataobj.ProductCategory;
import com.malin.order_backend.exception.SellException;
import com.malin.order_backend.form.CategoryForm;
import com.malin.order_backend.service.CategoryService;
import com.malin.order_backend.utils.ResultVOUtil;
import com.malin.order_backend.viewobj.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卖家类目
 */
@RestController
@Api(tags = "商品类目管理相关接口")
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("类目列表的接口")
    public ResultVO<ProductCategory> list() {
        Map<String, Object> map = new HashMap<>();
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return ResultVOUtil.success(map);
    }

    /**
     * 展示
     * @param categoryId
     * @return
     */
    @GetMapping("/show")
    @ApiOperation("展示单个类目的接口")
    @ApiImplicitParam(name = "categoryId", value = "类目id", required = false)
    public ResultVO<ProductCategory> index(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        Map<String, Object> map = new HashMap<>();
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("category", productCategory);
        }

        return ResultVOUtil.success(map);
    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("保存/更新类目的接口")
    public ResultVO<ProductCategory> save(@Valid CategoryForm form,
                             BindingResult bindingResult/*,
                             Map<String, Object> map*/) {
        if (bindingResult.hasErrors()) {
//            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
//            map.put("url", "/sell/seller/category/index");
            return ResultVOUtil.error(2, bindingResult.getFieldError().getDefaultMessage());
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        } catch (SellException e) {
//            map.put("msg", e.getMessage());
//            map.put("url", "/sell/seller/category/index");
            return ResultVOUtil.error(3,  e.getMessage());
        }

//        map.put("url", "/sell/seller/category/list");
        return ResultVOUtil.success();
    }
}
