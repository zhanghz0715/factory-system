package com.factory.boot.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.model.Mould;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.session.RowBounds;

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
public interface MouldService extends IService<Mould> {


    Page<Map> findPage(Page<Map> page, Map<String,Object> params);


}
