package com.cimr.api.code.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.dao.TerRealDataDao;
import com.cimr.api.code.model.Terminal_1_Info;
import com.cimr.api.code.service.configs.MessageHandle;
import com.cimr.api.comm.model.TerimalModel;


@SuppressWarnings("rawtypes")
@Service
public class RealTimeDateService {
	
	private static final Logger log = LoggerFactory.getLogger(RealTimeDateService.class);

	
	
	
	@Autowired
	private TerRealDataDao terRealDataDao;
	
	@Autowired
	private  MessageHandle handle;
	
	
	
	private static ReentrantLock lock = new ReentrantLock();
	
	

	
	/**
	 * 定时处理收到的消息
	 * @throws Exception
	 */
	  @Scheduled(fixedRate = 5000)
	   private  void callback() throws Exception {
		  handle.hanleMessage();
	   }
	
    
    
      
	  /**
	   * 处理订阅消息，接受到消息后，对应更新数据，websocket推送的消息对应自动更新
	   * @param message
	   */
	  @KafkaListener(topics = {"${iot.code.topic-recive-real-data}"})
	  public void updateData(String message)  {
		   //更新数据
		  log.debug("开始接受kafka数据:"+message);
		  handle.saveMessage(message);
	  }
	  
	  


//
//	 /**
//	  * 根据终端id获取终端最新数据
//	  * 数据来源:redis 第二组
//	  * @param termimals
//	  * @return
//	  */
//	
//	public List<HashMap> getInfoByTerId(List<TerimalModel> termimals,String signal) {
//		return terRealDataDao.getInfosByTerIds(termimals,signal);
//	}
//
//
//
//
//	/**
//	 * 根据终端id获取终端最新位置数据
//	 * @param termimals
//	 * @return
//	 */
//	public List<Terminal_1_Info> getLocationInfoByTerId(List<TerimalModel> termimals,String signal) {
//		return terRealDataDao.getLocationInfosByTerIds(termimals,signal);
//	}
	  
	public List<Map<String,Object>> getAllDate(List<TerimalModel> termimals,String signal){
		return terRealDataDao.getDate(termimals, signal, 0);
	}
	
	public List<Map<String,Object>> getAllDateInclude(List<TerimalModel> termimals,String signal,String...fields){
		return terRealDataDao.getDate(termimals, signal, 1,fields);
	} 
	
	public List<Map<String,Object>> getAllDateExclude(List<TerimalModel> termimals,String signal,String...fields){
		return terRealDataDao.getDate(termimals, signal, -1,fields);
	}



	/**
	 * 
	 * @param termimals
	 * @param signal
	 * @param includeType
	 * @param includeType2
	 * @param countIncludeType
	 * @param countFields
	 * @return
	 */
	public List<Map<String, Object>> getDateBoolean(List<TerimalModel> termimals, String signal, String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
		// TODO Auto-generated method stub
		return terRealDataDao.getDateBoolean(termimals, signal, includeType, fields,countIncludeType,countFields);
	} 
	  
    
   
}
