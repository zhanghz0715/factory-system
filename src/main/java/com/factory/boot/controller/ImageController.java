package com.factory.boot.controller;


import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Image;
import com.factory.boot.service.ImageService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/image")
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ImageController extends BaseController {


    @Autowired
    private ImageService imageService;


    @PostMapping("/save")
    public AjaxJson save(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        AjaxJson ajaxJson = new AjaxJson();
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file");
        try {
            if (ObjectUtils.isEmpty(multipartFile)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            byte[] bytes = multipartFile.getBytes();
            Image image = new Image();
            image.setImage(bytes);
            imageService.insert(image);
            ajaxJson.setData(image.getId());
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;

    }


}

