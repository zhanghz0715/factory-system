package com.factory.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.config.ExceptionUtil;
import com.factory.boot.model.User;
import com.factory.boot.service.UserService;
import com.factory.boot.util.CheckSumBuilder;
import com.factory.boot.util.HttpRequest;
import com.factory.boot.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wx")
@RestController
@Slf4j
public class WxController extends BaseController {


    @Autowired
    private UserService userService;


    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    private String wxUrl = "https://api.weixin.qq.com/sns/jscode2session";


    /**
     * 微信登录
     *
     * @param js_code
     * @return
     */
    @PostMapping("/user/login")
    public AjaxJson wxLogin(String js_code) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            if (ObjectUtils.isEmpty(js_code)) {
                return new AjaxJson("缺少参数，请检查参数是否正确！");
            }
            String param = "appid=" + appId + "&secret=" + appSecret + "&js_code=" + js_code + "&grant_type=authorization_code";
            String result = HttpRequest.sendGet(wxUrl, param);
            System.out.println(result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String openId = jsonObject.getString("openid");
            String sessionKey = jsonObject.getString("session_key");
            User user = userService.selectOne(new EntityWrapper<User>().eq("openId", openId));
            if (ObjectUtils.isEmpty(user)) {
                return new AjaxJson("请先授权！");
            } else {
                ajaxJson.setData(user);
                ajaxJson.setToken(sessionKey);
                return ajaxJson;
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            return new AjaxJson("服务器错误，请稍后重试");
        }
    }


    /**
     * 微信登录
     *
     * @param js_code
     * @return
     */
    @PostMapping("/user/update")
    public AjaxJson updateUser(String username, String password, String js_code) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            User user = userService.selectOne(new EntityWrapper<User>().eq("username", username));
            String param = "appid=" + appId + "&secret=" + appSecret + "&js_code=" + js_code + "&grant_type=authorization_code";
            String result = HttpRequest.sendGet(wxUrl, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String openId = jsonObject.getString("openid");
            //默认为工人
            if(ObjectUtils.isEmpty(user)){
                return new AjaxJson("账号不存在，授权失败！");
            }else{
                if(!CheckSumBuilder.getMD5(password).equals(user.getPassword())){
                    return new AjaxJson("密码错误，授权失败！");
                }
                user.setOpenId(openId);
                userService.updateById(user);
                ajaxJson.setData(user);
            }

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionAllinformation(e, getClass().getName()));
            ajaxJson.setCode(AjaxJson.ERROR_CODE);
            return new AjaxJson("服务器错误，请稍后重试");
        }
        return ajaxJson;
    }


}
