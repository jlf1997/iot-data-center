package com.cimr.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("demo")
public class demo {

	@KafkaListener(topics="testKafkaTopic")
	private void testListener(String message) {
		System.out.println("================:"+message);
	}
	
	
	@RequestMapping(value="/sendTopic",method=RequestMethod.GET)
	public String sendTopic() {
		kafkaTemplate.send("testKafkaTopic","test123456");
		return "success";
	}
	
	@Autowired
	private KafkaTemplate kafkaTemplate;

	
	
	
}
