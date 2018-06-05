package com.cimr.api.code.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.model.Terminal_1_Info;
import com.cimr.api.comm.model.TerimalModel;
import com.cimr.util.MapResultUtils;


@Repository
public class TerRealDataDao {
	
	
	
	/**
	 * redis 查询工具
	 */
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate<String, Map<String,Object>> redisTemplate ;
	
	
	
//	/**
//	 * 根据终端编号查询终端信息
//	 * @param termimals 终端编号列表
//	 * @return 终端信息列表
//	 */
//    @SuppressWarnings("rawtypes")
//	public List<HashMap> getInfosByTerIds(List<TerimalModel> termimals,String signal){
//    	List<HashMap> res = new ArrayList<>();
//    	for(TerimalModel ter:termimals) {
//    		HashMap out = redisTemplate.opsForValue().get(CodeProperties.NEW_DATA+signal+":"+ter.getTerId());
//    		if(out!=null) {
//    			res.add(out);
//    		}
//    	}    	
//    	return res;
//    }
//
//    
//    /**
//     * 
//     * 根据终端编号查询终端位置信息
//     * @param termimals 终端编号列表
//     * @return 终端位置信息列表
//     */
//    @SuppressWarnings("rawtypes")
//	public List<Terminal_1_Info> getLocationInfosByTerIds(List<TerimalModel> termimals,String signal) {
//		// TODO Auto-generated method stub
//		List<HashMap> res = new ArrayList<>();
//    	for(TerimalModel ter:termimals) {
//    		HashMap out = redisTemplate.opsForValue().get(CodeProperties.NEW_DATA+signal+":"+ter.getTerId());
//    		if(out!=null) {
//    			res.add(out);
//    		}
//    	}    	
//    	List<Terminal_1_Info> infos = new ArrayList<>();
//    	res.forEach(action->{
//    		infos.add(new Terminal_1_Info(action));
//    	});
//    	return infos;
//	}


	/**
	 * 
	 * @param termimals
	 * @param signal
	 * @param fields
	 * @return
	 */
	public List<Map<String,Object>> getDate(List<TerimalModel> termimals, String signal,int type, String... fields) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> res = new ArrayList<>();
    	for(TerimalModel ter:termimals) {
    		Map<String,Object> out = redisTemplate.opsForValue().get(CodeProperties.NEW_DATA+signal+":"+ter.getTerId());
    		out = MapResultUtils.getList(out, fields, type);
    		if(out!=null) {
    			res.add(out);
    		}
    	}    	
    	
    	return res;
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
	public List<Map<String, Object>> getDateBoolean(List<TerimalModel> termimals, String signal, String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> res = new ArrayList<>();
    	for(TerimalModel ter:termimals) {
    		Map<String,Object> out = redisTemplate.opsForValue().get(CodeProperties.NEW_DATA+signal+":"+ter.getTerId());
    		out = MapResultUtils.parseBooleanCount(out, countIncludeType, countFields);
    		out = MapResultUtils.getList(out, fields, includeType);
    		if(out!=null) {
    			res.add(out);
    		}
    	}    	
    	
    	return res;
	}
	
	
	
	
}
