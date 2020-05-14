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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public AjaxJson getPage(Page<Product> page,String productDate,String typeId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            EntityWrapper entityWrapper = new EntityWrapper<Product>();
            if(!ObjectUtils.isEmpty(productDate)){
                entityWrapper.eq("productDate",productDate);
            }
            if(!ObjectUtils.isEmpty(typeId)){
                entityWrapper.eq("type_id",typeId);
            }
            page = productService.selectPage(page, entityWrapper.orderBy("createTime", false));
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


}

