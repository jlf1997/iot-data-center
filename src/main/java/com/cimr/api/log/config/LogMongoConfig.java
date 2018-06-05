package com.cimr.api.log.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.cimr.boot.mongodb.config.DefaultMongoConfig;
import com.cimr.boot.mongodb.config.MultipleMongoProperties;

@Configuration
@EnableMongoRepositories(basePackages = "com.cimr.api.log.model",
        mongoTemplateRef = LogMongoConfig.MONGO_TEMPLATE)
public class LogMongoConfig extends DefaultMongoConfig{
	
	public static final String MONGO_TEMPLATE = "log";
	

	
  
    @Bean(name = LogMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate primaryMongoTemplate() throws Exception {
        return super.primaryMongoTemplate(1);
    }
    
  
   
}
