package com.factory.boot.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具类
 */
public class DateUtil {

	/**
	 * 根据自定义的格式获取日期字符串
	 * 
	 * @param customFormat 自定义的格式
	 * @return
	 */
	public static String getCurrDateString(String customFormat) {
		return new SimpleDateFormat(customFormat).format(new Date());
	}

	/**
	 * 根据自定义的格式获取 -- 给定日期 -- 字符串
	 * 
	 * @param customFormat 自定义的格式
	 * @param Date         给定日期对象
	 * @return
	 */
	public static String getCurrDateString(String customFormat, Date date) {
		return new SimpleDateFormat(customFormat).format(date);
	}

	/**
	 * 得到当前日期字符串，格式为 yyyy-MM-dd
	 * 
	 * @return 当前日期的字符串
	 */
	public static String getCurrDateString() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 按日期得到字符串，格式为 yyyy-MM-dd
	 * 
	 * @param date 日期
	 * @return
	 */
	public static String getDateString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 得到当前日期字符串，格式为 yyyy年MM月dd日
	 * 
	 * @return 当前日期的字符串
	 */
	public static String getCurrDateStringChina() {
		return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
	}

	/**
	 * 按日期得到字符串，格式为 yyyy年MM月dd日
	 * 
	 * @param date 日期
	 * @return
	 */
	public static String getDateStringChina(Date date) {
		return new SimpleDateFormat("yyyy年MM月dd日").format(date);
	}

