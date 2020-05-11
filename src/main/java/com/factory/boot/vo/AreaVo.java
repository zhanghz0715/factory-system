/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-31 09:56:05
 * @LastEditTime: 2019-09-04 16:47:36
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot.vo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 */
@Data
@Accessors(chain = true)
public class AreaVo {

    private String id;

    private String content;
    /**
     * 区域等级
     */
    private String grade;
    /**
     * 电话区号
     */
    private String code;
    
   private List<AreaVo> areaArray;
}
