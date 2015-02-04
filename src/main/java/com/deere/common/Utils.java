package com.deere.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.deere.exception.GenericException;

public class Utils {
	public static String readTxtFile(String filePath) throws IOException {
		String content = "";
		try {
			String encoding = "UTF-8";

			File file = new File(filePath);
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				content += lineTxt + "\n";
			}
			read.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("找不到指定的文件或读取文件内容出错", e);
		}
		return content;

	}

	public static Method findGetId(Class cls) {
		for (Method m : cls.getDeclaredMethods()) {
			Annotation[] annList = m.getDeclaredAnnotations();
			for (Annotation ann : annList) {
				if (ann.annotationType().equals(Id.class)) {
					return m;
				}
			}
		}

		return null;
	}

	public static List json2Object(String jsonString, Class clazz) throws GenericException {

		List dtoList = new ArrayList();
		try {
			JSONArray array = JSONArray.fromObject(jsonString);

			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Object obj = JSONObject.toBean(jsonObject, clazz);
				// obj[i]= JSONObject.toBean(jsonObject, clazz);
				dtoList.add(obj);
			}

		} catch (Exception e) {
			throw new GenericException(Const.ERR_CODE_SOR_01, "Json Convert Error", e);
		}
		return dtoList;
	}

	public static String object2Json(Object obj) {
		return JSONObject.fromObject(obj).toString();
	}

	public static Map<String, Date> fiscalYear() {

		Calendar cal = Calendar.getInstance();
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		int currentYear = cal.get(Calendar.YEAR);

		if (currentMonth > 10) {
			currentYear = currentYear + 1;
		}

		Calendar from = Calendar.getInstance();
		/*
		 * from.set(Calendar.YEAR, currentYear - 1);
		 * from.set(Calendar.MONTH, 10);
		 * from.set(Calendar.DAY_OF_MONTH, 1);
		 */
		from.set(currentYear - 1, 10, 1, 0, 0, 0);

		Calendar to = Calendar.getInstance();
		/*
		 * to.set(Calendar.YEAR, currentYear);
		 * to.set(Calendar.MONTH, 9);
		 * to.set(Calendar.DAY_OF_MONTH, 31);
		 */
		to.set(currentYear, 9, 31, 0, 0, 0);

		Map<String, Date> fiscalYear = new HashMap<String, Date>();
		fiscalYear.put("from", from.getTime());
		fiscalYear.put("to", to.getTime());

		return fiscalYear;
	}

	public static int getFiscalMonthByDay(Calendar cal) {
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(day);
		int month = cal.get(Calendar.MONTH);
		return ((month + 2) % 12) + 1;
	}

	/**
	 * 
	 * @Title: getFiscalMonth
	 * @Description: get Fiscal Month
	 * @param day
	 * @throws GenericException
	 * @return int
	 */
	public static int getFiscalMonth(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		if (isFiscalMonthOfWeek(day)) {
			Date converWeek = getMonDay(day, 2);
			int a = (dateDifferent(day, converWeek) + 7) % 7;
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - a);
			return getFiscalMonthByDay(cal);
		}
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 7);
		// 判断时间前一周是否是财年结算周
		if (isFiscalMonthOfWeek(cal.getTime())) {
			// 重新获取时间
			cal.setTime(day);
			Date converWeek = getMonDay(day, 6);
			int a = (dateDifferent(day, converWeek)) % 7;
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - a);
			return getFiscalMonthByDay(cal);
		}
		// 重新获取时间 －－ 普通时间
		cal.setTime(day);
		return getFiscalMonthByDay(cal);
	}

	public static int getFiscalYear(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		if (month > 8) {
			int fiscalMonth = getFiscalMonth(day);
			if (fiscalMonth != 12) {
				year++;
			}
		}
		return year;
	}

	// 获得时间为当周转换成周几
	public static Date getMonDay(Date day, int weekDay) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(day);
		cd.add(Calendar.DATE, -cd.get(Calendar.DAY_OF_WEEK) + weekDay);
		return cd.getTime();
	}

	public static Date firstDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		return cal.getTime();

	}

	public static Date lastDayOfMonth(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1, 23, 59, 59);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();

	}

	public static String strFirstDay(Date day) {
		Date date = firstDay(day);
		SimpleDateFormat df = new SimpleDateFormat(Constants.dfyyyyMMddHHmmss);
		return df.format(date);

	}

	public static Date calDate(int fscMon, int fscYr) {
		SimpleDateFormat df = new SimpleDateFormat(Constants.dfyyyyMMddHHmmss);
		Calendar cal = Calendar.getInstance();
		int ind = ((fscYr * 12) + fscMon) - 4;
		// Date day = new Date(ind / 12, (ind % 12) + 1, 1);
		cal.set(ind / 12, (ind % 12) + 1, 1, 0, 0, 0);
		Date day = cal.getTime();
		try {
			day = df.parse(df.format(day));
		} catch (ParseException e) {
			System.out.println("Date Convert Error:" + e.getMessage());
		}
		return day;
	}

	public static String getClassStr(String packageClass) {
		int lastI = packageClass.lastIndexOf('.');
		String rstr = packageClass.substring(lastI + 1);
		int r = rstr.indexOf("ServiceImpl");
		if (r > 0) {
			rstr = rstr.substring(0, rstr.length() - 4);
		}
		r = rstr.indexOf("Service");
		if (r > 0) {
			rstr = rstr.substring(0, rstr.length() - 7);
		}
		return rstr;
		// return null;
	}

	/**
	 * 
	 * @Title: dateDifferent
	 * @Description: 两个date 比较时间相差几天
	 * @param @param day1
	 * @param @param day2
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int dateDifferent(Date day1, Date day2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day1);
		Calendar initialCal = Calendar.getInstance();
		initialCal.setTime(day2);
		long longDate = cal.getTimeInMillis() - initialCal.getTimeInMillis();
		// 计算相隔天数
		int qtyDay = new Long(longDate / (1000 * 60 * 60 * 24)).intValue();
		return qtyDay;

	}

	/**
	 * 
	 * @Title: fiscalMonth
	 * @Description: 当前日期是财月的第几周
	 * @param @return
	 * @param @throws GenericException
	 * @return int
	 * @throws
	 */
	public static Boolean isFiscalMonthOfWeek(Date day) {
		int lastweek = 0;
		Boolean flag = false;
		Calendar cal = Calendar.getInstance();
		// 设置初始财年，2014-11-03 为财年的第一天
		cal.set(2014, 10, 3, 0, 0, 0);
		int qtyDay = dateDifferent(day, cal.getTime());
		// 多少个星期
		int week = (qtyDay / 7) + 1;
		// 13周一个循环
		lastweek = week % 13;

		switch (lastweek) {
		case 0:
		case 4:
		case 8:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		return flag;
	}

	/**
	 * 
	 * @Title: getDateOfWeekDay
	 * @Description: 是否为周一到周五，是 true
	 * @param @return
	 * @return Boolean
	 * @throws
	 */
	public static Boolean isWorkDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if ((dayOfWeek > 1) && (dayOfWeek < 7)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: oneHourFromAndTo
	 * @Description: 当前时间小时前from和当前时间to
	 * @param @return
	 * @return Map<String,Date>
	 * @throws
	 */
	public static Date getOneHourBack(Date currentDate) {
		// one hour
		// Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - 60);

		return cal.getTime();
	}

	public static Date stringToDate(String dateStr, String fmtStr) throws Exception {
		DateFormat df = new SimpleDateFormat(fmtStr);
		Date date = df.parse(dateStr);
		return date;

	}

	public static Date strToDate(String str) throws GenericException {
		DateFormat df = new SimpleDateFormat(Constants.dfyyyyMMddHHmmss);
		String yyyy = str.substring(0, 4);
		String MM = str.substring(4, 6);
		String day = yyyy + "-" + MM + "-01 00:00:00";
		Date date;
		try {
			date = df.parse(day);
		} catch (Exception e) {
			throw new GenericException(Const.ERR_CODE_SOR_01, "Date Convert Error" + str, e);
		}
		return date;
	}

	public static String strToDateStr(String str) throws GenericException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = strToDate(str);
		String day = "";
		try {
			day = df.format(date);
		} catch (Exception e) {
			throw new GenericException(Const.ERR_CODE_SOR_01, "Date Convert Error" + str, e);
		}
		return day;
	}

	/**
	 * 设置查询时使用的page信息
	 * 
	 * @param allCount
	 * @param page
	 * @return
	 */
	public static Page setPageInfo(int allCount, Page page) {
		if (page != null) {
			boolean b = ((allCount % page.getPageSize()) != 0);
			if (allCount == 0) {
				b = true;
			}
			int pageCount = (allCount / page.getPageSize()) + (b ? 1 : 0);
			if (page.getPageNo() > pageCount) {
				page.setPageNo(pageCount);
			}
		} else {
			page = new Page();
			page.setPageNo(1);
			page.setPageSize(10);
		}
		return page;
	}

	/**
	 * 设置显示用的page信息进入jsonobject
	 * 
	 * @param page
	 * @param allCount
	 * @param jo
	 */
	public static void setJO(Page page, int allCount, JSONObject jo) {
		// ajax请求时，界面根据下面3个值设置分页器的显示
		// 初期表示一页10件
		int pageSize = 10;
		int pageNo = 1;
		if (page != null) {
			pageSize = page.getPageSize();
			pageNo = page.getPageNo();
		}

		int curSize = allCount % pageSize;
		boolean b = (curSize > 0);
		int pageCount = (allCount / pageSize) + (b ? 1 : 0);
		curSize = b ? curSize : pageSize;
		pageCount = (pageCount == 0) ? 1 : pageCount;

		jo.accumulate("pageno", pageNo);
		jo.accumulate("pagesize", pageSize);
		jo.accumulate("pagecount", pageCount);
		jo.accumulate("resultcount", allCount);
		jo.accumulate("curSize", curSize);

	}

	public static int monthdiff(Date day1, Date day2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day1);
		int yr1 = cal.get(Calendar.YEAR);
		int mon1 = cal.get(Calendar.MONTH);
		cal.setTime(day2);
		int yr2 = cal.get(Calendar.YEAR);
		int mon2 = cal.get(Calendar.MONTH);
		int diff = ((yr1 * 12) + mon1) - ((yr2 * 12) + mon2);
		return diff;
	}

	/**
	 * 
	 * @Title: getFscmonWeekFri
	 * @Description: 传一个时间，转换成财月结算周的周五
	 * @param day
	 * @return Calendar
	 * @throws
	 */
	public static Calendar getFscmonWeekFri(Calendar day) {
		day.set(day.get(Calendar.YEAR), day.get(Calendar.MONTH) + 1, 1);
		day.add(Calendar.DAY_OF_MONTH, -1);
		if (isFiscalMonthOfWeek(day.getTime())) {
			int week = day.get(Calendar.DAY_OF_WEEK);
			if (week == 1) {
				day.add(Calendar.DAY_OF_MONTH, -7);
				day.set(Calendar.DAY_OF_WEEK, 6);
			} else {
				day.set(Calendar.DAY_OF_WEEK, 6);
			}
		} else {
			day.add(Calendar.DAY_OF_MONTH, -7);
			day.set(Calendar.DAY_OF_WEEK, 6);
		}
		return day;
	}

	/**
	 * 
	 * @Title: calStrDate
	 * @Description: 根据财年财月推算当月15号日期
	 * @param fscMon
	 * @param fscYr
	 * @return String
	 * @throws
	 */
	public static String calStrDate(int fscMon, int fscYr) {
		Calendar cal = Calendar.getInstance();
		int ind = ((fscYr * 12) + fscMon) - 4;
		cal.set(ind / 12, (ind % 12) + 1, 15, 0, 0, 0);
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		return df.format(cal.getTime());
	}
}
