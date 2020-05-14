package com.factory.boot.service.impl;

import com.factory.boot.model.Type;
import com.factory.boot.dao.TypeDao;
import com.factory.boot.service.TypeService;
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
public class TypeImpl extends ServiceImpl<TypeDao, Type> implements TypeService {

}
