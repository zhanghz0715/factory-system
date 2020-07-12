
package com.factory.boot.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.factory.boot.model.Product;
import com.factory.boot.model.Stock;
import com.factory.boot.service.ProductService;
import com.factory.boot.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 跟踪器定时任务
 */
@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class SchedulingUtil {

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;


    @Scheduled(cron = "0 0 0 * * ? ")
    public void placeholder() {
        List<Product> productList = productService.selectList(new EntityWrapper<Product>().eq("is_statistics", 0).isNotNull("total_weight").isNotNull("count"));
        if (!ObjectUtils.isEmpty(productList) && productList.size() > 0) {
            Map<String, List<Product>> prorductMap = productList.stream().collect(Collectors.groupingBy(product -> product.getFactoryId()));
            for (String factoryId : prorductMap.keySet()) {
                List<Stock> stockList = stockService.selectList(new EntityWrapper<Stock>().eq("factory_id", factoryId));
                Map<String, Stock> stockMap = new HashMap<>();
                if (stockList.size() > 0) {
                    stockList.forEach(stock -> {
                        stockMap.put(stock.getTypeId(), stock);
                    });
                }
                List<Product> productList1 = prorductMap.get(factoryId);
                if (productList1.size() > 0) {
                    Map<String, List<Product>> typeMap = productList1.stream().collect(Collectors.groupingBy(product -> product.getTypeId()));
                    Integer count = 0;
                    Double weight = 0d;
                    for (String typeId : typeMap.keySet()) {
                        List<Product> productList2 = typeMap.get(typeId);
                        for (Product product : productList2) {
                            count += product.getCount();
                            weight += product.getWeight();
                        }
                        DecimalFormat df = new DecimalFormat(".00");
                        Stock stock = stockMap.get(typeId);
                        if (ObjectUtils.isEmpty(stock)) {
                            Stock newStock = new Stock();
                            newStock.setFactoryId(factoryId);
                            newStock.setCount(count);
                            newStock.setWeight(Double.parseDouble(df.format(weight)));
                            newStock.setTypeId(typeId);
                            stockService.insert(newStock);
                        } else {
                            stock.setCount(stock.getCount() + count);
                            stock.setWeight(Double.parseDouble(df.format(weight + stock.getWeight())));
                            stock.setUpdateTime(new Date());
                            stockService.updateById(stock);
                        }
                    }

                }
            }
        }


    }
}