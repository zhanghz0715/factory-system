package com.factory.boot.service.impl;

import com.factory.boot.model.SaleType;
import com.factory.boot.dao.SaleTypeDao;
import com.factory.boot.service.SaleTypeService;
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
 * @since 2020-06-30
 */
@Service
public class SaleTypeImpl extends ServiceImpl<SaleTypeDao, SaleType> implements SaleTypeService {

    @Override
    public List<SaleType> findList(Map<String, Object> params) {
        return baseMapper.findList(params);
    }
}
