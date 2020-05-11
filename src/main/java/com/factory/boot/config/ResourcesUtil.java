package com.factory.boot.config;

import java.util.ArrayList;
import java.util.List;

public class ResourcesUtil {
	public static List<String> getkeyList() {
		List<String> list = new ArrayList<String>();
		list.add("/login");// 登陆页页面
		list.add("/logout");// 退出登录
		list.add("/doLogin");// 登陆
		list.add("/sys/attachment/showPic");
		list.add("/sys/attachment/showTempPic");
		return list;
	}
}
