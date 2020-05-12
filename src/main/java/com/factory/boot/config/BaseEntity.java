package com.factory.boot.config;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {

	/**
	 * 删除标记（0：正常；1：删除；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7284368225998860828L;

	@TableId(value = "id")
	private String id;

	/**
	 * 创建时间
	 */
	@TableField(value = "createTime", fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "updateTime", fill = FieldFill.INSERT)
	private Date updateTime;

	/**
	 * 删除标记
	 */
	@TableField(value = "isDelete", fill = FieldFill.INSERT)
	@TableLogic
	private String isDelete;

}
