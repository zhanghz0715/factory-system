package com.factory.boot.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.factory.boot.config.BaseEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sale_type")
public class SaleType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("sale_id")
    private String saleId;
    @TableField("type_id")
    private String typeId;
    private Double weight;
    private Integer count;
    private Double price;
    @TableField("total_price")
    private Double totalPrice;


}
