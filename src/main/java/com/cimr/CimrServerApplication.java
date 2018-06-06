package com.cimr;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cimr.boot.convert.EnableHttpConvert;
import com.cimr.boot.swagger.EnableSwagger2Doc;


@SpringCloudApplication
//@SpringBootApplication
@EnableSwagger2Doc
@EnableHttpConvert
@EnableScheduling
@EnableFeignClients
public class CimrServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CimrServerApplication.class, args);
	}
}
