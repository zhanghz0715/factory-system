package com.factory.boot.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.factory.boot.config.BaseEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-05-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sale")
public class Sale extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 销售日期
     */
    @TableField("sale_date")
    private Date saleDate;

    /**
     * 销售单号
     */
    @TableField("sale_number")
    private String saleNumber;
    @TableField("factory_id")
    private String factoryId;
    @TableField("type_id")
    private String typeId;

    @TableField(exist = false)
    private String typeName;

    private Double weight;
    /**
     * 销售数量
     */
    private Double count;
    /**
     * 单价
     */
    private Double price;
    /**
     * 总价
     */
    @TableField("total_price")
    private Double totalPrice;
    /**
     * 收款
     */
    @TableField("collect_money")
    private Double collectMoney;
    /**
     * 欠款
     */
    private Double arrears;
    /**
     * 是否欠款：1是 0否
     */
    @TableField("is_arrears")
    private Integer isArrears;



}
