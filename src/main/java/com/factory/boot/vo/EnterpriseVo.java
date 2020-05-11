/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-31 09:56:05
 * @LastEditTime: 2019-10-02 15:08:25
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 好企推荐VO类
 * </p>
 *
 */
@Data
@Accessors(chain = true)
public class EnterpriseVo {

    private String id;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业规模开始
     */
    private Integer companyUnitStart;
    /**
     * 企业规模结束
     */
    private Integer companyUnitEnd;
    /**
     * 公司性质
     */
    private String companyNature;
    /**
     * @description: 在招职位总数
     */
    private Integer jobNumber;
    /**
     * @description: 在招职位前三个
     */
    private String jobTop3;

    private String head_portrait;

}
