package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Sale;
import com.factory.boot.service.SaleService;
import com.factory.boot.service.TypeService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2020-05-30
 */
@RestController
@RequestMapping("/sale")
@Slf4j
public class SaleController extends BaseController{


    @Autowired
    private SaleService saleService;

    @Autowired
    private TypeService typeService;


    @PostMapping("/page")
    public AjaxJson getPage(Page<Sale> page, String saleDate, String saleNumber,Integer isArrears,String factoryId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(factoryId)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            EntityWrapper entityWrapper = new EntityWrapper<Sale>();
            if (!ObjectUtils.isEmpty(factoryId)) {
                entityWrapper.eq("factory_id", factoryId);
            }
            if (!ObjectUtils.isEmpty(saleDate)) {
                entityWrapper.eq("sale_date", saleDate);
            }
            if (!ObjectUtils.isEmpty(saleNumber)) {
                entityWrapper.like("sale_number", saleNumber);
            }
            if (!ObjectUtils.isEmpty(isArrears)) {
                entityWrapper.eq("is_arrears", isArrears);
            }
            page = saleService.selectPage(page, entityWrapper.orderBy("createTime", false));
            ajaxJson.setData(page);

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }




    @PostMapping("/save")
    public AjaxJson save(Sale sale) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(sale) || ObjectUtils.isEmpty(sale.getFactoryId())) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
            String nowDate = format.format(date);
            Integer count = saleService.selectCount(new EntityWrapper<Sale>().eq("factory_id",sale.getFactoryId()).le("createTime",nowDate+" 23:59:59").ge("createTime",nowDate+" 00:00:00"));
            if(count==0){
                sale.setSaleNumber("S"+format1.format(date)+"001");
            }else{
                sale.setSaleNumber("S"+format1.format(date)+getIndex(count+1));
            }
            if(ObjectUtils.isEmpty(sale.getCollectMoney())){
                sale.setIsArrears(1);
            }else{
                if(sale.getCollectMoney()>=sale.getTotalPrice()){
                    sale.setIsArrears(0);
                }
            }
            saleService.insert(sale);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }

    private String getIndex(Integer count){
        String result =String.valueOf(count);
        Integer size = result.length();
        for(int i=0;i<3-size;i++) {
            result = "0" + result;
        }
        return result;
    }


    @PostMapping("/update")
    public AjaxJson update(Sale sale) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(sale)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            if(ObjectUtils.isEmpty(sale.getCollectMoney())){
                sale.setIsArrears(1);
            }else{
                if(sale.getCollectMoney()>sale.getTotalPrice()){
                    sale.setIsArrears(0);
                }
            }
            saleService.updateById(sale);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }





}

