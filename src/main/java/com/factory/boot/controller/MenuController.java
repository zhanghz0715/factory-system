package com.factory.boot.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.Menu;
import com.factory.boot.model.User;
import com.factory.boot.service.MenuService;
import com.factory.boot.service.UserService;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-05-13
 */
@RequestMapping("/menu")
@RestController
@Slf4j
public class MenuController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;


    /**
     * 根据用户获取菜单列表
     *
     * @param userId
     * @return
     */
    @PostMapping("/list")
    public AjaxJson wxLogin(String userId) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            User user = userService.selectById(userId);
            if (ObjectUtils.isEmpty(user)) {
                return new AjaxJson("参数错误，请检查参数是否正确！");
            }
            List<Menu> menuList = menuService.selectList(new EntityWrapper<Menu>().eq("type", user.getType()));
            ajaxJson.setData(menuList);
            return ajaxJson;

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
    }


}

