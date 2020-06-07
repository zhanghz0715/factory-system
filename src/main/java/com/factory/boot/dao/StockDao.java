package com.factory.boot.dao;

import com.factory.boot.model.Stock;
import com.factory.boot.config.SuperMapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-05-31
 */
public interface StockDao extends SuperMapper<Stock> {


    List<Map> findPage(RowBounds rowBounds,Map<String,Object> params);

}
