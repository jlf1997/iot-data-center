package com.cimr.api.code.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.cimr.api.comm.model.TerimalModel;
import com.cimr.boot.redis.RedisTemplateConfig;
import com.cimr.util.MapResultUtils;
import com.mongodb.util.JSON;


public class NormalRedisDao {
	
    
  
private static final Logger log = LoggerFactory.getLogger(NormalRedisDao.class);

    
    
    public List<Map<String,Object>> getData(RedisTemplate<String, Object> redisTemplate,String dbName,List<TerimalModel> termimals,int type, String... fields) {
		// TODO Auto-generated method stub
    	
		List<Map<String,Object>> res = new ArrayList<>();
		Object obj;
    	for(TerimalModel ter:termimals) {
    		obj  = redisTemplate.opsForValue().get(dbName+":"+ter.getTerId());
    		Map<String,Object> out =MapResultUtils.parseRedisObject(obj);
    		
    		if(out!=null) {
    			out = MapResultUtils.getList(out, fields, type);
    			res.add(out);
    		}
    	}    	
    	
    	return res;
	} 
    
    public List<Map<String,Object>> getData(RedisTemplate<String, Object> redisTemplate,String dbName,List<TerimalModel> termimals,String type, String... fields) {
		// TODO Auto-generated method stub
    	
		List<Map<String,Object>> res = new ArrayList<>();
		Object obj;
    	for(TerimalModel ter:termimals) {
    		obj  = redisTemplate.opsForValue().get(dbName+":"+ter.getTerId());
    		Map<String,Object> out =MapResultUtils.parseRedisObject(obj);
    		
    		if(out!=null) {
    			out = MapResultUtils.getList(out, fields, type);
    			res.add(out);
    		}
    	}    	
    	
    	return res;
	}
    
    public List<Map<String, Object>> getDateBoolean(RedisTemplate<String, Object> redisTemplate,String dbName,List<TerimalModel> termimals,  String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> res = new ArrayList<>();
		Object obj;
    	for(TerimalModel ter:termimals) {
    		obj = redisTemplate.opsForValue().get(dbName+":"+ter.getTerId());
    		Map<String,Object> out =MapResultUtils.parseRedisObject(obj);
    		if(out!=null) {
    			out = MapResultUtils.parseBooleanCount(out, countIncludeType, countFields);
        		out = MapResultUtils.getList(out, fields, includeType);
    			res.add(out);
    		}
    	}    	
    	
    	return res;
	}
}
