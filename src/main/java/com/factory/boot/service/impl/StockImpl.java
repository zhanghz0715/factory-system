package com.factory.boot.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.model.Stock;
import com.factory.boot.dao.StockDao;
import com.factory.boot.service.StockService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-05-31
 */
@Service
public class StockImpl extends ServiceImpl<StockDao, Stock> implements StockService {

    @Override
    public Page<Map> getPage(Page<Map> page, Map<String, Object> params) {
        page.setRecords(baseMapper.findPage(page, params));
        return page;
    }
}
