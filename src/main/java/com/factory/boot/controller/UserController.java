package com.factory.boot.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.User;
import com.factory.boot.service.UserService;
import com.factory.boot.util.CheckSumBuilder;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @PostMapping("/page")
    public AjaxJson getPage(Page<User> page,String factoryId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            page = userService.selectPage(page, new EntityWrapper<User>().eq("factory_id",factoryId).ne("type",User.TYPE_ONE).orderBy("createTime", false));
            if(!ObjectUtils.isEmpty(page)&&page.getRecords().size()>0){
                for(User user1:page.getRecords()){
                    if(user1.getType().equals(User.TYPE_TWO)){
                        user1.setTypeName("财务");
                    }
                    if(user1.getType().equals(User.TYPE_THREE)){
                        user1.setTypeName("工人");
                    }
                    if(user1.getType().equals(User.TYPE_ONE)){
                        user1.setTypeName("老板");
                    }

                }
            }
            ajaxJson.setData(page);

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/save")
    public AjaxJson save(User user) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(user)||ObjectUtils.isEmpty(user.getFactoryId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            List<User> userList = userService.selectList(new EntityWrapper<User>().eq("factory_id",user.getFactoryId()).eq("username",user.getUsername()));
            if(userList.size()>0){
                return new AjaxJson("该手机号已经存在");
            }
            user.setPassword(CheckSumBuilder.getMD5("123456"));
            userService.insert(user);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/update")
    public AjaxJson update(User user) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(user)||ObjectUtils.isEmpty(user.getId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            User user1 = userService.selectById(user.getId());
            List<User> userList = userService.selectList(new EntityWrapper<User>().eq("factory_id",user1.getFactoryId()).eq("username",user.getUsername()));
            if(!user1.getUsername().equals(user.getUsername())&&userList.size()>0){
                return new AjaxJson("该手机号已经存在");
            }
            user.setUpdateTime(new Date());
            userService.updateById(user);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }

    @PostMapping("/delete")
    public AjaxJson delete(String userId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(userId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            userService.deleteById(userId);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }










}
