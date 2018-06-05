package com.cimr.api.history.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class NormalMongodbService {

	public List<Map<String, Object>> findByCondition(String condition,String[] sortBy,String sortType){
		
		
		return null;
	}
	
	private Query  parseCondition(String condition) {
		Query query = new Query();
		
		
		return query;
	}
}
