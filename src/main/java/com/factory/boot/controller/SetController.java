package com.factory.boot.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.model.Set;
import com.factory.boot.model.User;
import com.factory.boot.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2020-07-13
 */
@RestController
@RequestMapping("/set")
public class SetController extends BaseController {


    @Autowired
    private SetService setService;


    @PostMapping("/getValue")
    public AjaxJson getValue() {
        AjaxJson ajaxJson = new AjaxJson();
        Set set = setService.selectById("1");
        ajaxJson.setData(set.getValue());
        return ajaxJson;

    }

}

