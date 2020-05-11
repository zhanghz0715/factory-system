package com.factory.boot.util;

import com.factory.boot.config.SpringMVCUtil;
import com.factory.boot.model.User;

public class UserUtil {
	/**
	 * 获取系统当前登录用户
	 * 
	 * @return
	 */
	public static User getCurryUser() {
		User user = (User) SpringMVCUtil.getRequest().getSession().getAttribute("login_user");
		return user;
	}

	/**
	 * 获取系统当前登录用户Id
	 * 
	 * @return
	 */
	public static String getCurryUserId() {
		User user = getCurryUser();
		if (null == user)
			return null;
		return user.getId();
	}
}
