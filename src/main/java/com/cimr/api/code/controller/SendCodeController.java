
package com.cimr.api.code.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.model.Message;
import com.cimr.api.code.service.CommandsService;
import com.cimr.api.code.service.RealTimeDateService;
import com.cimr.api.code.service.configs.MessageHandle;
import com.cimr.api.code.util.MessageUtil;
import com.cimr.api.comm.model.HttpResult;
import com.cimr.api.comm.model.TerimalModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api(description="指令据相关操作",tags= {"code"})
@RestController
@RequestMapping("/code")
public class SendCodeController {
	
	private static final Logger log = LoggerFactory.getLogger(SendCodeController.class);

	@Autowired
	private KafkaTemplate<String,Object> KafkaTemplate;
	
	@Autowired
	private MessageHandle handle;
	
	@Autowired
	private RealTimeDateService realTimeDateService;
	
	@Autowired
	private CodeProperties codeProperties;
	@Autowired
	private CommandsService commandsService;
	
//	//应用向终端发送的topic
//	private String TOPIC_APP_TO_TER = "SYS_MANAGE_CENTER";
	
	

	@ApiOperation(value = "应用向终端发指令", notes = "cmdContents与telIds均以逗号隔开"			
			)
//	@ApiImplicitParams({ 
//		@ApiImplicitParam(paramType = "query", dataType = "Long", name = "cmdType", value = "指令类型", required = true),
//		@ApiImplicitParam(paramType = "query", dataType = "Long", name = "cmdTitle", value = "指令标题", required = true),
//		@ApiImplicitParam(paramType = "query", dataType = "String", name = "cmdId", value = "指令id", required = true),
//		@ApiImplicitParam(paramType = "body", dataType = "String", name = "telIds", value = "终端编号id", required = true)
//	}) 
	@RequestMapping(value="/app/ter/code",method=RequestMethod.POST)
	public HttpResult sendCode(@RequestParam("cmdType") int cmdType,
			@RequestParam("cmdTitle") int cmdTitle,
			@RequestParam("cmdId") String cmdId,
			@RequestBody List<TerimalModel> telIds) {
		Message message = null;
		HttpResult res ;
		try {
			String cmdContents = commandsService.getCommandsById(cmdId);
			if(cmdContents==null) {
				res = new HttpResult(false,"指令id错误或未获得许可");
				return res;
			}
			message = MessageUtil.getMessage(90,1,cmdType, cmdTitle, cmdContents, MessageUtil.convertTerminalModelListToStringList(telIds));
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			KafkaTemplate.send(codeProperties.getTopicAppToTer(),messageJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new HttpResult(false,"出现异常，发送失败");
		}
		return res = new HttpResult(true,"发送成功");
	}
	
	
	
	@ApiOperation(value = "应用设置终端调试", notes = "cmdContents与telIds均以逗号隔开"			
			)	
	@RequestMapping(value="/app/ter/debug",method=RequestMethod.POST)
	public String sendDebug(
			@RequestBody List<TerimalModel> telIds) {
		Message message = null;
		try {
			message = MessageUtil.getMessage(90,2,null, null, null, MessageUtil.convertTerminalModelListToStringList(telIds));
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			KafkaTemplate.send(codeProperties.getTopicAppToTer(),messageJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "faile";
		}
		return "success";
	}
	
	
	@ApiOperation(value = "应用设置终端解除调试", notes = ""			
			)	
	@RequestMapping(value="/app/ter/endDebug",method=RequestMethod.POST)
	public String sendEndDebug(
			@RequestBody List<TerimalModel> telIds) {
		Message message = null;
		try {
			message = MessageUtil.getMessage(90,3,null, null, null, MessageUtil.convertTerminalModelListToStringList(telIds));
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			KafkaTemplate.send(codeProperties.getTopicAppToTer(),messageJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "faile";
		}
		return "success";
	}
	
	
	
	@ApiOperation(value = "发送指令 获取实时数据"			
			)	
	@RequestMapping(value="/app/ter/realData",method=RequestMethod.POST)
	public List<Map<String,Object>> sendCodeToGetRealData(
			@RequestParam("signal") String signal,
			@RequestBody List<TerimalModel> termimals) {
	    List<String> ids = new ArrayList<>();
	    termimals.forEach(action->{
	    	ids.add(action.getTerId());
	    });
		handle.getRealData(ids);
		return realTimeDateService.getAllDate(termimals,signal);
	}
	
	
	
	
	
//	/*
//	 *测试接受数据
//	 */
//	 @Scheduled(fixedRate = 1000000)
//	   private  void callback() throws Exception {
//		 String str = "{\"consumerId\":\"iot\",\"data\":{\"cmdTitle\":2,\"cmdContent\":\"ISIkJg==\",\"cmdType\":30,\"telIds\":\"[\\\"TEL0000001\\\"]\"},\"msgId\":5481,\"msgTime\":1527130382348,\"producerId\":\"TEL0000001\",\"title\":1,\"type\":90,\"version\":1}";
//		 log.debug("发送消息"+str);
//		 KafkaTemplate.send("DATA_PUBLISH", str);
//	   }
	
}
