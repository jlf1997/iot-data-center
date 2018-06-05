package com.cimr.boot.mongodb.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mongodb")
public class MultipleMongoProperties {
	
	private MongoProperties[] source;

	
	
	public MongoProperties[] getSource() {
		return source;
	}



	public void setSource(MongoProperties[] source) {
		this.source = source;
	}



	public MongoProperties getMongoNamedProperties(int index) {
		
		return source[index];
	}

	
	 
	 
	 
	 
	 
}
