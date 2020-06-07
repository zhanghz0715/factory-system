package com.factory.boot.service;

import com.factory.boot.model.Material;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-05-27
 */
public interface MaterialService extends IService<Material> {


    List<Map> sumStatistics(Map<String,Object> params);

}
