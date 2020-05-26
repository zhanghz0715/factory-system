package com.factory.boot.service.impl;

import com.factory.boot.model.Image;
import com.factory.boot.dao.ImageDao;
import com.factory.boot.service.ImageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-05-23
 */
@Service
public class ImageImpl extends ServiceImpl<ImageDao, Image> implements ImageService {

}
