package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Stock;
import com.factory.boot.model.Type;
import com.factory.boot.model.User;
import com.factory.boot.service.ProductService;
import com.factory.boot.service.StockService;
import com.factory.boot.service.TypeService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @since 2020-05-14
 */
@RestController
@RequestMapping("/type")
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TypeController extends BaseController {


    @Autowired
    private TypeService typeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;


    @PostMapping("/page")
    public AjaxJson getPage(Page<Type> page, String factoryId,String name) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            EntityWrapper entityWrapper = new EntityWrapper<Type>();
            if (!ObjectUtils.isEmpty(factoryId)) {
                entityWrapper.eq("factory_id", factoryId);
            }
            if (!ObjectUtils.isEmpty(name)) {
                entityWrapper.like("name", name);
            }
            page = typeService.selectPage(page, entityWrapper.orderBy("createTime", false));
            List<Map> mapList = productService.getAverageWeight();
            Map<String, Object> typeMap = new HashMap<>();
            if (!ObjectUtils.isEmpty(mapList) && mapList.size() > 0) {
                for (Map map : mapList) {
                    String typeId = (String) map.get("typeId");
                    typeMap.put(typeId, map.get("averageWeight"));
                }

            }
            List<Stock> stockList = stockService.selectList(new EntityWrapper<Stock>());
            Map<String, Integer> stockMap = new HashMap<>();
            stockList.forEach(stock -> {
                stockMap.put(stock.getTypeId(),stock.getCount());
            });
            if(page.getRecords().size()>0){
                for (Type type : page.getRecords()) {
                    if (!ObjectUtils.isEmpty(typeMap.get(type.getId()))) {
                        Double averageWeight = (Double) typeMap.get(type.getId());
                        type.setAverageWeight(Double.parseDouble(String.format("%.2f", type.getLength() * averageWeight)));
                    }
                    if (!ObjectUtils.isEmpty(stockMap.get(type.getId()))) {
                        type.setStock(stockMap.get(type.getId()));
                    }else{
                        type.setStock(0);
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
    public AjaxJson save(Type type) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(type) || ObjectUtils.isEmpty(type.getFactoryId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            List<Type> typeList = typeService.selectList(new EntityWrapper<Type>().eq("factory_id",type.getFactoryId()).eq("name",type.getName()));
            if(typeList.size()>0){
                return new AjaxJson("该产品已经存在");
            }
            typeService.insert(type);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/update")
    public AjaxJson update(Type type) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(type) || ObjectUtils.isEmpty(type.getId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Type type1 = typeService.selectById(type.getId());
            List<Type> typeList = typeService.selectList(new EntityWrapper<Type>().eq("factory_id",type1.getFactoryId()).eq("name",type.getName()));
            if(!type1.getName().equals(type.getName())&&typeList.size()>0){
                return new AjaxJson("该产品已经存在");
            }
            type.setUpdateTime(new Date());
            typeService.updateById(type);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/delete")
    public AjaxJson delete(String typeId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(typeId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            typeService.deleteById(typeId);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    /**
     * 根据产品类型列表
     *
     * @return
     */
    @PostMapping("/list")
    public AjaxJson wxLogin() {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            List<Type> typeList = typeService.selectList(new EntityWrapper<Type>().orderBy("createTime", false));
            List<Map> mapList = productService.getAverageWeight();
            Map<String, Object> typeMap = new HashMap<>();
            if (!ObjectUtils.isEmpty(mapList) && mapList.size() > 0) {
                for (Map map : mapList) {
                    String typeId = (String) map.get("typeId");
                    typeMap.put(typeId, map.get("averageWeight"));
                }

            }
            for (Type type : typeList) {
                if (!ObjectUtils.isEmpty(typeMap.get(type.getId()))) {
                    Double averageWeight = (Double) typeMap.get(type.getId());
                    type.setAverageWeight(Double.parseDouble(String.format("%.2f", type.getLength() * averageWeight)));
                }
            }
            ajaxJson.setData(typeList);
            return ajaxJson;

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
    }

}

