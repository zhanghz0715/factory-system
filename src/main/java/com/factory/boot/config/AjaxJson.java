package com.factory.boot.config;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.aspectj.weaver.loadtime.Aj;

/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class AjaxJson {

	public static final Integer ERROR_CODE=0;//失败
	private Integer code = 1;// 是否成功
	private String msg = "操作成功";// 提示信息
	private String token;
	private Object data = null;// 其他信息
	private Map<String, Object> attributes;// 其他参数

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AjaxJson(){

	}

	public  AjaxJson(String msg){
		this.code=ERROR_CODE;
		this.msg=msg;
	}

	/*public String getJsonStr() {
		JSONObject obj = new JSONObject();
		obj.put("success", this.isSuccess());
		obj.put("msg", this.getMsg());
		obj.put("obj", this.obj);
		obj.put("attributes", this.attributes);
		return obj.toJSONString();
	}*/
}
