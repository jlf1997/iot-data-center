package com.cimr;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cimr.api.code.util.MessageUtil;
import com.cimr.boot.convert.EnableHttpConvert;
import com.cimr.boot.swagger.EnableSwagger2Doc;


//@SpringCloudApplication
@SpringBootApplication
@EnableSwagger2Doc
@EnableHttpConvert
@EnableScheduling
//@EnableFeignClients
public class CimrServerApplication {
	


	public static void main(String[] args) throws UnsupportedEncodingException {
		SpringApplication.run(CimrServerApplication.class, args);
////		String cmd = "0x01, 0x1C, 0x1F, 0x17, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01";
//		String cmd = "0x01, 0x1C, 0x1F, 0x17, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01";
//		cmd = MessageUtil.parseCmdContent(cmd);
//		
////		byte[] bb = MessageUtil.parseCmdBytes(cmd);
//		byte[] bc = MessageUtil.getBytes(cmd);
//		String tt = new String(bc,"ISO8859-1");
//		System.out.println(tt);
	}
	
	
}
