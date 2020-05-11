package com.factory.boot.config;

public class ExceptionUtil {

	public static RuntimeException convertExceptionToUnchecked(Exception e) {
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	/**
	 *
	 * <P>
	 * 获取错误信息
	 * </p>
	 *
	 * @author 闫枫
	 * @date Jan 11, 2017
	 * @param e         错误信息
	 * @param className 出错的类名
	 * @return String
	 */
	public static String getExceptionAllinformation(Exception e, String className) {
		StringBuffer sOut = new StringBuffer();
		sOut.append("异常名称：").append(e.getClass().getName()).append("\r\n异常原因：").append(e.getMessage())
				.append("\r\n出错位置：\r\n").append(className);
		StackTraceElement[] trace = e.getStackTrace();
		for (StackTraceElement s : trace) {
			sOut.append("\tat ").append(s).append("\r\n");
		}
		return sOut.toString();
	}
}
