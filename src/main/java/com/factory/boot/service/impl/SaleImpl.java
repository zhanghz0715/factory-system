package com.factory.boot.service.impl;

import com.factory.boot.model.Sale;
import com.factory.boot.dao.SaleDao;
import com.factory.boot.service.SaleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-05-30
 */
@Service
public class SaleImpl extends ServiceImpl<SaleDao, Sale> implements SaleService {

}
