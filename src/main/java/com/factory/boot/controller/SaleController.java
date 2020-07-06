package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.*;
import com.factory.boot.service.SaleService;
import com.factory.boot.service.SaleTypeService;
import com.factory.boot.service.StockService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-05-30
 */
@RestController
@RequestMapping("/sale")
@Slf4j
public class SaleController extends BaseController {


    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleTypeService saleTypeService;

    @Autowired
    private StockService stockService;


    @PostMapping("/page")
    public AjaxJson getPage(Page<Sale> page, String saleDate, String saleNumber, Integer isArrears, String factoryId) {
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
    public AjaxJson save(Sale sale, String list) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(sale) || ObjectUtils.isEmpty(sale.getFactoryId()) || ObjectUtils.isEmpty(list)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
            String nowDate = format.format(date);
            Integer count = saleService.selectCount(new EntityWrapper<Sale>().eq("factory_id", sale.getFactoryId()).le("createTime", nowDate + " 23:59:59").ge("createTime", nowDate + " 00:00:00"));
            if (count == 0) {
                sale.setSaleNumber("S" + format1.format(date) + "001");
            } else {
                sale.setSaleNumber("S" + format1.format(date) + getIndex(count + 1));
            }
            if (ObjectUtils.isEmpty(sale.getCollectMoney())) {
                sale.setIsArrears(1);
            } else {
                if (sale.getCollectMoney() >= sale.getTotalPrice()) {
                    sale.setIsArrears(0);
                }
            }
            saleService.insert(sale);
            List<String> typeIdlist = new ArrayList<>();
            Map<String, Integer> typeMap = new HashMap<>();
            Map<String, Double> weightMap = new HashMap<>();
            JSONArray jsonArray = new JSONArray(list);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray pages = jsonObject.optJSONArray("pages");
                SaleType saleType = new SaleType();
                saleType.setSaleId(sale.getId());
                String typeId = "";
                Integer saleCount = 0;
                Double saleWeight = 0d;
                for (int j = 0; j < pages.length(); j++) {
                    JSONObject page = pages.getJSONObject(j);
                    switch (page.optString("name")) {
                        case "产品":
                            typeId = page.optString("id");
                            typeIdlist.add(typeId);
                            saleType.setTypeId(typeId);
                            break;
                        case "支数":
                            saleCount = page.optInt("value");
                            saleType.setCount(saleCount);
                            break;
                        case "总重":
                            saleWeight = page.optDouble("value");
                            saleType.setTotalWeight(saleWeight);
                            break;
                        case "实际支重":
                            saleType.setWeight(page.optDouble("value"));
                            break;
                        case "单价":
                            saleType.setPrice(page.optDouble("value"));
                            break;
                        case "总价":
                            saleType.setTotalPrice(page.optDouble("value"));
                            break;
                        case "库存":
                            saleType.setStock(page.optDouble("value"));
                            break;
                        default:
                            break;
                    }
                }
                typeMap.put(typeId, saleCount);
                weightMap.put(typeId, saleWeight);
                saleTypeService.insert(saleType);
            }
            List<Stock> stockList = stockService.selectList(new EntityWrapper<Stock>().eq("factory_id", sale.getFactoryId()).in("type_id", typeIdlist));
            Map<String, Stock> stockMap = new HashMap<>();
            stockList.forEach(stock -> {
                stockMap.put(stock.getTypeId(), stock);
            });
            typeIdlist.forEach(s -> {
                Stock stock = stockMap.get(s);
                if (ObjectUtils.isEmpty(stock)) {
                    Stock stock1 = new Stock();
                    stock1.setFactoryId(sale.getFactoryId());
                    stock1.setTypeId(s);
                    stock1.setCount(-typeMap.get(s));
                    stock1.setWeight(-weightMap.get(s));
                    stockService.insert(stock);
                } else {
                    stock.setCount(stock.getCount() - typeMap.get(s));
                    stock.setWeight(Double.parseDouble(String.format("%.2f", stock.getWeight() - weightMap.get(s))));
                    stockService.updateById(stock);
                }

            });
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }

    private String getIndex(Integer count) {
        String result = String.valueOf(count);
        Integer size = result.length();
        for (int i = 0; i < 3 - size; i++) {
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
            if (ObjectUtils.isEmpty(sale.getCollectMoney())) {
                sale.setIsArrears(1);
            } else {
                if (sale.getCollectMoney() > sale.getTotalPrice()) {
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


    @PostMapping("/getDetail")
    public AjaxJson getDetail(String id) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(id)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            Sale sale = saleService.selectById(id);
            if (ObjectUtils.isEmpty(sale)) {
                return new AjaxJson("获取不到信息，请检查参数是否正确！");
            }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("sale", sale);
            Map<String, Object> map = new HashMap<>();
            map.put("saleId", sale.getId());
            List<SaleType> saleTypeList = saleTypeService.findList(map);
            List<Map> list = new ArrayList<>();
            for (int i = 0; i < saleTypeList.size(); i++) {
                SaleType saleType = saleTypeList.get(i);
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("id", String.valueOf(i));
                tempMap.put("open", false);
                tempMap.put("saleTypeId", saleType.getId());
                tempMap.put("name", saleType.getTypeName());
                List<Map> pages = new ArrayList<>();
                Map<String, Object> typeMap = new HashMap<>();
                typeMap.put("id", saleType.getTypeId());
                typeMap.put("name", "产品");
                typeMap.put("value", saleType.getTypeName());
                pages.add(typeMap);
                Map<String, Object> countMap = new HashMap<>();
                countMap.put("id", "");
                countMap.put("name", "支数");
                countMap.put("value", saleType.getCount());
                pages.add(countMap);
                Map<String, Object> totalWeightMap = new HashMap<>();
                totalWeightMap.put("id", "");
                totalWeightMap.put("name", "总重");
                totalWeightMap.put("value", saleType.getTotalWeight());
                pages.add(totalWeightMap);
                Map<String, Object> weightMap = new HashMap<>();
                weightMap.put("id", "");
                weightMap.put("name", "实际支重");
                weightMap.put("value", saleType.getWeight());
                pages.add(weightMap);
                Map<String, Object> stockMap = new HashMap<>();
                stockMap.put("id", "");
                stockMap.put("name", "库存");
                stockMap.put("value", saleType.getStock());
                pages.add(stockMap);
                Map<String, Object> priceMap = new HashMap<>();
                priceMap.put("id", "");
                priceMap.put("name", "单价");
                priceMap.put("value", saleType.getPrice());
                pages.add(priceMap);
                Map<String, Object> totalPriceMap = new HashMap<>();
                totalPriceMap.put("id", "");
                totalPriceMap.put("name", "总价");
                totalPriceMap.put("value", saleType.getTotalPrice());
                pages.add(totalPriceMap);
                tempMap.put("pages", pages);
                list.add(tempMap);
            }
            resultMap.put("list", list);
            ajaxJson.setData(resultMap);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


}

