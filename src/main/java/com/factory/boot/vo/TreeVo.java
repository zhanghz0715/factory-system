/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-31 09:56:05
 * @LastEditTime: 2019-09-04 16:47:36
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  用户树实体类
 * </p>
 *
 */
@Data
@Accessors(chain = true)
public class TreeVo {

    private String title;

    private String id;

    private String parentId;

    private String level;

    private boolean last;

    private boolean spread;

    private Map  checkArr;

    private List<TreeVo> children;

}
