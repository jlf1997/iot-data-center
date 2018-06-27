package com.cimr.api.code.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.cimr.api.code.util.MessageUtil;
import com.cimr.api.comm.model.TerimalModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(description="指令据相关操作",tags= {"systemCode"})
@RestController
@RequestMapping("/code")
public class SendSysCodeController {

	
	private static final Logger log = LoggerFactory.getLogger(SendSysCodeController.class);

	
	@Autowired
	private CodeProperties codeProperties;
	
	@Autowired
	private KafkaTemplate<String,Object> KafkaTemplate;
	
	@ApiOperation(value = "向终端发送命令，可自定义参数，api中未限制，所有给的参数将全部传给终端"			
			)	
	@RequestMapping(value="/system/command",method=RequestMethod.POST)
	public String sendSysCommemd(
			HttpServletRequest request,
			@RequestParam(name="title",required=false,defaultValue="3") Integer title,
			@RequestParam(name="type",required=false,defaultValue="90") Integer type,
			@RequestParam(name="version",required=false,defaultValue="1") Integer version,
			@RequestBody List<TerimalModel> termimals) {
		Message message = null;
		try {
			message = MessageUtil.getSysMessage(version,type,title,request, MessageUtil.convertTerminalModelListToStringList(termimals));
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			KafkaTemplate.send(codeProperties.getTopicAppToTer(),messageJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "faile";
		}
		return "success";
	}
	
	
	@ApiOperation(value = "向终端发送命令，可自定义参数，api中未限制，所有给的参数将全部传给终端"			
			)	
	@RequestMapping(value="/system/clean",method=RequestMethod.POST)
	public String sendSysCommemdClean(
			HttpServletRequest request,
			@RequestBody List<TerimalModel> termimals) {
		Message message = null;
		try {
			message = MessageUtil.getSysMessage(1,50,13,null, MessageUtil.convertTerminalModelListToStringList(termimals));
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			KafkaTemplate.send(codeProperties.getTopicAppToTer(),messageJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "faile";
		}
		return "success";
	}
}
