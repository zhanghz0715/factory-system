package com.factory.boot.dao;

import com.factory.boot.model.Product;
import com.factory.boot.config.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-05-14
 */
public interface ProductDao extends SuperMapper<Product> {

    /**
     * 获取产品的平均支重
     * @return
     */
    List<Map> getAverageWeight();

}
