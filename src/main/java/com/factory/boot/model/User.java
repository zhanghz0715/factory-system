package com.factory.boot.model;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.factory.boot.config.BaseEntity;

import com.factory.boot.util.ObjectUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
/**
 * @data 生成getset方法
 * @NoArgsConstructor 生成无参的构造方法
 * @Accessors 是否支持链式调用
 * @EqualsAndHashCode 是否生成hashcode,equals方法
 */
@TableName("user")
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String money;
	private String name;
	private String remark;
	private String phone;
	private String type;

	/**
	 * 系统用户
	 */
	public static final String TYPE_ONE = "1";
	/**
	 * 主办方
	 */
	public static final String TYPE_TWO = "2";

	@TableField(exist = false)
	private List<Object> logoimg;

	public void setLogoimg(List<Object> logoimg) {
		if(ObjectUtils.isEmpty(logoimg)){
			logoimg = new ArrayList<>();
		}
		this.logoimg = logoimg;
	}
}
