package com.cimr.api.code.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iot.code")
public class CodeProperties {

	/**
	 * 订阅间隔 毫秒数
	 */
	private Long subscribeTimeMill = 5*60*1000L;
	
	/**
	 * 应用向终端发送的topic
	 */
	private String topicAppToTer = "SYS_MANAGE_CENTER";
	
	/**
	 * 接受终端发送实时数据的主题
	 */
	private String topicReciveRealData = "DATA_PUBLISH";
	
	public static final String NEW_DATA = "NEW_DATA_";
	

	public Long getSubscribeTimeMill() {
		return subscribeTimeMill;
	}

	public void setSubscribeTimeMill(Long subscribeTimeMill) {
		this.subscribeTimeMill = subscribeTimeMill;
	}

	public String getTopicAppToTer() {
		return topicAppToTer;
	}

	public void setTopicAppToTer(String topicAppToTer) {
		this.topicAppToTer = topicAppToTer;
	}

	public String getTopicReciveRealData() {
		return topicReciveRealData;
	}

	public void setTopicReciveRealData(String topicReciveRealData) {
		this.topicReciveRealData = topicReciveRealData;
	}
	
	
	
	
	
	
}
