package org.echoice.modules.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {
	/*必填*/
	public static final String _REQUIRE =".+";
	/*电子邮件*/
	public static final String _EMAIL ="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	/*电话*/
	public static final String _Phone ="^((\\(\\d{2,3}\\))|(\\d{3}-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(-\\d{1,4})?$";
	/*手机*/
	public static final String Mobile ="^((\\(\\d{2,3}\\))|(\\d{3}\\-))?13\\d{9}$";
	/*URL地址*/
	public static final String _Url ="^http://[A-Za-z0-9]+\\.[A-Za-z0-9]+[/=?%-&_~`@[\\]\':+!]*([^<>\"\"])*$";
	/*货币*/
	public static final String _CURRENCY ="^\\d+(\\.\\d+)?$";
	/*数字*/
	public static final String _NUMBER ="^\\d+$";
	/*邮编*/
	public static final String _ZIP ="^[1-9]\\d{5}$";
	/*整型*/
	public static final String _INTEGER ="^[-\\+]?\\d+$";
	/*双精度型*/
	public static final String _DOUBLE ="^[-\\+]?\\d+(\\.\\d+)?$";
	/*英文*/
	public static final String _ENGLISH ="^[A-Za-z]+$";
	/*中文*/
	public static final String _CHINESE ="/^[\u0391-\uFFE5]+$/";
	/*用户名*/
	public static final String _USERNAME="^[a-z]\\w{3,}$";
	/*日期格式*/
	public static final String _DATE_FORMAT_YMD="^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$";
	
	public static final String _DATE_FORMAT_DMY="^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$";
	
	public static boolean validatorRequire(String value){
		return validatorRegx(_REQUIRE,value);
	}
	
	public static boolean validatorEmail(String value){
		return validatorRegx(_EMAIL,value);
	}
	
	public static boolean validatorPhone(String value){
		return validatorRegx(_Phone,value);
	}
	
	public static boolean validatorMobile(String value){
		return validatorRegx(Mobile,value);
	}
	
	public static boolean validatorNumber(String value){
		return validatorRegx(_NUMBER,value);
	}
	
	public static boolean validatorCurency(String value){
		return validatorRegx(_CURRENCY,value);
	}
	
	public static boolean validatorChinese(String value){
		return validatorRegx(_CHINESE,value);
	}
	
	public static boolean validatorInteger(String value){
		return validatorRegx(_INTEGER,value);
	}
	
	public static boolean validatorEnglish(String value){
		return validatorRegx(_ENGLISH,value);
	}
	
	public static boolean validatorUserName(String value){
		return validatorRegx(_USERNAME,value);
	}
	/**
	 * 验证日期格式(ymd dmy)
	 * @param value
	 * @return
	 */
	public static boolean validatorIsDate(String format,String value){
		return validatorRegx(format,value);
	}
	
	/**
	 * 判断字符串长度
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean validatorLimit(String value,int min,int max){
		if(validatorRequire(value)){
			int length=value.length();
			if(length>=min&&length<=max){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断数字取范围
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean validatorRange(String value,int min,int max){
		if(validatorRequire(value)){
			int valueInt=Integer.parseInt(value);
			if(valueInt>=min&&valueInt<=max){
				return true;
			}
		}
		return false;
	}
	public static boolean validatorRegx(String patternRegx,String value){
		Pattern pattern = Pattern.compile(patternRegx);
		Matcher matcher=pattern.matcher(value);
		return matcher.matches();
	}
}
