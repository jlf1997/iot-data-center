package com.cimr.api.code.service;

import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.RestTemplate;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.dao.CommandsDao;
import com.cimr.api.code.dao.DictDao;
import com.cimr.api.code.model.mgr.Commands;
import com.cimr.api.code.po.CodeResultNotiyObject;
import com.cimr.api.code.po.CodeSenderObject;

@Service
public class CommandsService {

	
	@Autowired
	private CommandsDao commandsDao;
	
	@Autowired
	private DictDao dictDao;
	
	@Autowired
	private CodeProperties codeProperties;
	
	@Autowired
	private KafkaTemplate<String,String> KafkaTemplate;
	
	@Resource(name="executorServiceForSendCodeByKafka")
	private ExecutorService executorService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	private static final Logger log = LoggerFactory.getLogger(CommandsService.class);

	
	
	/**
	 * 根据指令id获取指令内容 指令内容必须可查
	 * @param id
	 * @return
	 */
	public String getCommandsById(String id) {
		Commands t = new Commands();
		t.setId(id);
		t.setAppLicense(codeProperties.getAppLicenseCode());
		Commands res = commandsDao.find(t);
		if(res!=null) {
			return res.getContents();
		}		
		return null;
		
	}
	
	/**
	 * 通过kafka向中终端发送指令,并在发送结束后，反馈结果
	 * @param message
	 */
	public void sendCodeToTerminalByKafka(String message,CodeSenderObject codeSenderObject) {
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				ListenableFuture<SendResult<String, String>>  future = KafkaTemplate.send(codeProperties.getTopicAppToTer(),message);
				future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

		            @Override
		            public void onSuccess(SendResult<String, String> result) {
//		                log.info("send success {}", result.getProducerRecord().value());
		                
		                CodeResultNotiyObject codeResult = new CodeResultNotiyObject();
		                codeResult.setCodeId(codeSenderObject.getCodeId());
		                codeResult.setReturn_code("SUCCESS");
		                codeResult.setReturn_message("发送成功");
		                notiyCodeResutl(codeSenderObject.getNotify_url(),codeResult);
		            }

		            @Override
		            public void onFailure(Throwable ex) {
		                log.warn("send fail {}", ex.getMessage());
		                CodeResultNotiyObject codeResult = new CodeResultNotiyObject();
		                codeResult.setCodeId(codeSenderObject.getCodeId());
		                codeResult.setReturn_code("FAILD");
		                codeResult.setReturn_message("发送失败");
		                notiyCodeResutl(codeSenderObject.getNotify_url(),codeResult);
		            }

		        });
			}
			
		});
		

    
	}
	
	/**
	 * 通知指令发送结果
	 */
	public void notiyCodeResutl(String url,CodeResultNotiyObject result) {
		HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		log.info("通知url："+url);
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("return_code", result.getReturn_code());
        params.add("return_message", result.getReturn_message());
        params.add("codeId", result.getCodeId());
        HttpEntity entity = new HttpEntity( params,headers);
        ResponseEntity<String> rss =null;
        try {
        	rss =  restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }catch(Exception e) {
//        	e.printStackTrace();
        	log.error("连接失败:"+e.getMessage());
        	return ;
        }

        if(rss.getStatusCode()==HttpStatus.OK) {
        	if("success".equals(rss.getBody())){
        		log.info("处理成功");
        	}else {
        		log.error("处理失败");
        	}
        }else {
        	log.error("连接失败");
        }
	}
	
	
	
	
}
