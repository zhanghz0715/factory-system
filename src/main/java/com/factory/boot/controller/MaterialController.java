package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Material;
import com.factory.boot.service.MaterialService;
import com.factory.boot.service.ProductService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Transactional(rollbackFor = Exception.class)
public class MaterialController extends BaseController {


    @Autowired
    private MaterialService materialService;

    @Autowired
    private ProductService productService;


    @GetMapping("/info")
    public AjaxJson getInfo(String factoryId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Map<String, Object> params = new HashMap<>();
            params.put("factoryId", factoryId);
            List<Map> mapList = materialService.sumStatistics(params);
            List<Map> productList = productService.sumStatistics(params);
            Map<String, Object> resultMap = new HashMap<>();
            double material = 0;
            double waste = 0;
            double product = 0;
            if (!ObjectUtils.isEmpty(mapList) && mapList.size() > 0) {
                for (Map map : mapList) {
                    if(!ObjectUtils.isEmpty(map)){
                        Integer type = (Integer) map.get("type");
                        if (type.equals(Material.TYPE_ONE)) {
                            material = Double.parseDouble(String.format("%.2f", map.get("weight")));
                        }
                        if (type.equals(Material.TYPE_TWO)) {
                            waste = Double.parseDouble(String.format("%.2f", map.get("weight")));
                        }
                    }


                }
            }
            if (!ObjectUtils.isEmpty(productList) && productList.size() > 0) {
                if(!ObjectUtils.isEmpty(productList.get(0))){
                    product = Double.parseDouble(String.format("%.2f", productList.get(0).get("weight")));
                }
            }
            resultMap.put("material", material);
            resultMap.put("waste", waste);
            resultMap.put("product", product);
            resultMap.put("surplus", String.format("%.2f", (material - product - waste)));
            ajaxJson.setData(resultMap);

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/page")
    public AjaxJson getPage(Page<Material> page,String type, String factoryId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            EntityWrapper entityWrapper = new EntityWrapper<Material>();
            if (!ObjectUtils.isEmpty(factoryId)) {
                entityWrapper.eq("factory_id", factoryId);
            }
            if (!ObjectUtils.isEmpty(type)) {
                entityWrapper.eq("type", type);
            }
            page = materialService.selectPage(page, entityWrapper.orderBy("createTime", false));
            ajaxJson.setData(page);

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

