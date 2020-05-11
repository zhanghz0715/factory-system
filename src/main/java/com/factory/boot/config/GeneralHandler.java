/**
 * 
 */
package com.factory.boot.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;

public class GeneralHandler extends MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		Object createTime = getFieldValByName("createTime", metaObject);
		Object isDelete = getFieldValByName("isDelete", metaObject);

		if (createTime == null) {
			setFieldValByName("createTime", new Date(), metaObject);
		}
		if (isDelete == null) {
			setFieldValByName("isDelete", BaseEntity.DEL_FLAG_NORMAL, metaObject);
		}

	}

	@Override
	public void updateFill(MetaObject metaObject) {
	}

}
