package com.factory.boot.service.impl;

import com.factory.boot.model.Material;
import com.factory.boot.dao.MaterialDao;
import com.factory.boot.service.MaterialService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-05-27
 */
@Service
public class MaterialImpl extends ServiceImpl<MaterialDao, Material> implements MaterialService {

    @Override
    public List<Map> sumStatistics(Map<String, Object> params) {
        return baseMapper.sumStatistics(params);
    }
}
