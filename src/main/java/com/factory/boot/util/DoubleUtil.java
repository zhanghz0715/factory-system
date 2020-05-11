package com.factory.boot.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yanfeng
 * @date 2018年12月10日 下午4:42:16
 * @name DoubleUtil.java 浮点数计算（加、减、乘、除、比较大小、格式化等）工具类
 */
public class DoubleUtil {

	/** 进制为10进制 */
	private static final int DEF_DIV_SCALE = 10;

	public static void main(String[] args) {
		System.out.println(getIntNum(1222.125153));
		System.out.println(changeDecimal(12.125153, 4));
	}

	/**
	 * 得到整数位数
	 * 
	 * @param value
	 * @return
	 */
	public static int getIntNum(Double value) {
		DecimalFormat df = new DecimalFormat("#");
		String str = df.format(value).replaceAll("-", "");
		return str.length();
	}

	/**
	 * 四舍五入
	 * 
	 * @param value 值
	 * @param num   小数位数
	 * @return
	 */
	public static double changeDecimal(double value, int num) {
		BigDecimal b = new BigDecimal(value);
		double v = b.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();// 表明四舍五入，保留两位小数
		return v;
	}

	/**
	 * 得到不以科学计数数据格式的字符串全精度返回
	 * 
	 * @param str 字符串格式数值
	 * @return
	 */
	public static String toDecimalString(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}

		BigDecimal bigDecimal = new BigDecimal(str);
		String result = bigDecimal.toString();
		return result;
	}

	/**
	 * 得到不以科学计数数据格式的字符串全精度返回
	 * 
	 * @param num double数值
	 * @return
	 */
	public static String toDecimalString(Double num) {
		if (num == null) {
			return "";
		}
		return DoubleUtil.toDecimalString(num.toString());
	}

	/**
	 * 得到不以科学计数数据格式的字符串 四舍五入
	 * 
	 * @param str     字符串格式数值
	 * @param decimal 保留精度
	 * @return
	 */
	public static String toDecimalString(String str, int decimal) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		return DoubleUtil.toDecimalString(Double.valueOf(str.trim()), decimal);
	}

	/**
	 * 得到不以科学计数数据格式的字符串 四舍五入
	 * 
	 * @param num     数值
	 * @param decimal 精度
	 * @return
	 */
	public static String toDecimalString(Double num, int decimal) {
		if (num == null) {
			return "";
		}
		String str = "" + num;
		String formatStr = "0.";
		for (int i = 0; i < decimal; i++) {
			formatStr += "0";
		}
		DecimalFormat df = new DecimalFormat(formatStr);
		df.setRoundingMode(RoundingMode.HALF_UP);// 四舍五入
		try {
			str = "" + df.format(num);

		} catch (Exception e) {

		}
		return str;
	}

	/**
	 * 得到以,号分隔的计数数据格式的字符串 四舍五入
	 * 
	 * @param num
	 * @param decimal
	 * @return
	 */
	public static String toSplitDecimalString(Double num, int decimal) {
		if (num == null) {
			return "";
		}
		String str = "" + num;
		String formatStr = "###,##0.";
		for (int i = 0; i < decimal; i++) {
			formatStr += "0";
		}
		DecimalFormat df = new DecimalFormat(formatStr);
		df.setRoundingMode(RoundingMode.HALF_UP);// 四舍五入
		try {
			str = "" + df.format(num);
		} catch (Exception e) {

		}
		return str;
	}

	/** 得到以,号分隔的计数数据格式的字符串 */
	public static String toSplitDecimalString(String str, int decimal) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		return DoubleUtil.toSplitDecimalString(Double.valueOf(str.trim()), decimal);
	}

	/**
	 * 两个Double数相加
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.add(b2).doubleValue();
	}

	public static Double add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 两个Double数相加
	 * 
	 * @param v1
	 * @param v2
	 * @param num 小数点后保留几位
	 * @return
	 */
	public static Double add(Double v1, Double v2, int num) {
		return changeDecimal(add(v1, v2), num);
	}

	public static Double add(String v1, String v2, int num) {
		return changeDecimal(add(v1, v2), num);
	}

	/**
	 * 两个Double数相减
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.subtract(b2).doubleValue();
	}

	public static Double sub(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 两个double相减,并把结果设置精度
	 * 
	 * @param v1
	 * @param v2
	 * @param num 小数点后保留几位
	 * @return
	 */
	public static Double sub(Double v1, Double v2, int num) {
		return changeDecimal(sub(v1, v2), num);
	}

	public static Double sub(String v1, String v2, int num) {
		return changeDecimal(sub(v1, v2), num);
	}

	/**
	 * 两个Double数相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.multiply(b2).doubleValue();
	}

	public static Double mul(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 两个double相乘,并把结果设置精度
	 * 
	 * @param v1
	 * @param v2
	 * @param num 小数点后保留几位
	 * @return
	 */
	public static Double mul(Double v1, Double v2, int num) {
		return changeDecimal(mul(v1, v2), num);
	}

	public static Double mul(String v1, String v2, int num) {
		return changeDecimal(mul(v1, v2), num);
	}

	/**
	 * 两个Double数相除
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double div(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static Double div(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 * 
	 * @param v1
	 * @param v2
	 * @param scale 小数点后保留几位
	 * @return Double
	 */
	public static Double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 * 
	 * @param v1
	 * @param v2
	 * @param scale 小数点后保留几位
	 * @return Double
	 */
	public static Double div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 *
	 * <P>
	 * 比较浮点数大小
	 * </p>
	 *
	 * @author 闫枫
	 * @date Sep 1, 2017
	 * @param subMoney
	 * @param string
	 * @return int (-1:前小于后，0相等，1前大于后)
	 */
	public static int compareTo(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);
	}

	/**
	 *
	 * <P>
	 * 比较浮点数大小
	 * </p>
	 *
	 * @author 闫枫
	 * @date Sep 1, 2017
	 * @param subMoney
	 * @param string
	 * @return int (-1:前小于后，0相等，1前大于后)
	 */
	public static int compareTo(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);
	}

	/**
	 * 判断是否是一位小数或整数
	 */
	public static boolean isDoubleNumber(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher isNum = pattern.matcher(str);
		boolean isInt = isNum.matches();
		if (isInt) {
			return true;
		} else {
			if (str.length() == 2 || str.indexOf(".") != str.length() - 2) {
				return false;
			} else {
				return pattern.matcher(str.replaceFirst("\\.", "")).matches();
			}
		}
	}

	/**
	 * 对double值四舍五入
	 * 
	 * @param value 要格式化的double值
	 * @param num   保留的小数后有效个数
	 * @return 格式化结果
	 */
	public static String formatDouble(Double value, int num) {
		String format = "#";
		if (num > 0) {
			format += ".";
		}
		for (int i = 0; i < num; i++) {
			format += "#";
		}
		DecimalFormat df = new DecimalFormat(format);
		df.setRoundingMode(RoundingMode.HALF_UP);
		String result = df.format(value);
		return result;
	}

	/**
	 * 获取指定范围内随机数
	 * 
	 * @param max 最大值
	 * @param min 最小值
	 * @return
	 */
	public static Double getRandomDouble(String max, String min) {
		return add(min, mul(new Random().nextDouble(), sub(max, min)).toString());
	}

	/**
	 * 获取指定范围内随机数
	 * 
	 * @param max 最大值
	 * @param min 最小值
	 * @return
	 */
	public static Double getRandomDouble(Double max, Double min) {
		return add(min, mul(new Random().nextDouble(), sub(max, min)));
	}
}
