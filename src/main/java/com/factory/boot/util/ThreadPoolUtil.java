/**
 *
 * <P> ThreadPoolUtil.java -- 线程池Util </p>
 * 
 *
 * @author 闫枫
 */
package com.factory.boot.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * <P>
 * 线程池Util
 * </p>
 *
 * @author 闫枫
 */
public class ThreadPoolUtil {
	private static ExecutorService pool;

	static {
		pool = Executors.newCachedThreadPool();
	}

	/**
	 *
	 * <P>
	 * get线程池
	 * </p>
	 *
	 * @author 闫枫
	 * @return ExecutorService
	 */
	public static ExecutorService getPool() {
		return pool;
	}

}
