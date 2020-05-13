package com.factory.boot.service.impl;

import com.factory.boot.model.Menu;
import com.factory.boot.dao.MenuDao;
import com.factory.boot.service.MenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-05-13
 */
@Service
public class MenuImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

}
