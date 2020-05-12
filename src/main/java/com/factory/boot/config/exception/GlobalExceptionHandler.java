package com.factory.boot.config.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.factory.boot.config.AjaxJson;

/**
 * @author 刘旭
 * @date 2018年12月6日 下午3:34:09
 * @name GlobalExceptionHandler.java 全局异常捕捉
 */
//@ControllerAdvice --停用
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public AjaxJson exceptionHandler() {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setCode(AjaxJson.ERROR_CODE);
		ajaxJson.setMsg("系统出错啦! 请稍后再试");
		return ajaxJson;
	}
}
