/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-30 17:16:03
 * @LastEditTime: 2019-09-02 15:58:25
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.factory.boot.config.AjaxJson;
import com.factory.boot.config.BaseController;
import com.factory.boot.model.User;
import com.factory.boot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SystemController extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/logout")
	public String logout() {
		this.getRequest().getSession().removeAttribute("login_user");
		return "login";
	}


	@PostMapping("/doLogin")
	@ResponseBody
	public AjaxJson doLogin(String form_username, String form_password) {
		AjaxJson ajaxJson = new AjaxJson();
		User user = userService.selectOne(new EntityWrapper<User>().eq("username", form_username).eq("password",
				DigestUtils.md5DigestAsHex(form_password.getBytes())));

		if (null == user || StringUtils.isBlank(user.getId())) {
			ajaxJson.setCode(AjaxJson.ERROR_CODE);
			ajaxJson.setMsg("用户名或密码错误");
		}

		if (null == user || StringUtils.isBlank(user.getId())) {
			ajaxJson.setCode(AjaxJson.ERROR_CODE);
			ajaxJson.setMsg("用户名或密码错误");
		} else {
			this.getRequest().getSession().setAttribute("login_user", user);
		}
		return ajaxJson;
	}

}
