package com.factory.boot.model;

import java.util.Date;
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
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_material")
public class Material extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Double weight;
    private Double price;
    @TableField("total_price")
    private Double totalPrice;
    @TableField("factory_id")
    private String factoryId;

    private Integer type;

    /**
     * 原料
     */
    public static final Integer TYPE_ONE=1;

    /**
     * 废料
     */
    public static final Integer TYPE_TWO=2;


}
