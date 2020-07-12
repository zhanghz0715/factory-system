package com.factory.boot.controller;


import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Product;
import com.factory.boot.model.UserMould;
import com.factory.boot.service.UserMouldService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.factory.boot.config.BaseController;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-05-14
 */
@RestController
@RequestMapping("/userMould")
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserMouldController extends BaseController {

    @Autowired
    private UserMouldService userMouldService;

    @PostMapping("/save")
    public AjaxJson save(UserMould userMould) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            userMouldService.insert(userMould);

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


}

