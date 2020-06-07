package com.factory.boot.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.model.Mould;
import com.factory.boot.dao.MouldDao;
import com.factory.boot.service.MouldService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-05-14
 */
@Service
public class MouldImpl extends ServiceImpl<MouldDao, Mould> implements MouldService {

    @Override
    public Page<Map> findPage(Page<Map> page, Map<String, Object> params) {
        page.setRecords(baseMapper.findPage(page,params));
        return page;
    }
}
