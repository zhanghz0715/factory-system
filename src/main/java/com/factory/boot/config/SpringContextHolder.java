package com.factory.boot.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBean(clazz);
	}

	/**
	 * 按类型获得所有实现类
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		checkApplicationContext();
		return applicationContext.getBeansOfType(clazz);
	}

	/**
	 * 按类型获得一个实现类，没有时返回null
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T getBeanOneOfType(Class<T> clazz) {
		checkApplicationContext();
		Collection<T> results = applicationContext.getBeansOfType(clazz).values();
		return results.size() > 0 ? results.iterator().next() : null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}

}
