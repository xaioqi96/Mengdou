/**
 * Project Name:frame-common File Name:DateUtils.java Package Name:com.wuys.utils Date:2016年1月7日 下午1:03:59
 * Copyright (c) 2016, WYS All Rights Reserved.
 *
 */

package com.md.utils;

import com.md.enums.DateTimePatternEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by ljx on 2017/2/7.
 */

public class DateUtils {

	// 锁对象
	private static final Object lockObj = new Object();

	// 存放不同的日期模板格式的sdf的Map
	private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static final String[] WEEKCN = { "周日", "周一", "周二", "周三", "周四", "周五", "周六", };


	/**
	 * 
	 * 返回一个ThreadLocal的sdf，每个线程只会new一次sdf. <br/>
	 *
	 * @author WYS
	 * @param pattern
	 * @return
	 * @since JDK 1.7
	 */
	private static SimpleDateFormat getSdf( final String pattern ) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

		if ( null == tl ) {
			synchronized ( lockObj ) {
				tl = sdfMap.get(pattern);
				if ( null == tl ) {
					tl = new ThreadLocal<SimpleDateFormat>() {

						@Override
						protected SimpleDateFormat initialValue() {

							return new SimpleDateFormat(pattern);
						}
					};
					sdfMap.put(pattern, tl);
				}
			}
		}
		return tl.get();
	}


	public static String format( Date date, String pattern ) {
		return getSdf(pattern).format(date);
	}


	public static Date parse( String dateStr, String pattern ) {
		try {
			return getSdf(pattern).parse(dateStr);
		} catch ( ParseException e ) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 按照固定类型转换Date成date
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse( Date date, String pattern ) {
		return parse(format(date, pattern), pattern);
	}


	/**
	 * 
	 * 获取某年某月第多少天是周几，这个月总共有多少天. <br/>
	 *
	 * @author WYS
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @since JDK 1.7
	 */
	public static Map<String, Integer> getTotalDayAndFirstWeekDay4Month( int year, int month, int day ) {
		Map<String, Integer> result = new HashMap<String, Integer>();

		// 获取一个月有多少天
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		// java 月份从0开始算，1代表上一个月
		cal.set(Calendar.MONTH, month - 1);
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		// 获取当前日期是星期几
		cal.set(Calendar.DATE, day);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		result.put("totalDay", dateOfMonth);
		result.put("firstWeekDay", week);
		return result;
	}


	public static Date getDayYYYYMMDD( Date date ) {
		String dateStr = format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern());
		try {
			return parse(dateStr, DateTimePatternEnum.YYYY_MM_DD.getPattern());
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return date;
	}


	/**
	 * 
	 * 判断时间字符串是否在当天之前. <br/>
	 *
	 * @author WYS
	 * @param date
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean beforeNowDate( String date ) {
		try {
			Date d = getDayYYYYMMDD(new Date());
			boolean flag = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD.getPattern()).parse(date).before(d);
			return flag;
		} catch ( ParseException e ) {
			return false;
		}
	}


	/**
	 * 比较两个日期dateBefor是否在dateAfter之前，如果在其之前则返回true
	 * <p>
	 *
	 * @param dateBefor
	 * @param dateAfter
	 * @return 
	 */
	public static boolean beforeForCustomDate( String dateBefor, String dateAfter ) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dateBefor1 = sf.parse(dateBefor);
			Date dateAfter1 = sf.parse(dateAfter);
			if ( dateBefor1.before(dateAfter1) ) {
				return true;
			}
		} catch ( ParseException e ) {
			e.printStackTrace();
		}

		return false;
	}


	public static int getWeek( Date date ) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int w = c.get(Calendar.DAY_OF_WEEK) - 1;
		return w;
	}


	public static String getWeekChinese( Date date ) {
		int w = getWeek(date);
		return WEEKCN[w];
	}


	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}


	public static int getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}


	/**
	 * 根据日期返回日
	 * 
	 * @param date 日期
	 * @return 返回日份
	 */
	public static int getDayByDate( Date date ) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}


	/**
	 * 获取上一年
	 * <p>
	 *
	 * @param date
	 * @return 
	 */
	public static Date getLastYear( Date date ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		return cal.getTime();
	}


	/**
	 * 获取上个月
	 * <p>
	 *
	 * @param date
	 * @return 
	 */
	public static Date getPrevMonth( Date date ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}


	/**
	 * 获取当前时间
	 * <p>
	 *
	 * @param pattern 日期时间格式
	 * @return
	 */
	public static String getCurrentTime( String pattern ) {
		Date date = new Date();
		String currentTime = format(date, pattern);
		return currentTime;
	}


	/**
	 * 获取当月的第一天
	 * <p>
	 *
	 * @return 
	 */
	public static String getCurrentMonthFirstDay() {
		Calendar cal_1 = Calendar.getInstance();
		cal_1.add(Calendar.MONTH, 0);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天 
		String firstDay = format(cal_1.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern());
		return firstDay;
	}


	/**
	 * 获取当月的最后一天
	 * <p>
	 *
	 * @return 
	 */
	public static String getCurrentMonthLastDay() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format(ca.getTime(), DateTimePatternEnum.YYYY_MM_DD.getPattern());
		return last;
	}


	/**
	 * 获取指定几个月后的那月的第一天和最后一天
	 * <p>
	 *
	 * @param num	几个月（当num=0则表示当前月）
	 * @return 
	 */
	public static Map<String, String> getCustomMonthFirstDayAndLastDay( int num ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> map = new HashMap<String, String>();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + num);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = sdf.format(c.getTime());
		map.put("firstDay", firstDay);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = sdf.format(c.getTime());
		map.put("lastDay", lastDay);
		return map;
	}


	/**
	 * 获取当前天的前6月时间
	 * <p>
	 *
	 * @return 
	 */
	public static String getHalfYearTime() {
		SimpleDateFormat matter = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -6);
		Date date02 = calendar.getTime();
		String halfTime = matter.format(date02);
		return halfTime;
	}


	/**
	 * 获取当前天的前7天时间
	 * <p>
	 *
	 * @return 
	 */
	public static String getStatetime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}


	/**
	 * 获取之前的n天日期(自定义)
	 * <p>
	 *
	 * @param day 要找的之前的天数(比如前一天，则day=1即可)
	 * @return
	 */
	public static String getCustomPrevDay( int day ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -day);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}


	/**
	 * 获取未来n天的日期(自定义)
	 * <p>
	 * @param day 要找的未来的天数(比如未来7天，则day=7即可)
	 *
	 * @return 
	 */
	public static String getCustomNextDay( int day ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}


	/**
	 *  加几天   时间戳
	 * <p>
	 * @param systime 需要加天数的日期
	 * @param day 要找的未来的天数(比如未来7天，则day=7即可)
	 *
	 * @return
	 */
	public static Long addDay( Long systime, int day ) {

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(systime);
		c.add(Calendar.DATE, day);

		return c.getTimeInMillis();
	}


	/**
	 * 比较两个日期相差的天数
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static int compareDate( String starDate, String endDate ) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date now;
		int day = 0;
		try {
			now = df.parse(endDate);
			Date date = df.parse(starDate);
			long l = now.getTime() - date.getTime();
			day = (int) (l / (24 * 60 * 60 * 1000));

		} catch ( ParseException e ) {
			e.printStackTrace();
		}
		return day < 0 ? 0 : day;
	}


	public static String getNextTimestampLast( int month, Date date ) {

		if ( month < 1 ) { //返回当前系统时间的时间戳
			return System.currentTimeMillis() + "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化对象
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(date);//设置当前日期
		calendar.add(Calendar.MONTH, month);//月份加一

		System.out.println(sdf.format(calendar.getTime()));//输出格式化的日期


		return sdf.format(calendar.getTime()) + "";
	}


	/**
	 * 获取指定日期加上月份后的日期
	 *
	 * @param month
	 * @param date 如果为空，则默认为当前日期
	 * @param type 日期返回格式 1：yyyy-MM-dd HH:mm:ss 2：yyyy-MM-dd 3:时间戳
	 * @return 
	 */
	public static String getAddMonthTime( int month, Date date, int type ) {
		if ( date == null ) {
			date = new Date();
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		if ( type == 2 ) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		if ( type != 3 ) {
			return sdf.format(calendar.getTime());
		} else {
			return String.valueOf(calendar.getTimeInMillis());
		}

	}


	/**
	 * 获取指定日期减去month之后的日期
	 * <p>
	 * 比如获取前3个月、前6个月等等，则只需传month = 3  或  6 即可
	 *
	 * @param month	要减去的月份
	 * @param date  如果为空，则默认为当前日期
	 * @param type  日期返回格式 1：yyyy-MM-dd HH:mm:ss 2：yyyy-MM-dd
	 * @return 
	 */
	public static String getCustomPrevMonth( int month, Date date, int type ) {
		if ( date == null ) {
			date = new Date();
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		if ( type == 2 ) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -month);
		return sdf.format(calendar.getTime()) + "";
	}


	/**
	 * 计算两个时间相差的天数
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static int getDeltaT( String starDate, String endDate ) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date now;
		int day = 0;
		try {
			now = df.parse(endDate);
			Date date = df.parse(starDate);
			long l = now.getTime() - date.getTime();
			day = (int) (l / (24 * 60 * 60 * 1000));

		} catch ( ParseException e ) {
			e.printStackTrace();
		}
		return day < 0 ? day * -1 : day;
	}


	/**
	 * 将时间戳转换成指定的时间格式
	 * @param date		时间戳
	 * @param format	格式
	 * @return
	 */
	public static String getStrDateFormat( String date, String format ) {
		try {
			long loanDate = Long.parseLong(date);
			Date newDate = new Date(loanDate);
			SimpleDateFormat sdf = new SimpleDateFormat(format == null ? "yyyy-MM-dd" : format);
			return sdf.format(newDate);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return "";
	}


	/**
	 * 日期格式字符串转换成时间戳
	 * @param date_str 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp( String date_str, String format ) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime());
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return "";
	}


	/**
	 * 返回当前系统时间
	 * @param format
	 * @return
	 */
	public static String getSysTimes( String format ) {

		if ( format != null ) {
			Date curDate = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(curDate); //返回指定格式的系统时间
		}
		return System.currentTimeMillis() + ""; //返回系统时间戳
	}


	/**
	* 根据生日返回年龄
	* @param birthday
	* @return 年龄
	*/
	public static int getAge( String birthday ) {
		Date date = new Date();
		Calendar today = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		today.setTime(date);
		birth.setTime(DateUtils.parse(birthday, "yyyyMMdd"));
		int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) - 1;
		if ( birth.get(Calendar.MONTH) < today.get(Calendar.MONTH)
				|| birth.get(Calendar.MONTH) == today.get(Calendar.MONTH) && birth.get(Calendar.DATE) <= today.get(Calendar.DATE) ) {
			age++;
		}
		return age;
	}


	/**  
	 * 根据输入的日期 获取 凌晨   
	 * @param date  
	 * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>  
	 *       1 返回yyyy-MM-dd 23:59:59日期  
	 * @return   
	 */
	public static Date weeHours( Date date, int flag ) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		//时分秒（毫秒数）    
		long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
		//凌晨00:00:00    
		cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

		if ( flag == 0 ) {
			return cal.getTime();
		} else if ( flag == 1 ) {
			//凌晨23:59:59    
			cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
		}
		return cal.getTime();
	}


	/**
	 * 校验日期是否合法
	 *
	 * @param str
	 * @param pattern
	 * @return 
	 */
	public static boolean isValidDate( String str, String pattern ) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			format.parse(str);
		} catch ( ParseException e ) {

			convertSuccess = false;
		}
		return convertSuccess;
	}


	/**
	 * 判断时间是否在这个时间段内
	* @param date
	* @param dateBefor
	* @param dateAfter
	* @param format
	* @return
	*/
	public static boolean atRange( Date date, String dateBefor, String dateAfter, String format ) {
		if ( ObjectUtils.isNull(date) || StringUtils.isEmpty(format) || StringUtils.isEmpty(dateBefor) || StringUtils.isEmpty(dateAfter) ) return false;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			String now = sf.format(date);
			Date time = sf.parse(now);
			Date dateAfter1 = sf.parse(dateAfter);
			Date dateBefor1 = sf.parse(dateBefor);
			if ( time.before(dateAfter1) && time.after(dateBefor1) ) {
				return true;
			}
		} catch ( ParseException e ) {
			e.printStackTrace();
		}

		return false;
	}


	/*
	 * 参数：时间戳    增加天数
	 * 返回增加一定天数以后的时间
	 */
	public static String getDateAfterAddDay( long baseDate, int day ) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(baseDate);
		cd.add(Calendar.DATE, day);//增加一天
		int month = cd.get(Calendar.MONTH) + 1;
		String linshiMonth = "";
		int date = cd.get(Calendar.DATE);
		String linshiDay = "";
		if ( (cd.get(Calendar.MONTH) + 1) < 10 ) {
			linshiMonth = "0" + month;
		} else {
			linshiMonth = month + "";
		}
		if ( date < 10 ) {
			linshiDay = "0" + date;
		} else {
			linshiDay = date + "";
		}
		String linshiDate = cd.get(Calendar.YEAR) + "-" + linshiMonth + "-" + linshiDay;

		return linshiDate;

	}


	/**
	 * 根据字符串返回指定格式的日期
	 *
	 * @param dateStr 日期(字符串)
	 * @param format 日期格式
	 * @return 日期(Date)
	 * @throws ParseException
	 */
	public static Date convertDate( String dateStr, String format ) throws ParseException {
		Date date = null;
		if ( StringUtils.isEmpty(dateStr) ) {
			return date;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		date = simpleDateFormat.parse(dateStr);
		return date;
	}


	/*
	 * 年月日的字符串   加天数后的返回值
	 */
	public static String getDateAfterAddDay( String baseDate, int day ) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cd = Calendar.getInstance();
		cd.setTime(sdf.parse(baseDate));
		cd.add(Calendar.DATE, day);//增加一天
		int month = cd.get(Calendar.MONTH) + 1;
		String linshiMonth = "";
		int date = cd.get(Calendar.DATE);
		String linshiDay = "";
		if ( (cd.get(Calendar.MONTH) + 1) < 10 ) {
			linshiMonth = "0" + month;
		} else {
			linshiMonth = month + "";
		}
		if ( date < 10 ) {
			linshiDay = "0" + date;
		} else {
			linshiDay = date + "";
		}
		String linshiDate = cd.get(Calendar.YEAR) + "-" + linshiMonth + "-" + linshiDay;

		return linshiDate;

	}


	public static boolean nowAtRangeYMDHS( String dateBefor, String dateAfter ) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String now = sf.format(new Date());
			Date time = sf.parse(now);
			Date dateAfter1 = sf.parse(dateAfter);
			Date dateBefor1 = sf.parse(dateBefor);
			if ( time.before(dateAfter1) && time.after(dateBefor1) ) {
				System.out.println(sf.format(time) + "此区间");
				return true;
			}
		} catch ( ParseException e ) {
			e.printStackTrace();
		}

		return false;
	}


	/**
	 * 
	 * 查看指定时间是否在时间范围内
	 * <p>
	 *
	 * @param specifyTime
	 * @param dateBefor
	 * @param dateAfter
	 * @return 
	 */
	public static boolean checkTimeAtRangeYMDHS( String specifyTime, String dateBefor, String dateAfter ) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time = sf.parse(specifyTime);
			Date dateAfter1 = sf.parse(dateAfter);
			Date dateBefor1 = sf.parse(dateBefor);
			if ( time.before(dateAfter1) && time.after(dateBefor1) ) {
				System.out.println(sf.format(time) + "此区间");
				return true;
			}
		} catch ( ParseException e ) {
			e.printStackTrace();
		}

		return false;
	}


	/*
	 * 参数：时间戳    增加月数
	 * 返回增加月以后的时间
	 */

	public static String getDateAfterAddMonth( long baseDate, int addMonth ) {
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(baseDate);
		cd.add(Calendar.MONTH, addMonth);//增加一天
		int month = cd.get(Calendar.MONTH) + 1;
		String linshiMonth = "";
		int date = cd.get(Calendar.DATE);
		String linshiDay = "";
		if ( (cd.get(Calendar.MONTH) + 1) < 10 ) {
			linshiMonth = "0" + month;
		} else {
			linshiMonth = month + "";
		}
		if ( date < 10 ) {
			linshiDay = "0" + date;
		} else {
			linshiDay = date + "";
		}
		String linshiDate = cd.get(Calendar.YEAR) + "-" + linshiMonth + "-" + linshiDay;

		return linshiDate;

	}


	public static long date2timestamp( String dateStr ) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = sf.parse(dateStr);
		return d.getTime();
	}


	/**
	 * 比较两个时间的大小
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static int compareDeltaT( String starDate, String endDate ) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date now;
		int day = 0;
		try {
			now = df.parse(endDate);
			Date date = df.parse(starDate);
			long l = now.getTime() - date.getTime();
			day = (int) (l / (24 * 60 * 60 * 1000));

		} catch ( ParseException e ) {
			e.printStackTrace();
		}
		return day < 0 ? 0 : day;
	}


	public static boolean beforDate( String dateBefor, String dateAfter ) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dateBefor1 = sf.parse(dateBefor);
			Date dateAfter1 = sf.parse(dateAfter);
			if ( dateBefor1.before(dateAfter1) ) {
				return true;
			}
		} catch ( ParseException e ) {
			e.printStackTrace();
		}

		return false;
	}


	/**
	 * 
	 * 将时间戳转换为日期
	 * <p>
	 *
	 * @param time
	 * @return 
	 */
	public static Date changeTimeToDate( long time ) {
		Date date = new Date();
		date.setTime(time);
		return date;
	}


	/**
	 * 将一种日期类型，转换成别一种 20121208--->2012-12-08
	* @param date
	* @param formatBefore
	* @param formatAfter
	* @return
	*/
	public static String dateFormatDate( String date, String formatBefore, String formatAfter ) {
		if ( StringUtils.isEmpty(date) || StringUtils.isEmpty(formatBefore) || StringUtils.isEmpty(formatBefore) ) {
			return "";
		}
		try {
			SimpleDateFormat sf1 = new SimpleDateFormat(formatBefore);
			SimpleDateFormat sf2 = new SimpleDateFormat(formatAfter);
			return sf2.format(sf1.parse(date));
		} catch ( Exception e ) {
			e.printStackTrace();
			return "";
		}
	}


	public static int getNowHour() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		int time = Integer.parseInt(sdf.format(date));
		return time;
	}


	public static long getCompareHour( String endDate, String nowDate ) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long nd = 1000 * 24 * 60 * 60;
			long nh = 1000 * 60 * 60;
			long nm = 1000 * 60;
			// long ns = 1000;
			// 获得两个时间的毫秒时间差异
			long diff = sf.parse(endDate).getTime() - sf.parse(nowDate).getTime();
			// 计算差多少天
			long day = diff / nd;
			// 计算差多少小时
			//long hour = diff % nd / nh;
			long hour = diff / nh;
			// 计算差多少分钟
			long min = diff % nd % nh / nm;
			return hour;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return 0;
	}


	/**
	 * 判断是否是闰年
	 * <p>
	 *
	 * @param year
	 * @return 如果是闰年返回true,否则返回false
	 */
	public static boolean isLeapYear( long year ) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) ? true : false;
	}


	//计算两个日期相差年数  
	public static int yearDateDiff( String startDate, String endDate ) {
		Calendar calBegin = Calendar.getInstance(); //获取日历实例  
		Calendar calEnd = Calendar.getInstance();
		calBegin.setTime(DateUtils.parse(startDate, "yyyy")); //字符串按照指定格式转化为日期  
		calEnd.setTime(DateUtils.parse(endDate, "yyyy"));
		return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
	}


	public static void main( String[] args ) throws Exception {
		System.out.println( DateUtils.getCurrentTime(DateTimePatternEnum.YYYYMMDD_DIAGONAL.getPattern()));
//		1386659100000--2013-12-10
		String date = "1386659100000";
		Date now=new Date(Long.parseLong(date));
		System.out.println(DateUtils.format(now,DateTimePatternEnum.YYYYMMDD_DIAGONAL.getPattern()));

	}


}
