package com.factory.boot.config;

import com.baomidou.mybatisplus.mapper.BaseMapper;

public interface SuperMapper<T> extends BaseMapper<T> {
	// 这里可以放一些公共的方法
}
