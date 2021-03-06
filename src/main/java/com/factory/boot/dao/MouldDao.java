package com.factory.boot.dao;

import com.factory.boot.model.Mould;
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
 * @since 2020-05-14
 */
public interface MouldDao extends SuperMapper<Mould> {


    List<Map> findPage(RowBounds rowBounds, Map<String,Object> params);


}
