package com.malin.order_backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class CategoryForm {

    @ApiModelProperty(value = "类目id", example = "123")
    private Integer categoryId;

    /** 类目名字. */
    @ApiModelProperty(value = "类目名")
    private String categoryName;

    /** 类目编号. */
    @ApiModelProperty(value = "类目编号", example = "123")
    private Integer categoryType;
}
