package gprs.tool.dateUitle;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @desc：
 * @author: CJ
 * @time: 2017年3月22日 下午2:53:33
 * @ver: 1.0.0
 */
public class DateUtils {
		public static Date date = null;
	
		public static DateFormat dateFormat = null;
		/**
		 * 功能描述：格式化输出日期
		 * 
		 * @param date
		 *            Date 日期
		 * @param format
		 *            String 格式
		 * @return 返回字符型日期
		 */
		public static String format(Date date, String format) {
			String result = "";
			try {
				if (date != null) {
					dateFormat = new SimpleDateFormat(format);
					result = dateFormat.format(date);
				}
			} catch (Exception e) {
			}
			return result;
		}
		
		/**
		 * 功能描述：格式化输出日期
		 * 
		 * @param String
		 *            Date 日期
		 * @param format
		 *            String 格式
		 * @return 返回Date日期
		 */
		public static Date format(String date, String format) {
			Date result = null;
			try {
				if (date!= null) {
					dateFormat = new SimpleDateFormat(format);
					result = dateFormat.parse(date);
				}
			} catch (Exception e) {
			}
			return result;
		}
		/**
		 * 功能描述：返回字符型日期时间
		 * @author chenhui
		 * @param date
		 *            Date 日期
		 * @return 返回字符型日期时间  格式自定义
		 */
		public static String getOneDateTime(Date date,String mode) {
			return format(date, mode);
		}
		
	    //获取当天的开始时间
	    public static Date getDayBegin() {
	       Calendar cal = new GregorianCalendar();
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        return cal.getTime();
	    }
	    //获取当天的结束时间
	    public static Date getDayEnd() {
	        Calendar cal = new GregorianCalendar();
	        cal.set(Calendar.HOUR_OF_DAY, 23);
	        cal.set(Calendar.MINUTE, 59);
	        cal.set(Calendar.SECOND, 59);
	        return cal.getTime();
	    }
	    //获取昨天的开始时间
	    public static Date getBeginDayOfYesterday() {
	        Calendar cal = new GregorianCalendar();
	        cal.setTime(getDayBegin());
	        cal.add(Calendar.DAY_OF_MONTH, -1);
	        return cal.getTime();
	    }
	    //获取昨天的结束时间
	    public static Date getEndDayOfYesterDay() {
	        Calendar cal = new GregorianCalendar();
	        cal.setTime(getDayEnd());
	        cal.add(Calendar.DAY_OF_MONTH, -1);
	        return cal.getTime();
	    }
	    //获取明天的开始时间
	    public static Date getBeginDayOfTomorrow() {
	        Calendar cal = new GregorianCalendar();
	        cal.setTime(getDayBegin());
	        cal.add(Calendar.DAY_OF_MONTH, 1);

	        return cal.getTime();
	    }
	    //获取明天的结束时间
	    public static Date getEndDayOfTomorrow() {
	        Calendar cal = new GregorianCalendar();
	        cal.setTime(getDayEnd());
	        cal.add(Calendar.DAY_OF_MONTH, 1);
	        return cal.getTime();
	    }
	    
	    //获取昨天的现在时间
	    public static String getYesterday(){
	    	Date Yesterday = new Date(new Date().getTime()-24*60*60*1000);
	    	SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    	String time = matter1.format(Yesterday);
	    	return time;
	    }
	    
	    //获取一小时前的时间
        public static String getLastHour(){
            Date lastHour = new Date(new Date().getTime()-60*60*1000);
            SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String time = matter1.format(lastHour);
            return time;
        }
	    
