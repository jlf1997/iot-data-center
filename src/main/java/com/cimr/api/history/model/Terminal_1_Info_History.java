package com.cimr.api.history.model;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cimr.api.comm.model.TerimalModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 对应终端的历史数据
 * @author Administrator
 *
 */
@ApiModel(value = "Terminal_1_Info", description = "信号组1终端查询对象")
public class Terminal_1_Info_History extends TerimalModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty(value = "lng",notes="经度")
	private String lng;
	@ApiModelProperty(value = "lat",notes="纬度")
	private String lat;
	
	@ApiModelProperty(value = "time",notes="记录时间")
	private Long time;

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	
	
	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Terminal_1_Info_History() {
		
	}
	
	public void setTerminalId(Map map) {
		this.setTerId(map.get("terminalNo").toString());
	}
	
	public void setLocation(Map map) {
//		setTerminalId(map);
		this.lat = map.get("lat").toString();
		this.lng = map.get("lng").toString();
	}
	
	public void setTime(Map map) {
		this.time = Long.parseLong( map.get("gatherMsgTime").toString());
	}
	
	
}
