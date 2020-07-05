package com.factory.boot.service;

import com.factory.boot.model.SaleType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-06-30
 */
public interface SaleTypeService extends IService<SaleType> {

    List<SaleType> findList(Map<String,Object> params);

}
