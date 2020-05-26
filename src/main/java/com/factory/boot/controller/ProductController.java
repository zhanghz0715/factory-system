package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Mould;
import com.factory.boot.model.Product;
import com.factory.boot.model.Type;
import com.factory.boot.service.MouldService;
import com.factory.boot.service.ProductService;
import com.factory.boot.service.TypeService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-05-14
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController extends BaseController {


    @Autowired
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private MouldService mouldService;


    @PostMapping("/page")
    public AjaxJson getPage(Page<Product> page, String productDate, String typeId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            EntityWrapper entityWrapper = new EntityWrapper<Product>();
            if (!ObjectUtils.isEmpty(productDate)) {
                entityWrapper.eq("product_date", productDate);
            }
            if (!ObjectUtils.isEmpty(typeId)) {
                entityWrapper.eq("type_id", typeId);
            }
            page = productService.selectPage(page, entityWrapper.orderBy("product_date", false));
            if (!ObjectUtils.isEmpty(page) && !ObjectUtils.isEmpty(page.getRecords())) {
                for (Product product1 : page.getRecords()) {
                    Type type = typeService.selectById(product1.getTypeId());
                    Mould mould = mouldService.selectById(product1.getMouldId());
                    product1.setTypeName(type.getName());
                    product1.setMouldName(mould.getName());
                }
                ajaxJson.setData(page);
            }

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }

    @PostMapping("/getDetail")
    public AjaxJson getDetail(String id) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(id)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Product product = productService.selectById(id);
            if (ObjectUtils.isEmpty(product)) {
                return new AjaxJson("获取不到信息，请检查参数是否正确！");
            }
            Type type = typeService.selectById(product.getTypeId());
            Mould mould = mouldService.selectById(product.getMouldId());
            product.setTypeName(type.getName());
            product.setMouldName(mould.getName());
            ajaxJson.setData(product);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/update")
    public AjaxJson update(Product product) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(product.getId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Product product1 = productService.selectById(product.getId());
            if (ObjectUtils.isEmpty(product1)) {
                return new AjaxJson("参数错误，请检查参数是否正确！");
            }
            product.setUpdateTime(new Date());
            productService.updateById(product);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


    @PostMapping("/save")
    public AjaxJson save(Product product, String list) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(list)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            JSONArray jsonArray = new JSONArray(list);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray pages = jsonObject.optJSONArray("pages");
                Product product1 = new Product();
                product1.setProductDate(product.getProductDate());
                product1.setMachine(product.getMachine());
                product1.setMonitor(product.getMonitor());
                product1.setOpenMachine(product.getOpenMachine());
                product1.setTopNote(product.getTopNote());
                product1.setPostNote(product.getPostNote());
                for (int j = 0; j < pages.length(); j++) {
                    JSONObject page = pages.getJSONObject(j);
                    switch (page.optString("name")) {
                        case "柜号":
                            product1.setCabinetNumber(page.optString("value"));
                            break;
                        case "产品型号":
                            product1.setTypeId(page.optString("id"));
                            break;
                        case "模号":
                            product1.setMouldId(page.optString("id"));
                            break;
                        case "长度":
                            product1.setLength(page.optDouble("value"));
                            break;
                        case "理论支重":
                            product1.setTheoryWeight(page.optDouble("value"));
                            break;
                        case "平均支重":
                            product1.setAverageWeight(page.optDouble("value"));
                            break;
                        case "支数":
                            product1.setCount(page.optInt("value"));
                            break;
                        default:
                            break;
                    }
                }
                productService.insert(product1);

            }

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


}

