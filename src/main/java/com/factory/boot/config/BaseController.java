package com.factory.boot.config;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.util.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 所有Controller的基类
 * 
 * @author tianyl
 * 
 */
public class BaseController {

	public BaseController() {

	}

	/**
	 * 在request中添加pageId参数
	 * 
	 * @return
	 */
	@ModelAttribute("pageId")
	public String populatePageId() {
		return "page" + UUID.randomUUID();
	}

	/**
	 * 获取request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return SpringMVCUtil.getRequest();
	}

	/**
	 * 获取response对象
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return SpringMVCUtil.getResponse();
	}

	public void print(String result) {
		render(result);
	}

	public void render(String result) {
		SpringMVCUtil.render(result, "text/plain");
	}

	public void renderText(String result) {
		SpringMVCUtil.render(result, "text/plain");
	}

	public void renderJson(String result) {
		SpringMVCUtil.render(result, "application/json");
	}

	public void renderXml(String result) {
		SpringMVCUtil.render(result, "text/xml");
	}

	public void renderHtml(String result) {
		SpringMVCUtil.render(result, "text/html");
	}

	public void render(String result, String contentType) {
		SpringMVCUtil.render(result, contentType);
	}

	public String redirectTo(String url) {
		return "redirect:" + url;
	}

	public String forwardTo(String url) {
		return "forward:" + url;
	}

	@InitBinder
	// 此方法用于日期的转换，如果未加，当页面日期格式转换错误，将报400错误，实际是因为此方法
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 在request中添加值
	 * 
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}


	public String getLink(HttpServletRequest request){
		String result = "";
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		String contextPath = request.getContextPath();
		int port = request.getServerPort();
		if(StringUtils.isNotBlank(scheme)){
			if(StringUtils.isNotBlank(serverName)){
				result += scheme.toLowerCase()+"://"+serverName;
			}
			if(port!=0){
				result += ":"+port;
			}
			if(StringUtils.isNotBlank(contextPath)){
				result += contextPath;
			}
			return result;
		}

		return "";
	}

	public int getAgeByBirth(Date birthDay) {
		int age = 0;
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);  //当前年份
		int monthNow = cal.get(Calendar.MONTH);  //当前月份
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		age = yearNow - yearBirth;   //计算整岁数
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
			} else {
				age--;//当前月份在生日之前，年龄减一
			}
		}
		return age;
	}

	public  <T> T setNullValue(T source){
		try{
			Field[] fields = source.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getGenericType().toString().equals(
						"class java.lang.String")) {
					field.setAccessible(true);
					Object obj = field.get(source);
					if (obj != null && obj.equals("")) {
						field.set(source, null);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return source;
	}

	public static boolean isEmpty(@Nullable Object str) {
		return str == null || "".equals(str);
	}
}