	    //获取本周的开始时间
	    public static Date getBeginDayOfWeek() {
	        Date date = new Date();
	        if (date == null) {
	            return null;
	        }
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
	        if (dayofweek == 1) {
	            dayofweek += 7;
	        }
	        cal.add(Calendar.DATE, 2 - dayofweek);
	        return getDayStartTime(cal.getTime());
	    }
	    //获取本周的结束时间
	    public static Date getEndDayOfWeek(){
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(getBeginDayOfWeek());  
	        cal.add(Calendar.DAY_OF_WEEK, 6); 
	        Date weekEndSta = cal.getTime();
	        return getDayEndTime(weekEndSta);
	    }
	    //获取本月的开始时间
	     public static Date getBeginDayOfMonth() {
	            Calendar calendar = Calendar.getInstance();
	            calendar.set(getNowYear(), getNowMonth() - 1, 1);
	            return getDayStartTime(calendar.getTime());
	        }
	    //获取本月的结束时间
	     public static Date getEndDayOfMonth() {
	            Calendar calendar = Calendar.getInstance();
	            calendar.set(getNowYear(), getNowMonth() - 1, 1);
	            int day = calendar.getActualMaximum(5);
	            calendar.set(getNowYear(), getNowMonth() - 1, day);
	            return getDayEndTime(calendar.getTime());
	        }
	     //获取本年的开始时间
	     public static java.util.Date getBeginDayOfYear() {
	            Calendar cal = Calendar.getInstance();
	            cal.set(Calendar.YEAR, getNowYear());
	            // cal.set
	            cal.set(Calendar.MONTH, Calendar.JANUARY);
	            cal.set(Calendar.DATE, 1);

	            return getDayStartTime(cal.getTime());
	        }
	     //获取本年的结束时间
	     public static java.util.Date getEndDayOfYear() {
	            Calendar cal = Calendar.getInstance();
	            cal.set(Calendar.YEAR, getNowYear());
	            cal.set(Calendar.MONTH, Calendar.DECEMBER);
	            cal.set(Calendar.DATE, 31);
	            return getDayEndTime(cal.getTime());
	        }
	    //获取某个日期的开始时间
	    public static Timestamp getDayStartTime(Date d) {
	        Calendar calendar = Calendar.getInstance();
	        if(null != d) calendar.setTime(d);
	        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
	        calendar.set(Calendar.MILLISECOND, 0);
	        return new Timestamp(calendar.getTimeInMillis());
	    }
	    //获取某个日期的结束时间
	    public static Timestamp getDayEndTime(Date d) {
	        Calendar calendar = Calendar.getInstance();
	        if(null != d) calendar.setTime(d);
	        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
	        calendar.set(Calendar.MILLISECOND, 999);
	        return new Timestamp(calendar.getTimeInMillis());
	    }
	    //获取今年是哪一年
	     public static Integer getNowYear() {
	             Date date = new Date();
	            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
	            gc.setTime(date);
	            return Integer.valueOf(gc.get(1));
	        }
	     //获取本月是哪一月
	     public static int getNowMonth() {
	             Date date = new Date();
	            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
	            gc.setTime(date);
	            return gc.get(2) + 1;
	        }
	     //两个日期相减得到的天数
	     public static int getDiffDays(Date beginDate, Date endDate) {

	            if (beginDate == null || endDate == null) {
	                throw new IllegalArgumentException("getDiffDays param is null!");
	            }

	            long diff = (endDate.getTime() - beginDate.getTime())
	                    / (1000 * 60 * 60 * 24);

	            int days = new Long(diff).intValue();

	            return days;
	        }
	    //两个日期相减得到的毫秒数
	     public static long dateDiff(Date beginDate, Date endDate) {
	            long date1ms = beginDate.getTime();
	            long date2ms = endDate.getTime();
	            return date2ms - date1ms;
	        }
	     //两个日期相减得到的分钟数(保留一位)
	     public static String datehour(Date beginDate, Date endDate) {
	    	 long date1ms = beginDate.getTime();
	    	 long date2ms = endDate.getTime();
	    	 long haomiao =  date2ms - date1ms;
	    	 System.out.println("开始时间1==="+beginDate);
	    	 System.out.println("结束时间1==="+endDate);
	    	 System.out.println("开始时间==="+date1ms);
	    	 System.out.println("结束时间==="+date2ms);
	    	 System.out.println("毫秒===="+haomiao);
	    	 double hour = ((double)haomiao)/(1000*60);
	    	 System.out.println("分钟==="+Math.ceil(hour));
	    	 int hour1 = (int)Math.ceil(hour);
	    	 return hour1+"";
	     }
	     //获取两个日期中的最大日期
	     public static Date max(Date beginDate, Date endDate) {
	            if (beginDate == null) {
	                return endDate;
	            }
	            if (endDate == null) {
	                return beginDate;
	            }
	            if (beginDate.after(endDate)) {
	                return beginDate;
	            }
	            return endDate;
	        }
	     //获取两个日期中的最小日期
	     public static Date min(Date beginDate, Date endDate) {
	            if (beginDate == null) {
	                return endDate;
	            }
	            if (endDate == null) {
	                return beginDate;
	            }
	            if (beginDate.after(endDate)) {
	                return endDate;
	            }
	            return beginDate;
	        }
	     //返回某月该季度的第一个月第一天
	     public static Date getFirstSeasonDate(Date date) {
	             final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	            Calendar cal = Calendar.getInstance();
	            cal.setTime(date);
	            int sean = SEASON[cal.get(Calendar.MONTH)];
	            cal.set(Calendar.MONTH, sean * 3 - 3);
	            cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	            return cal.getTime();
	        }
	     
