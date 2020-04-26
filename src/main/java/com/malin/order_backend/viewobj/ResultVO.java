package com.malin.order_backend.viewobj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 */
@Data
@ApiModel
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 3068837394742385883L;

    /** 错误码. */
    @ApiModelProperty(value = "错误码")
    private Integer code;

    /** 提示信息. */
    @ApiModelProperty(value = "提示信息")
    private String msg;

    /** 具体内容. */
    @ApiModelProperty(value = "具体内容")
    private T data;
}
