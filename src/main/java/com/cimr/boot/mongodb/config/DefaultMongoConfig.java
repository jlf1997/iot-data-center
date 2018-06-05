package com.cimr.boot.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoURI;


@Configuration
public abstract class DefaultMongoConfig {
	
	
	@Autowired
    protected MultipleMongoProperties mongoProperties;
	
    
   
	protected MongoTemplate primaryMongoTemplate(int index) throws Exception {
        return new MongoTemplate(primaryFactory(mongoProperties.getMongoNamedProperties(index)));
    }
    
  
    private MongoDbFactory primaryFactory(MongoProperties mongo) throws Exception {
        return new SimpleMongoDbFactory(new MongoURI(mongo.getUri()));
    }
}
