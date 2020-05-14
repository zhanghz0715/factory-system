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
@TableName("t_user_mould")
public class UserMould extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 生产日期
     */
    private Date productDate;
    /**
     * 模具Id
     */
    private String mouldId;
    /**
     * 班组
     */
    private String shift;
    /**
     * 支重
     */
    private Double weight;
    /**
     * 棒长
     */
    private Double length;
    /**
     * 生产棒数
     */
    private Integer count;
    /**
     * 生产重量
     */
    private Double totalWeight;
    /**
     * 氧化
     */
    private String oxidation;
    /**
     * 上机情况
     */
    private String situation;
    /**
     * 用户id
     */
    private String userId;


}
