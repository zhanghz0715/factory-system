package com.factory.boot.controller;


import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2020-05-14
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController extends BaseController {



    @PostMapping("/page")
    public AjaxJson getPage(Integer pageNo,Integer pageSize,String date,String typeId){
        AjaxJson ajaxJson = new AjaxJson();
        try {

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }












}

