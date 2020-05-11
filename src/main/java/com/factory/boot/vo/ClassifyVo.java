/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-31 09:59:03
 * @LastEditTime: 2019-09-02 16:34:35
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot.vo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author
 * @since 2019-08-31
 */
@Data
@Accessors(chain = true)
public class ClassifyVo {

    /**
     */
    private String id;
    /**
     * 分类名称
     */
    private String text;
    /**
     * 分类图标（采用Font Awesome 4.7.0 字体图标）
     */
    private String icon;
    /**
     * 分类名称
     */
    private List<ClassifyVo> nodes;

}
