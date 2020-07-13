package com.factory.boot.service.impl;

import com.factory.boot.model.Set;
import com.factory.boot.dao.SetDao;
import com.factory.boot.service.SetService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-07-13
 */
@Service
public class SetImpl extends ServiceImpl<SetDao, Set> implements SetService {

}
