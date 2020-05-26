package com.factory.boot.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.factory.boot.config.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-05-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_image")
public class Image extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private byte[] image;



}
