package com.cimr.api.history.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.cimr.boot.mongodb.config.DefaultMongoConfig;

@Configuration
@EnableMongoRepositories(basePackages = "com.cimr.api.history.model",
        mongoTemplateRef = HistoryMongoConfig.MONGO_TEMPLATE)
public class HistoryMongoConfig extends DefaultMongoConfig{
	
	
	public static final String MONGO_TEMPLATE = "history";
	
	
	
  
	/**
	 * 用于mongodb历史记录查询
	 * 使用第一个配置的数据源，编号为0
	 * @return
	 * @throws Exception
	 */
	@Primary
    @Bean(name = HistoryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate primaryMongoTemplate() throws Exception {
        return super.primaryMongoTemplate(0);
	}
    
  
   
}
