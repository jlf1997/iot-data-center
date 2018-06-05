package com.cimr.api.comm.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.websocket.config.WebSocketProperties;

@Configuration
public class IotAutoConfiguration {

	
	@Bean
    @ConditionalOnMissingBean
    public WebSocketProperties webSocketProperties() {
        return new WebSocketProperties();
    }
	
	@Bean
    @ConditionalOnMissingBean
    public CodeProperties codeProperties() {
        return new CodeProperties();
    }
}
