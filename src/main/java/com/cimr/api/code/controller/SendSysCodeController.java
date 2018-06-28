package com.cimr.api.code.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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


@Api(description="系统指令操作，只提供给管控平台使用",tags= {"systemCode"})
@RestController
@RequestMapping("/code")
public class SendSysCodeController {

	
	private static final Logger log = LoggerFactory.getLogger(SendSysCodeController.class);

	
	@Autowired
	private CodeProperties codeProperties;
	
	@Autowired
	private KafkaTemplate<String,Object> KafkaTemplate;
	
	@ApiOperation(value = "向终端发送系统命令，可自定义参数，api中未限制，所有给的参数将全部传给终端"			
			)	
	@RequestMapping(value="/system/command",method=RequestMethod.POST)
	public String sendSysCommemd(
			HttpServletRequest request,
			@RequestParam(name="title",required=true) Integer title,
			@RequestParam(name="type",required=true) Integer type,
			@RequestParam(name="version",required=true) Integer version,
			@RequestBody List<TerimalModel> termimals) {
		Message message = null;
		try {
			message = MessageUtil.getSysMessage(version,type,title,request, MessageUtil.convertTerminalModelListToStringList(termimals));
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			
			try {
				KafkaTemplate.send(codeProperties.getTopicAppToTer(),messageJson).get(60000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "false";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "faile";
		}
		return "success";
	}
	
	
	@ApiOperation(value = "向终端发送系统命令：清除信号配置缓存"			
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
			try {
				KafkaTemplate.send(codeProperties.getTopicAppToTer(),messageJson).get(60000, TimeUnit.MILLISECONDS);
				
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "faile";
			};
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "faile";
		}
		return "success";
	}
}
