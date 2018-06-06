package com.cimr.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.util.JSON;

public class MapResultUtils {
	
	
	private static final Logger log = LoggerFactory.getLogger(MapResultUtils.class);


	private static Map<String,Object> parseResultMapIncludeFields(Map<String,Object> map,String[] fields) {
		Map<String,Object> res = new HashMap<>();
		Iterator<String> iterator = map.keySet().iterator();
		String fieldi;
		while(iterator.hasNext()) {
			fieldi = iterator.next();
			if(fieldi==null) {
				break;
			}
			int index = 0;
			for(String field : fields) {
				if(fieldi.equals(field)) {
					res.put(fieldi, map.get(field));
					break;
				}
				index++;
			}
//			//字段数据库中不存在
//			if(index==fields.length) {
//				res.put(fieldi, null);
//			}
		}
		return res;
	}
	
	private static Map<String,Object> parseResultMapExcludeFields(Map<String,Object> map,String[] fields) {
		Map<String,Object> res = new HashMap<>();
		res.putAll(map);
		Iterator<String> iterator = map.keySet().iterator();
		String fieldi;
		while(iterator.hasNext()) {
			fieldi = iterator.next();
			if(fieldi==null) {
				break;
			}
			for(String field : fields) {
				if(fieldi.equals(field)) {
					res.remove(fieldi);
					break;
				}
				
			}
			
		}
		return res;
	}
	
	/**
	 * 
	 * @param map
	 * @param fields
	 * @param type
	 * @return
	 */
	public static Map<String,Object> getList(Map<String,Object> map,String[] fields,int type){
		Map<String,Object> res = null ;
		if(fields!=null && fields.length>0) {
			//表示只查询给的字段
			if(type>0) {
				res = parseResultMapIncludeFields(map,fields);
			}
			//表示过滤给定的字段
			if(type<0) {
				res = parseResultMapExcludeFields(map,fields);
    		}
		}
		if(res==null) {
			return map;
		}
		return res;
	}
	
	/**
	 * 过滤查询字段
	 * @param map
	 * @param fields
	 * @param typeStr
	 * @return
	 */
	public static Map<String,Object> getList(Map<String,Object> map,String[] fields,String typeStr){
		int type = 0;
		if("INCLUDE".equals(typeStr)) {
			type = 1;
		}
		if("EXCLUDE".equals(typeStr)) {
			type = -1;
		}
		return getList(map,fields,type);
	}
	
	
	/**
	 * 统计给定或排除的boolean类型字段的数量
	 * @param mapIn
	 * @param type
	 * @param fields
	 * @return
	 */
	public static Map<String,Object> parseBooleanCount(Map<String,Object> mapIn,String type,String[] fields){
		Map<String,Object> map = new HashMap<>();
		map.putAll(mapIn);
		Iterator<String> iterator = map.keySet().iterator();
		int countTrue = 0;
		int countFalse = 0;
		String key;
		while(iterator.hasNext()) {
			key = iterator.next();
			Boolean b;
			if(map.get(key) instanceof Boolean 
					&& needCountBoolean(type,fields,key)) {
				b = (Boolean)map.get(key);
				iterator.remove();
				if(b) {
					countTrue++;
				}else {
					countFalse++;
				}
			}
		}
		map.put("countTrue", countTrue);
		map.put("countFalse", countFalse);
		return map;
	}
	
	/**
	 * 判断key字段是否需要统计
	 * @param type
	 * @param fields
	 * @param key
	 * @return true 需要统计 false 不需要统计
	 */
	private static boolean needCountBoolean(String type,String[] fields,String key) {
		if("INCLUDE".equals(type)) {
			if(fields!=null && fields.length>0) {
				for(String field:fields) {
					if(key.equals(field)) {
						return true;
					}
				}
				return false;
			}else {//不统计任何字段
				return false;
			}
			
		}else {//默认为排除字段
			if(fields!=null && fields.length>0) {
				for(String field:fields) {
					if(key.equals(field)) {//需要排除
						return false;
					}
				}
				return true;
			}else {//无需排除任何字段
				return true;
			}
		}
	}
	
	
	/**
	 * 处理redis查询到的数据
	 * 支持map 以及json字符串
	 * @param obj
	 * @return 数据为空或数据类型不支持返回null 
	 * 其他返回map对象 对应redis 的key-value
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> parseRedisObject(Object obj) {
		
		Map<String,Object> out =null;
		if(obj==null) {
			log.info("查询数据为空");
			return out;
		}
		if(obj instanceof Map) {
			out = (Map<String,Object>) obj;
		}else if(obj instanceof String) {
			out = (Map<String, Object>) JSON.parse((String)obj);
			
		}else {
			log.error("暂时不支持除String 以及 map 类型外的redis对象");
		}
		
		return out;
	}
}
