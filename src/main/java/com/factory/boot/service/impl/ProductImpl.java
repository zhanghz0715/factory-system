package com.factory.boot.service.impl;

import com.factory.boot.model.Product;
import com.factory.boot.dao.ProductDao;
import com.factory.boot.service.ProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-05-14
 */
@Service
public class ProductImpl extends ServiceImpl<ProductDao, Product> implements ProductService {

}
