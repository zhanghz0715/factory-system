package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Material;
import com.factory.boot.model.Type;
import com.factory.boot.service.MaterialService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/material")
@Slf4j
public class MaterialController extends BaseController {


    @Autowired
    private MaterialService materialService;


    @GetMapping("/info")
    public AjaxJson getInfo(String factoryId){
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }




    @PostMapping("/save")
    public AjaxJson save(Material material) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(material) || ObjectUtils.isEmpty(material.getFactoryId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            materialService.insert(material);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/update")
    public AjaxJson update(Material material) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(material) || ObjectUtils.isEmpty(material.getId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            material.setUpdateTime(new Date());
            materialService.updateById(material);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/delete")
    public AjaxJson delete(String materialId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(materialId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            materialService.deleteById(materialId);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


}

