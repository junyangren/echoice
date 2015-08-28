package org.echoice.modules.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期Util类
 */
public class DateUtil {
	/**
     * 时间格式yyyy-MM-dd HH:mm:ss
     */
    public final static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式yyyyMMddHHmmss
     */
    public final static String DP_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 时间格式yyyy-MM-dd HH:mm
     */
    public final static String DP_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";    
    /**
     * 时间格式yyyy-MM-dd
     */
    public final static String DP_YYYY_MM_DD_H = "yyyy-MM-dd";
    /**
     * 时间格式yyyy/MM/dd
     */
    public final static String DP_YYYY_MM_DD_X = "yyyy/MM/dd";

    /**
     * get the default date pattern
     */
    public static String getDefalutDatePattern() {
        return defaultDatePattern;
    }

    /**
     * returns the current date in the default format
     */
    public static String getToday() {
       Date today = new Date();
       return format(today);
    }

    /**
     * convert Date to String  in default format
     */
    public static String format(Date date) {
        return format(date, getDefalutDatePattern());
    }

    /**
     * convert Date to String in pattern
     */
    public static String format(Date date, String pattern) {
        String returnValue = null;
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * converts a String to a Date using the default Pattern
     */
    public static Date parse(String strDate){
    	return parse(strDate, getDefalutDatePattern());
    }

    /**
     * converts a String to a Date using the  pattern
     */
    public static Date parse(String strDate, String pattern){
    	try{
    		SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(strDate);
    	}catch (ParseException e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
        return null;
    }

    /**
     * 将源时間格式转换成目标时间格式
     */
    public static String parseConvert(String strDate, String srcPattern,String destPattern){
    	try{
    		 SimpleDateFormat df = new SimpleDateFormat(srcPattern);
    	        SimpleDateFormat destDf = new SimpleDateFormat(destPattern);
    	        Date srcDate=df.parse(strDate);
    	        return destDf.format(srcDate);
    	}catch (ParseException e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	return null;
    }
    /**
     * add some month from the date
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }
    /**
     * 将数值转换成时间类型
     * @param time
     * @return
     */
    public static Date parse(Long time){
    	if(time==null){
    		return null;
    	}
    	Timestamp timestamp=new Timestamp(time.longValue());
    	return timestamp;
    }
	/**
	 * 以时间来取得编号
	 * @return
	 */
	public static String getServial(){
		Date date = new Date();
		//String pattern="yyyyMMddHHmmss";
		SimpleDateFormat df = new SimpleDateFormat(getDefalutDatePattern());
		String str=df.format(date);		
		return str;		
	}
	
	public static long getMitime(String strDate,String pattern) throws ParseException{
		Date date=parse(strDate,pattern);
		//Calendar ca=GregorianCalendar.getInstance();
		//ca.setTime(date);
		date.getTime();
		return date.getTime();
	}
	public static void main(String[] args) {
		System.out.println(format(new Date(),"yyyy-MM-dd"));
	}
}
