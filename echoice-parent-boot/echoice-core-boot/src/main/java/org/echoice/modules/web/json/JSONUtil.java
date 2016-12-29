package org.echoice.modules.web.json;

import java.sql.Timestamp;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class JSONUtil {
	private static SerializeConfig mapping = new SerializeConfig();
	public final static String DEFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer(DEFAULT_DATE_FORMAT));
        mapping.put(Timestamp.class, new SimpleDateFormatSerializer(DEFAULT_DATE_FORMAT));
    }
    /**
     * 日期格式yyyy-MM-dd HH:mm
     * @param obj
     * @return
     */
    public static String toJSONString(Object obj){
    	return toJSONString(obj, DEFAULT_DATE_FORMAT);
    }
    
    public static String toJSONString(Object obj,String dateFormat){
    	return JSON.toJSONStringWithDateFormat(obj, dateFormat);
    }
}
