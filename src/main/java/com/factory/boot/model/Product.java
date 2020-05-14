package com.factory.boot.model;

import java.util.Date;
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
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_product")
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 生产日期
     */
    private Date productDate;
    /**
     * 柜号
     */
    private String cabinetNumber;
    /**
     * 产品型号ID
     */
    private String typeId;
    /**
     * 模具ID
     */
    private String mouldId;
    /**
     * 长度
     */
    private Double length;
    /**
     * 理论支重
     */
    private Double theoryWeight;
    /**
     * 平均支重
     */
    private Double averageWeight;
    /**
     * 支重
     */
    private Double weight;
    /**
     * 支数
     */
    private Integer count;
    /**
     * 重量
     */
    private Double totalWeight;


}