	     //返回当前时间未第几季度
	     public static int getQuarterDate(Date date) {
	    	 final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	    	 Calendar cal = Calendar.getInstance();
	    	 cal.setTime(date);
	    	 return SEASON[cal.get(Calendar.MONTH)];
	     }
	     
	     //返回某月该季度的最后个月最后一天
	     public static Date getEndSeasonDate(Date date) {
	             final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	            Calendar cal = Calendar.getInstance();
	            cal.setTime(date);
	            int sean = SEASON[cal.get(Calendar.MONTH)-1];
	            cal.set(Calendar.MONTH,sean * 3-1);
	            cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	            return cal.getTime();
	        }
	     
	     //返回某个日期下几天的日期
	     public static Date getNextDay(Date date, int i) {
	            Calendar cal = new GregorianCalendar();
	            cal.setTime(date);
	            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
	            return cal.getTime();
	        }
	     //返回某个日期前几天的日期
	     public static Date getFrontDay(Date date, int i) {
	            Calendar cal = new GregorianCalendar();
	            cal.setTime(date);
	            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
	            return cal.getTime();
	        }
	     //获取某年某月到某年某月按天的切片日期集合（间隔天数的集合）
	     public static List getTimeList(int beginYear, int beginMonth, int endYear,
	                int endMonth, int k) {
	            List list = new ArrayList();
	            if (beginYear == endYear) {
	                for (int j = beginMonth; j <= endMonth; j++) {
	                    list.add(getTimeList(beginYear, j, k));

	                }
	            } else {
	                {
	                    for (int j = beginMonth; j < 12; j++) {
	                        list.add(getTimeList(beginYear, j, k));
	                    }

	                    for (int i = beginYear + 1; i < endYear; i++) {
	                        for (int j = 0; j < 12; j++) {
	                            list.add(getTimeList(i, j, k));
	                        }
	                    }
	                    for (int j = 0; j <= endMonth; j++) {
	                        list.add(getTimeList(endYear, j, k));
	                    }
	                }
	            }
	            return list;
	        }
	     //获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
	     public static List getTimeList(int beginYear, int beginMonth, int k) {
	            List list = new ArrayList();
	            Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
	            int max = begincal.getActualMaximum(Calendar.DATE);
	            for (int i = 1; i < max; i = i + k) {
	                list.add(begincal.getTime());
	                begincal.add(Calendar.DATE, k);
	            }
	            begincal = new GregorianCalendar(beginYear, beginMonth, max);
	            list.add(begincal.getTime());
	            return list;
	        }
	     //获取某月的开始时间
	     public static Date getBeginDayOfCertainMonth(int month) {
	            Calendar calendar = Calendar.getInstance();
	            calendar.set(getNowYear(), month - 1, 1);
	            return getDayStartTime(calendar.getTime());
	        }
	    //获取某月的结束时间
	     public static Date getEndDayOfCertainMonth(int month) {
	            Calendar calendar = Calendar.getInstance();
	            calendar.set(getNowYear(), month - 1, 1);
	            int day = calendar.getActualMaximum(5);
	            calendar.set(getNowYear(), month - 1, day);
	            return getDayEndTime(calendar.getTime());
	        }
	     
