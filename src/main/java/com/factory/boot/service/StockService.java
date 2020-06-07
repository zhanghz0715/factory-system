package com.factory.boot.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.model.Stock;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-05-31
 */
public interface StockService extends IService<Stock> {


    Page<Map> getPage(Page<Map> page, Map<String,Object> params);


}
