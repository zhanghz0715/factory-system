package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Type;
import com.factory.boot.service.ProductService;
import com.factory.boot.service.TypeService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class TypeController extends BaseController {


    @Autowired
    private TypeService typeService;

    @Autowired
    private ProductService productService;

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