	/**
	 * 得到当前精确到分的日期字符串，格式为 yyyy-MM-dd HH:mm
	 * 
	 * @return 当前日期的字符串
	 */
	public static String getCurrDateMinString() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
	}

	/**
	 * 按日期得到精确到秒的字符串，格式为 yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateMinString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}

	/**
	 * 得到当前精确到秒的日期字符串，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 当前日期的字符串
	 */
	public static String getCurrDateSecondString() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 按日期得到精确到秒的日期字符串，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateSecondString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 得到当前精确到毫秒的日期字符串，格式为yyyy-MM-dd HH:mm:ss:SSS
	 * 
	 * @return 当前日期的字符串
	 */
	public static String getCurrDateMillisecondString() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
	}

	/**
	 * 按字符串得到日期对象，格式为 yyyy-MM-dd
	 * 
	 * @param dateStr 日期字符串
	 * @return
	 */
	public static Date getDate(String dateStr) {
		if (StringUtils.isEmpty(dateStr)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			throw ExceptionUtil.convertExceptionToUnchecked(e);
		}
		return date;
	}

	public static Date getDate(String dateStr, String customFormat) {
		if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(customFormat)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(customFormat);
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			throw ExceptionUtil.convertExceptionToUnchecked(e);
		}
		return date;
	}

	/**
	 * 按字符串得到日期对象，格式为 yyyy-MM-dd HH:mm
	 * 
	 * @param str
	 * @return
	 */
	public static Date getDateMin(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			throw ExceptionUtil.convertExceptionToUnchecked(e);
		}
		return date;
	}

	/**
	 * 按字符串得到日期对象，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return
	 */
	public static Date getDateSecond(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 得到下个月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextMonthFirstDay(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.MONTH, 1);
		calender.set(Calendar.DAY_OF_MONTH, 1);
		Date d = calender.getTime();
		return d;
	}

	/**
	 * 是否过期
	 * 
	 * @param dateString 开始日期字符串，格式 yyyy-MM-dd HH:mm
	 * @param limit      有效天数
	 * @return
	 */
	public static boolean isTimeOut(String dateString, int limit) {
		Date dueDate = getDateMin(dateString);
		Date nowDate = getDateMin(DateUtil.getCurrDateMinString());
		if ((nowDate.getTime() - dueDate.getTime()) < limit * 24 * 60 * 60 * 1000) {
			return false;
		}
		return true;
	}

	/**
	 * 比较时间大小 （前减后）
	 * 
	 * @param d1 格式为 yyyy-MM-dd HH:mm:ss
	 * @param d2 格式为 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static boolean compareTime(String d1, String d2) {
		Date date1 = getDateSecond(d1);
		Date date2 = getDateSecond(d2);
		if (date1.getTime() - date2.getTime() >= 0) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * <P>
	 * 比较时间大小 （前减后）
	 * </p>
	 *
	 * @author 闫枫
	 * @date Dec 14, 2016
	 * @param date
	 * @param date_ void
	 */
	public static boolean compareTime(Date date1, Date date2) {
		if (date1.getTime() - date2.getTime() >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 比较日期大小
	 * 
	 * @param d1 格式为 yyyy-MM-dd
	 * @param d2 格式为 yyyy-MM-dd
	 * @return
	 */
	public static boolean compareDay(String d1, String d2) {
		Date date1 = getDate(d1);
		Date date2 = getDate(d2);
		if (date1.getTime() - date2.getTime() >= 24 * 60 * 60 * 1000) {
			return true;
		}
		return false;
	}

	/**
	 * 根据指定的年、月、日返回当前是星期几。
	 * 
	 * @param date (yyyy-mm-dd)
	 * @return 返回一个代表当期日期是星期几的数字。0表示星期天、1表示星期一、6表示星期六。
	 */
	public static int getDayOfWeek(String dateStr) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDate(dateStr));
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return w;
	}

	/**
	 * 得到星期显示值
	 * 
	 * @param dateStr 日期字符串，格式yyyy-MM-dd
	 * @return
	 */
	public static String getWeekStr(String dateStr) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return weekDays[getDayOfWeek(dateStr)];
	}

	/**
	 * 求两个日期相差天数（后减前）
	 * 
	 * @param startDateStr 开始日期字符串，格式yyyy-MM-dd
	 * @param endDateStr   结束日期字符串，格式yyyy-MM-dd
	 * @return 天数，整数
	 */
	public static long getIntervalDays(String startDateStr, String endDateStr) {
		Date d1 = getDate(startDateStr);
		Date d2 = getDate(endDateStr);
		return (d2.getTime() - d1.getTime()) / (3600 * 24 * 1000);
	}

	/**
	 *
	 * <P>
	 * 求两个日期相差天数(后减前)
	 * </p>
	 *
	 * @author 闫枫
	 * @date Dec 14, 2016
	 * @param date  开始日期
	 * @param date_ 结束日期 void
	 */
	public static Long getIntervalDays(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime()) / (3600 * 24 * 1000);
	}

	/*****************************************
	 * wbf补充 start
	 ************************************************/

	/**
	 * 
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @param date
	 * 
	 *               给定的日期对象
	 * 
	 * @param amount
	 * 
	 *               需要添加的天数，如果是向前的天数，使用负数就可以.
	 * 
	 * @param format
	 * 
	 *               输出格式. yyyy-MM-dd
	 * 
	 * @return Date 加上一定天数以后的Date对象.
	 * 
	 */
	public static String getFormatDateAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return getDateString(cal.getTime());
	}

	/**
	 * 
	 * 返回指定日期的前一天。<br>
	 * 
	 * @param sourceDate
	 * 
	 */
	public static String getYestoday(String sourceDate) {
		return getFormatDateAdd(getDate(sourceDate), -1);
	}

	/**
	 * 
	 * 返回指定日期的前一天。<br>
	 * 
	 * @param sourceDate
	 * 
	 */
	public static Date getYestoday(Date sourceDate) {
		return getDate(getFormatDateAdd(sourceDate, -1));
	}

	/**
	 * 返回指定日期的后一天。<br>
	 * 
	 * @param sourceDate
	 * 
	 */
	public static String getFormatDateTommorrow(String sourceDate) {
		return getFormatDateAdd(getDate(sourceDate), 1);
	}

	// -------------------- wbf补充 end------------------------

	/**
	 * 返回当月第一天 格式为 yyyy-MM-dd
	 */
	public static String getFiestDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cale.getTime());
	}

	/**
	 * 返回当月最后一天 格式为 yyyy-MM-dd
	 */
	public static String getLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(cale.getTime());
	}

	/**
	 * 返回 给定年月的 第一天 格式为 yyyy-MM-dd
	 * 
	 * @param year  int类型
	 * @param month int类型（使用0、1、2……10、11 分别代表 一月、二月、三月……11月、12月）
	 * 
	 */
	public static String getFiestDay(int year, int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.YEAR, year + 1900);// 计算机元年为1900
		cale.set(Calendar.MONTH, month);
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cale.getTime());
	}

	/**
	 * 返回 给定年月的 最后一天 格式为 yyyy-MM-dd
	 * 
	 * @param year  int类型
	 * @param month int类型（使用0、1、2……10、11 分别代表 一月、二月、三月……11月、12月）
	 */
	public static String getLastDay(int year, int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.YEAR, year + 1900);
		cale.set(Calendar.MONTH, month);
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(cale.getTime());
	}

	/**
	 * 获取两个日期时间差，格式：x天x小时x分钟x秒
	 * 
	 * @author 闫枫
	 * @date 2016年12月27日
	 * @param date1
	 * @param date2
	 * @return String
	 */
	public static String getDistanceTime(Date date1, Date date2) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
	}

	/**
	 * 获取两个日期时间差，格式：1天或1小时或1分钟或1秒
	 * 
	 * @author 闫枫
	 * @date 2016年12月27日
	 * @param date1
	 * @param date2
	 * @return String
	 */
	public static String getDistanceTimeSingle(Date date1, Date date2) {

		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

		if (day == 0 && hour != 0) {
			if (min > 55L) {
				hour = hour + 1L;
			}
			return hour + "-小时";
		} else if (day > 0) {
			if (hour > 12L) {
				day = day + 1L;
			}
			return day + "-天";
		} else if (hour == 0 && min != 0) {
			if (sec > 55L) {
				min = min + 1L;
			}
			return min + "-分钟";
		} else if (hour > 0) {
			if (min > 55L) {
				hour = hour + 1;
			}
			return hour + "小时";
		} else {
			return sec + "-秒";
		}
	}

	/**
	 * 获取两个日期时间差，结果格式：x天x小时x分x秒，参数格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @author 闫枫
	 * @date 2016年12月27日
	 * @param date1
	 * @param date2
	 * @return String
	 */
	public static String getDistanceTime(String str1, String str2) {

		Date date1 = getDateSecond(str1);
		Date date2 = getDateSecond(str2);

		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
	}

	/**
	 * 获取两个日期相差天数，不足24小时算1天。 d2-d1, 后减前
	 * 
	 * @author 闫枫
	 * @date 2017年3月24日
	 * @param d1
	 * @param d2
	 * @return Long
	 */
	public static long getIntervalDayHours(Date d1, Date d2) {
		long l1 = (d2.getTime() - d1.getTime()) / (3600 * 1000);
		long l2 = (d2.getTime() - d1.getTime()) % (3600 * 1000);
		if (l2 != 0) {
			++l1;
		}
		if ((l1 % 24) != 0) {
			return (l1 / 24L) + 1L;
		} else {
			return l1 / 24L;
		}
	}

	/**
	 * 获取两个日期相差多少月，不足24小时算一天，不足30天算一个月，d2-d1，后减前
	 * 
	 * @author 闫枫
	 * @date 2017年3月27日
	 * @param d1
	 * @param d2
	 * @return long
	 */
	public static long getIntervalDayMonths(Date d1, Date d2) {
		long result = getIntervalDayHours(d1, d2);

		if (result <= 0) {
			return 0;
		}

		if ((result % 30) != 0) {
			return (result / 30L) + 1L;
		} else {
			return result / 30L;
		}
	}

	/**
	 * 获取两个日期相差月份
	 * 
	 * @author 闫枫
	 * @date 2017年4月6日
	 * @param d1
	 * @param d2
	 * @return int
	 */
	public static int getMonthSpace(String d1, String d2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int result = 0;
		int month = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(d1));
			c2.setTime(df.parse(d2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw ExceptionUtil.convertExceptionToUnchecked(e);
		}

		result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
		return Math.abs(month + result);
	}

	/**
	 *
	 * <P>
	 * 获取2个日期相差的小时(后减前)
	 * </p>
	 *
	 * @author 闫枫
	 * @date Jan 4, 2017
	 * @param d1
	 * @param d2
	 * @return Long 相差的小时数
	 */
	public static Long getIntervalHours(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime()) / (3600 * 1000);
	}

	/**
	 *
	 * <P>
	 * 获取2个日期相差的小时(后减前)
	 * </p>
	 *
	 * @author 闫枫
	 * @date Jan 4, 2017
	 * @param date1 字符串类型的日期，格式为：yyyy-MM-dd HH:mm:ss
	 * @param date2 字符串类型的日期，格式为：yyyy-MM-dd HH:mm:ss
	 * @return Long 相差的小时数
	 */
	public static Long getIntervalHours(String date1, String date2) {
		Date d1 = getDateSecond(date1);
		Date d2 = getDateSecond(date2);
		return (d2.getTime() - d1.getTime()) / (3600 * 1000);
	}

	/**
	 *
	 * <P>
	 * 返回日期加上 --- 一定月 --- 后的日期对象
	 * </p>
	 *
	 * @author 闫枫
	 * @date Jan 7, 2017
	 * @param date   给定的日期对象
	 * @param amount 需要加的月份数（可正可负，正数代表加上多少以后反正代表减）
	 * @return Date 加上一定月份后的日期对象
	 */
	public static Date getDateAddMonth(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.MONTH, amount);
		return cal.getTime();
	}

	/**
	 * 取得给定日期加上一定天数后的日期对象.
	 * 
	 * @author 闫枫
	 * @date 2017年4月5日
	 * @param date   给定的日期对象
	 * @param amount 需要添加的天数，如果是向前的天数，使用负数就可以.
	 * @param format 输出格式yyyy-MM-dd HH:mm
	 * @return Date 加上一定天数以后日期字符串.
	 */
	public static String getFormatDateAddMin(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, amount);
		return getDateMinString(cal.getTime());
	}

	/**
	 *
	 * <P>
	 * 取得给定日期加上一定-- 分钟后 --的日期对象.
	 * </p>
	 *
	 * @author 闫枫
	 * @date Jan 5, 2017
	 * @param date   给定的日期对象
	 * @param amount 需要添加的分钟数，如果是向前的分钟数，使用负数就可以.
	 * @return String 加上一定分钟数后的date对象
	 */
	public static Date getFormatMinuteAdd(Date date, int amount) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.MINUTE, amount);
		return cal.getTime();
	}

	/**
	 * 
	 * <p>
	 * 获取给定日期所在周的每一天
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param mdate
	 * @return List<Date>
	 */
	public static List<Date> dateToWeek(Date mdate) {
		List<Date> list = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(mdate);
		int date = cal.get(Calendar.DAY_OF_MONTH);
		int n = cal.get(Calendar.DAY_OF_WEEK);
		if (n == 1) {
			n = 7;
		} else {
			n = n - 1;
		}
		for (int i = 1; i <= 7; i++) {
			cal.set(Calendar.DAY_OF_MONTH, date + i - n);
			list.add(cal.getTime());
		}
		return list;
	}

	/**
	 * 
	 * <p>
	 * 获取指定月份的天数
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param year
	 * @param month
	 * @return int
	 */
	public static int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 
	 * <p>
	 * 获取给定日期所在月的每一天
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param month
	 * @return List<Date>
	 */
	public static List<Date> dayToMonth(Date d) {
		List<Date> lst = new ArrayList<>();
		Date date = getMonthStart(d);
		Date monthEnd = getMonthEnd(d);
		while (!date.after(monthEnd)) {
			lst.add(date);
			date = getNext(date);
		}
		return lst;
	}

	/**
	 * 
	 * <p>
	 * 获取给定日期所在月的第一天
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param date
	 * @return Date
	 */
	public static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (1 - index));
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * 获取给定日期所在月的最后一天
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param date
	 * @return Date
	 */
	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (-index));
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * 获取给定日期的下一天
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param date
	 * @return Date
	 */
	public static Date getNext(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 
	 * <p>
	 * 获取给定日期范围内每一个日期
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param dBegin
	 * @param dEnd
	 * @return List<Date>
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			lDate.add(calBegin.getTime());
			calBegin.add(Calendar.DAY_OF_YEAR, 1);
		}
		return lDate;
	}

	/**
	 * 
	 * <p>
	 * 获取当年的第一天
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @return Date
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 
	 * <p>
	 * 获取当年的最后一天
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @return Date
	 */
	public static Date getCurrYearLast() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 
	 * <p>
	 * 获取某年第一天日期
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param year
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 
	 * <p>
	 * 获取某年最后一天日期
	 * </p>
	 *
	 * @author 刘旭
	 * @date 2018年7月23日
	 * @param year
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	/**
	 * 计算以某天开始的第几周星期几是几月几日
	 * 
	 * @param startDate 开始日期
	 * @param weekNum   第几周
	 * @param dayOfWeek 星期几 周日传7
	 * @return
	 */
	public static String getDateWithTWeek(String startDate, Integer weekNum, Integer dayOfWeek) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(DateUtil.getDate(startDate));
		c.add(Calendar.DATE, (weekNum - 1) * 7);
		c.set(Calendar.DAY_OF_WEEK, (dayOfWeek + 1) % 7);
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		return sf2.format(c.getTime());
	}

	/**
	 * 获取某天是以哪天为开始的第几周星期几
	 * 
	 * @param startDate  开始日期
	 * @param searchDate 要查询的日期
	 * @return array[0] 第几周， 如果开始日期大于要查询的日期，则返回负数周 array[1] 星期几，
	 *         如果开始日期大于要查询的日期，则正常返回星期几
	 */
	public static Integer[] getTWeekWithDate(String startDate, String searchDate) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(DateUtil.getDate(startDate));
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		long nd = 1000 * 24 * 60 * 60 * 7;
		Integer[] tweek = new Integer[2];
		long spaceTime = DateUtil.getDate(searchDate).getTime() - c.getTime().getTime();
		Integer weekNum = Integer.parseInt(String.valueOf(spaceTime / nd));
		c.setTime(DateUtil.getDate(searchDate));
		if (spaceTime < 0) {
			tweek[0] = weekNum - 1;
		} else {
			tweek[0] = weekNum + 1;
		}
		Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0)
			tweek[1] = 7;
		else
			tweek[1] = dayOfWeek;
		return tweek;
	}

	/**
	 * 获取日期范围内(绝对值)有多少周
	 * 
	 * @desc 例如：2012-11-06(周二)到2012-11-19(周一)范围内，有3周
	 * 
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @return 天数
	 */
	public static int getWeekNumWithDateScope(String startDate, String endDate) {
		int result = 0;
		Date sdate = DateUtil.getDate(startDate);
		long LEN = Math.abs(DateUtil.getIntervalDays(startDate, endDate));// 间隔多少天
		// if (LEN < 0) return 0;
		int dayOfWeek = 0;
		for (int i = 0; i <= LEN; i++) {
			// 遍历开始日期后的每一天
			String dayAfterStartDate = DateUtil.getFormatDateAdd(sdate, i);
			dayOfWeek = DateUtil.getDayOfWeek(dayAfterStartDate);// 判断日期是周几，周日至周六分别是0到6
			if (dayOfWeek == 0) { // 到周日就算一周
				result++;
				long rest = LEN - i;
				if (rest > 0 && rest < 7) {// 到周日后还有剩余天数且不足7天，再加一周
					result++;
					break;
				}
			}
		}
		return result == 0 ? 1 : result;// 不足7天的算一周
	}

	/**
	 * 获取日期范围内的哪些天
	 * 
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @return array[] 日期的集合
	 */
	@SuppressWarnings("boxing")
	public static String[] getDaysWithDateScope(String startDate, String endDate) {
		if (DateUtil.compareDay(startDate, endDate)) {
			throw new RuntimeException("开始日期必须小于于结束日期");
		}
		if (startDate.equals(endDate)) {
			return new String[] { startDate };
		}
		Date sdate = DateUtil.getDate(startDate);
		Long LEN = DateUtil.getIntervalDays(startDate, endDate);// 用来计算两日期之间总共有多少天
		String[] dateResult = new String[LEN.intValue() + 1];
		dateResult[0] = startDate;
		for (int i = 1; i < LEN + 1; i++) {
			dateResult[i] = DateUtil.getFormatDateAdd(sdate, i);
		}
		return dateResult;
	}

	/**
	 * 获取日期范围内的第几周有哪些天
	 * 
	 * @desc 例如：2012-09-05(周三)到2013-01-18(周五)范围内，第1周有5天(周三到周日)，第2周有7天(周一到周日)
	 * 
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @param weekNum   第几周
	 * @return array[] 日期的集合
	 */
	@SuppressWarnings("boxing")
	public static String[] getDaysWithWeekNum(String startDate, String endDate, int weekNum) {
		List<String> list = Arrays.asList(getDaysWithDateScope(startDate, endDate));
		List<String> days = new ArrayList<String>();
		for (int i = 1; i <= 7; i++) {
			String dateWithTWeek = getDateWithTWeek(startDate, weekNum, i);
			if (list.contains(dateWithTWeek)) {
				days.add(dateWithTWeek);
			}
		}
		return days.toArray(new String[days.size()]);
	}

}
