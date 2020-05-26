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
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_mould")
public class Mould extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 工厂ID
     */
    @TableField("factory_id")
    private String factoryId;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 产品
     */
    @TableField("type_id")
    private String typeId;

    @TableField(exist = false)
    private String typeName;

    @TableField(exist = false)
    private Double typeLength;

    @TableField(exist = false)
    private Double theoryWeight;

    /**
     * 平均支重
     */
    @TableField(exist = false)
    private Double averageWeight;

    /**
     * 氧化情况
     */
    private String oxidation;

    /**
     * 状态
     */
    private Integer status;

    @TableField(exist = false)
    private String statusName;

    /**
     * 图片
     */
    @TableField("image_id")
    private String imageId;

    @TableField(exist = false)
    private String image;

    /**
     * 正常
     */
    public static final Integer STATUS_ONE=1;

    /**
     * 报废
     */
    public static final Integer STATUS_TWO=2;




}
