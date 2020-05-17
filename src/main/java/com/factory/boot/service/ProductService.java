package com.factory.boot.service;

import com.factory.boot.model.Product;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-05-14
 */
public interface ProductService extends IService<Product> {



    /**
     * 获取产品的平均支重
     * @return
     */
    List<Map> getAverageWeight();





}
