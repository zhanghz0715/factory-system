package com.factory.boot.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.factory.boot.config.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * <p>
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
    @TableField("product_date")
    private Date productDate;
    /**
     * 机台
     */
    private String machine;
    /**
     * 班长
     */
    private String monitor;
    /**
     * 开机/中断
     */
    @TableField("open_machine")
    private String openMachine;
    /**
     * 前调
     */
    @TableField("top_note")
    private String topNote;
    /**
     * 后调
     */
    @TableField("post_note")
    private String postNote;
    /**
     * 柜号
     */
    @TableField("cabinet_number")
    private String cabinetNumber;
    /**
     * 产品型号ID
     */
    @TableField("type_id")
    private String typeId;
    /**
     * 模具ID
     */
    @TableField("mould_id")
    private String mouldId;
    /**
     * 长度
     */
    private Double length;
    /**
     * 理论支重
     */
    @TableField("theory_weight")
    private Double theoryWeight;
    /**
     * 平均支重
     */
    @TableField("average_weight")
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
    @TableField("total_weight")
    private Double totalWeight;

    /**
     * 产品型号名称
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 模具名称
     */
    @TableField(exist = false)
    private String mouldName;

    /**
     * 模具图片
     */
    @TableField(exist = false)
    private String image;


}