	     /**
	 	 * 格式化当前时间 "yyyy-MM-dd HH:mm:ss"
	 	 * @return
	 	 */
	 	public static String nowDateTimeSS(){
	 		return nowDateTimeSS(new Date());
	 	}

	 	/**
	 	 * 格式化目标时间 "yyyy-MM-dd HH:mm:ss"
	 	 * @param date 日期
	 	 * @return
	 	 */
	 	public static String nowDateTimeSS(Date date){
	 		return formatDateByFormat(date,"yyyy-MM-dd HH:mm:ss");
	 	}
	 	/**
	 	 * 格式化目标时间 "yyyy"
	 	 * @param date 日期
	 	 * @return
	 	 */
	 	public static String nowDateTimeYear(Date date){
	 		return formatDateByFormat(date,"yyyy");
	 	}
	 	/**
	 	 * 格式化目标时间 "yyyy-MM"
	 	 * @param date 日期
	 	 * @return
	 	 */
	 	public static String nowDateTimeMouth(Date date){
	 		return formatDateByFormat(date,"yyyy-MM");
	 	}
	 	/**
	 	 * 格式化目标时间 "yyyyMMdd"
	 	 * @param date 日期
	 	 * @return
	 	 */
	 	public static String nowDateTimeday1(Date date){
	 		return formatDateByFormat(date,"yyyyMMdd");
	 	}
	 	/**
	 	 * 格式化目标时间 "yyyy-MM"
	 	 * @param date 日期
	 	 * @return
	 	 */
	 	public static String nowMonth(Date date){
	 		return formatDateByFormat(date,"yyyy-MM");
	 	}
	 	/**
	 	 * 格式化目标时间 "yyyy"
	 	 * @param date 日期
	 	 * @return
	 	 */
	 	public static String nowYear(Date date){
	 		return formatDateByFormat(date,"yyyy");
	 	}
	 	/**
	 	 * 格式化目标时间 "yyyy-MM-DD"
	 	 * @param date 日期
	 	 * @return
	 	 */
	 	public static String nowDateTimeday2(Date date){
	 		return formatDateByFormat(date,"yyyy-MM-dd");
	 	}
	 	/**
		 * 功能描述：以指定的格式来格式化日期
		 * 
		 * @param date
		 *            Date 日期
		 * @param format
		 *            String 格式
		 * @return String 日期字符串
		 */
		public static String formatDateByFormat(Date date, String format) {
			String result = "";
			if (date != null) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					result = sdf.format(date);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
	        return result;
		}
		/**
		 * 
		 * @param date2
		 * @param longtime
		 * @return
		 */
		public static String addhour(Date date2, double longtime) {
			long date = date2.getTime();
			long time = (long) (date+(longtime*(60*60*1000)));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(time);
			String t = formatter.format(calendar.getTime());
			return t;
		}
		
		/**
		 * 返回一年的12个月
		 * @param year xxxx
		 * @return
		 */
		public static List<String> getMonthtel(String time) {
			String year = time.substring(0,4);
			List<String> times = new ArrayList<>();
			for(int i=1;i<13;i++){
				String xx;
				if(i<10){
					xx = year+"-0"+i;
				}else{
					xx=year+"-"+i;
				}
				times.add(xx);
			}
			return times;
		}
	 	
		/**
		 * 过去一个月的时间
		 * @return
		 */
		public static String getUpperMonth(){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Calendar c = Calendar.getInstance();
	        c.setTime(new Date());
	        c.add(Calendar.MONTH, -1);
	        Date m = c.getTime();
	        String mon = format.format(m);
	        System.out.println("过去一个月："+mon);
	        return mon;
	         
		} 
		
}
