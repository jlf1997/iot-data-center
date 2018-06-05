package com.cimr.api.code.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.cimr.api.code.model.Message;
import com.cimr.api.comm.model.TerimalModel;



@Configuration
public class MessageUtil {
	
	static {
		//防止fastjson将小数转为BigDecimal
		int features = 0;
		features |= Feature.AutoCloseSource.getMask();
		features |= Feature.InternFieldNames.getMask();
//				features |= Feature.UseBigDecimal.getMask();
		features |= Feature.AllowUnQuotedFieldNames.getMask();
		features |= Feature.AllowSingleQuotes.getMask();
		features |= Feature.AllowArbitraryCommas.getMask();
		features |= Feature.SortFeidFastMatch.getMask();
		features |= Feature.IgnoreNotMatch.getMask();
		JSON.DEFAULT_PARSER_FEATURE = features;
		//防止fastjson将字段首字母小写
		System.setProperty("fastjson.compatibleWithJavaBean", "true");
	}
	/**
	 * 构造消息对象
	 * @param cmdType 指令类型
	 * @param cmdTitle 指令标题
	 * @param cmdContents 指令内容
	 * @param telIds 终端编号
	 * @return 消息指令对象
	 * @throws UnsupportedEncodingException
	 */
	public static Message getMessage(int type,int title,Integer cmdType,Integer cmdTitle,String cmdContents,List<String> telIds) throws UnsupportedEncodingException {
	   return MessageUtil.getMessage(1, type, title, cmdType, cmdTitle, cmdContents, telIds);
	}
	
	
	/**
	 * 构造消息对象
	 * @param cmdType 指令类型
	 * @param cmdTitle 指令标题
	 * @param cmdContents 指令内容
	 * @param telIds 终端编号
	 * @return 消息指令对象
	 * @throws UnsupportedEncodingException
	 */
	public static Message getMessage(int version,int type,int title,Integer cmdType,Integer cmdTitle,String cmdContents,List<String> telIds) throws UnsupportedEncodingException {
		Message message = new Message();
		message.setProducerId("app");
		message.setConsumerId("iot");
		Random rm = new Random();
		message.setMsgId(rm.nextInt(65534)+1);
		message.setMsgTime(new Date());
		message.setVersion((byte) version);
		message.setType((byte) type);
		message.setTitle((byte) title);
		Map<String,Object> data = new HashMap<>();
		if(cmdType!=null) {
			data.put("cmdType", cmdType);
		}
		if(cmdTitle!=null) {
			data.put("cmdTitle", cmdTitle);
		}
		if(cmdContents!=null) {
			data.put("cmdContent", cmdContents.getBytes("ISO-8859-1"));	
		}
			
		if(telIds!=null) {
		
			telIds.stream().filter(predicate->{
				if("".equals(predicate)) {
					return false;
				}
				return true;
			});
			String telIdsJson = JSON.toJSONString(telIds);
			data.put("telIds", telIdsJson);
		}
		message.setData(data);
		return message;
	}
	
	
	/**
	 * 终端对象列表转换为终端编号列表
	 * @param terList
	 * @return
	 */
	public static List<String> convertTerminalModelListToStringList(List<TerimalModel> terList){
		List<String> list = new ArrayList<>();
		Assert.notNull(terList,"terList is not null");
		terList.forEach(terminal->{
			Assert.notNull(terminal,"terminal is not null");
			list.add(terminal.getTerId());
		});
		return list;
	}
}
