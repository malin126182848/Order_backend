package com.malin.order_backend.dataobj;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.malin.order_backend.enums.ProductStatusEnum;
import com.malin.order_backend.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class SpecificationInfo {

    @Id
    private String specificationId;

    /** 名字. */
    private String specificationName;

    /** 菜品. */
    private String productId;

    /** 类别. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

}