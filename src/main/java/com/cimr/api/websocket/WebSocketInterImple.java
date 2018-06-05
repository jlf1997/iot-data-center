package com.cimr.api.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import com.cimr.api.websocket.model.SessionObject;
import com.cimr.boot.websocket.WebSocketChannelInter;

public class WebSocketInterImple implements WebSocketChannelInter{

	
	
	//记录连接websocket的终端
	private static Map<String,SessionObject> seesionMap = new ConcurrentHashMap<>();

	@Override
	public void connect(StompHeaderAccessor sha) {
		String terid = sha.getNativeHeader("terid").get(0);	
		SessionObject so = new SessionObject();
		so.setSa(sha);
		so.setTerid(terid);
		seesionMap.put(sha.getSessionId(),so);
	}

	@Override
	public void disconnect(StompHeaderAccessor sha) {
		seesionMap.remove(sha.getSessionId());
	}

	public static Map<String, SessionObject> getSeesionMap() {
		return seesionMap;
	}

	public static void setSeesionMap(Map<String, SessionObject> seesionMap) {
		WebSocketInterImple.seesionMap = seesionMap;
	}

	
	
	
	
}
