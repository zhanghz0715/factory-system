package com.factory.boot.dao;

import com.factory.boot.model.SaleType;
import com.factory.boot.config.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-06-30
 */
public interface SaleTypeDao extends SuperMapper<SaleType> {

    List<SaleType> findList(Map<String,Object> params);

}
