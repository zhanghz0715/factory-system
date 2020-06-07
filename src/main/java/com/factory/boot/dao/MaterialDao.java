package com.factory.boot.dao;

import com.factory.boot.model.Material;
import com.factory.boot.config.SuperMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-05-27
 */
public interface MaterialDao extends SuperMapper<Material> {


    List<Map> sumStatistics(Map<String,Object> params);


}
