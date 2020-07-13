package com.factory.boot.model;

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
 * @since 2020-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_set")
public class Set extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String value;


}
