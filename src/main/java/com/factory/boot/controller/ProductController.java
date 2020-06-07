package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Image;
import com.factory.boot.model.Mould;
import com.factory.boot.model.Product;
import com.factory.boot.model.Type;
import com.factory.boot.service.ImageService;
import com.factory.boot.service.MouldService;
import com.factory.boot.service.ProductService;
import com.factory.boot.service.TypeService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    @Autowired
    private ImageService imageService;


    @PostMapping("/page")
    public AjaxJson getPage(Page<Product> page, String productDate, String typeId,String mouldId,String factoryId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            EntityWrapper entityWrapper = new EntityWrapper<Product>();
            if (!ObjectUtils.isEmpty(productDate)) {
                entityWrapper.eq("product_date", productDate);
            }
            if (!ObjectUtils.isEmpty(typeId)) {
                entityWrapper.eq("type_id", typeId);
            }
            if (!ObjectUtils.isEmpty(factoryId)) {
                entityWrapper.eq("factory_id", factoryId);
            }
            if (!ObjectUtils.isEmpty(mouldId)) {
                entityWrapper.eq("mould_id", mouldId);
            }
            page = productService.selectPage(page, entityWrapper.orderBy("product_date", false));
            List<String> typeIdList = new ArrayList<>();
            List<String> mouldIdList = new ArrayList<>();
            List<String> imageIdList = new ArrayList<>();
            if (!ObjectUtils.isEmpty(page) && !ObjectUtils.isEmpty(page.getRecords())) {
                for (Product product1 : page.getRecords()) {
                    if(!typeIdList.contains(product1.getTypeId())){
                        typeIdList.add(product1.getTypeId());
                    }
                    if(!typeIdList.contains(product1.getMouldId())){
                        mouldIdList.add(product1.getMouldId());
                    }
                }
                List<Type> typeList = typeService.selectList(new EntityWrapper<Type>().in("id",typeIdList));
                Map<String,List<Type>> typeMap = typeList.stream().collect(Collectors.groupingBy(type->type.getId()));
                List<Mould> mouldList = mouldService.selectList(new EntityWrapper<Mould>().in("id", mouldIdList));
                for(Mould mould:mouldList){
                    if(!ObjectUtils.isEmpty(mould.getImageId())&&!typeIdList.contains(mould.getImageId())){
                        imageIdList.add(mould.getImageId());
                    }
                }
                Map<String,List<Mould>> mouldMap = mouldList.stream().collect(Collectors.groupingBy(mould->mould.getId()));
                List<Image> imageList = imageService.selectList(new EntityWrapper<Image>().in("id",imageIdList));
                Map<String,List<Image>> imageMap = imageList.stream().collect(Collectors.groupingBy(image->image.getId()));
                for (Product product1 : page.getRecords()) {
                    List<Type> typeList1 = typeMap.get(product1.getTypeId());
                    if(!ObjectUtils.isEmpty(typeList1)&&typeList1.size()>0){
                        product1.setTypeName(typeList1.get(0).getName());
                    }

                    List<Mould> mouldList1 = mouldMap.get(product1.getMouldId());
                    if(!ObjectUtils.isEmpty(mouldList1)&&mouldList1.size()>0){
                        product1.setMouldName(mouldList1.get(0).getName());
                        if(!ObjectUtils.isEmpty(mouldList1.get(0).getImageId())){
                            List<Image> imageList1 = imageMap.get(mouldList1.get(0).getImageId());
                            if(!ObjectUtils.isEmpty(imageList1)){
                                String base64String = Base64.encodeBase64String(imageList1.get(0).getImage());
                                product1.setImage("data:image/png;base64,"+base64String);
                            }
                        }


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
            if (ObjectUtils.isEmpty(list)||ObjectUtils.isEmpty(product)) {
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

