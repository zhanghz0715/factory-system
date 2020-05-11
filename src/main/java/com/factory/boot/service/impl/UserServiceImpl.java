package com.factory.boot.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.factory.boot.dao.UserDao;
import com.factory.boot.model.User;
import com.factory.boot.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
