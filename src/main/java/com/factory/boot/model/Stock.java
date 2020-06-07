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
 * @since 2020-05-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_stock")
public class Stock extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("factory_id")
    private String factoryId;
    @TableField("type_id")
    private String typeId;
    private Integer count;
    private Double weight;


}
