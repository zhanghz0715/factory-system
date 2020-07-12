package com.factory.boot.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.service.StockService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2020-05-31
 */
@RestController
@RequestMapping("/stock")
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StockController extends BaseController {


    @Autowired
    private StockService stockService;


    @PostMapping("/page")
    public AjaxJson getPage(Page<Map> page, String name,String factoryId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Map<String,Object> map = new HashMap<>();
            if (!ObjectUtils.isEmpty(factoryId)) {
                map.put("factoryId", factoryId);
            }
            if (!ObjectUtils.isEmpty(name)) {
                map.put("name", name);
            }
            page = stockService.getPage(page,map);
            ajaxJson.setData(page);

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }





}

