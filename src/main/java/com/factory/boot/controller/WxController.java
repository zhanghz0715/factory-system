package com.factory.boot.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.util.ObjectUtils;
import com.factory.boot.util.yunxin163.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/wx")
@RestController
@Slf4j
public class WxController extends BaseController {


    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;



    @PostMapping("/user/login")
    public AjaxJson wxLogin(String code){
        AjaxJson ajaxJson = new AjaxJson();
        try {
           if(ObjectUtils.isEmpty(code)){
               ajaxJson.setSuccess(false);
               ajaxJson.setMsg("缺少参数，请检查参数是否正确！");
               return ajaxJson;
           }
            HttpClientUtil
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("服务器错误，请稍后重试");
        }
        return ajaxJson;


    }











}
