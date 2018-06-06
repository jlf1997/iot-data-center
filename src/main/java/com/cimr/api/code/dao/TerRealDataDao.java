package com.cimr.api.code.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cimr.api.code.config.RedisProperties;
import com.cimr.api.code.util.RedisTemplateFactory;
import com.cimr.api.comm.model.TerimalModel;
import com.cimr.boot.redis.RedisTemplateConfig;


@Repository
public class TerRealDataDao {
	
	
	
	/**
	 * redis 查询工具
	 */

	 private NormalRedisDao normalRedisDao;
    

	
	@Autowired
	private RedisProperties redisProperties;
	
	@Autowired
	protected RedisTemplateConfig redisTemplateConfig;
	
	@Resource(name="fastJsonredisTemplate")
	private RedisTemplate<String, Object> redisTemplate ;
	
	  @PostConstruct
	    public void postConstruct() {
		  normalRedisDao = new NormalRedisDao();
			
	    }
	
	



	/**
	 * 
	 * @param termimals
	 * @param signal
	 * @param fields
	 * @return
	 */
	public List<Map<String,Object>> getData(List<TerimalModel> termimals, String signal,int type, String... fields) {
//		 redisTemplate =
//				 redisTemplateConfig.getFastJsonStringTemplate(redisProperties.getNewdataIndex(),Object.class);
		 redisTemplate = RedisTemplateFactory.changeDataBase(redisTemplate, redisProperties.getNewdataIndex());
		return normalRedisDao.getData(redisTemplate,RedisProperties.NEW_DATA+signal, termimals, type, fields);
	}
	
	
	public List<Map<String,Object>> getData(List<TerimalModel> termimals, String signal,String type, String... fields) {
//		 redisTemplate =
//				 redisTemplateConfig.getFastJsonStringTemplate(redisProperties.getNewdataIndex(),Object.class);
		 redisTemplate = RedisTemplateFactory.changeDataBase(redisTemplate, redisProperties.getNewdataIndex());
		return normalRedisDao.getData(redisTemplate,RedisProperties.NEW_DATA+signal, termimals, type, fields);
	}


	
	/**
	 * 
	 * @param termimals
	 * @param signal
	 * @param includeType
	 * @param fields
	 * @param countIncludeType
	 * @param countFields
	 * @return
	 */
	public List<Map<String, Object>> getDataBoolean(List<TerimalModel> termimals, String signal, String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
//		 redisTemplate =
//				 redisTemplateConfig.getFastJsonStringTemplate(redisProperties.getNewdataIndex(),Object.class);
//    	
		 redisTemplate = RedisTemplateFactory.changeDataBase(redisTemplate, redisProperties.getNewdataIndex());
		 return normalRedisDao.getDateBoolean(redisTemplate,RedisProperties.NEW_DATA+signal, termimals, includeType, fields, countIncludeType, countFields);
	}
	
	
	
	
}
