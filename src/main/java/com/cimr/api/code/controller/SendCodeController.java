
package com.cimr.api.code.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import io.swagger.annotations.ApiOperation;


@Api(description="指令据相关操作",tags= {"appCode"})
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
	@RequestMapping(value="/app/ter/code",method=RequestMethod.POST)
	public HttpResult sendCode(@RequestParam("cmdType") Integer cmdType,
			@RequestParam("cmdTitle") Integer cmdTitle,
			@RequestParam("cmdId") String cmdId,
			@RequestBody List<TerimalModel> telIds) {
		Message message = null;
		HttpResult res ;
		try {
			String cmdContents = commandsService.getCommandsById(cmdId);
			//判断指令是否错误
			if(cmdContents==null ) {
				res = new HttpResult(false,"指令id错误或未获得许可");
				return res;
			}
			if(StringUtils.isBlank(cmdContents)) {
				res = new HttpResult(false,"指令内容为空");
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
	public String sendCodeToGetRealData(
			@RequestParam("signal") String signal,
			@RequestBody List<TerimalModel> termimals) {
	    List<String> ids = new ArrayList<>();
	    termimals.forEach(action->{
	    	ids.add(action.getTerId());
	    });
		handle.getRealData(ids);
		return "success";
	}
	
	
	
	
	
	
	
	

	
}
