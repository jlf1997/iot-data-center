package com.cimr.api.code.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(description="指令发送历史查询",tags= {"codeHistory"})
@RestController
@RequestMapping("/codeHistory")
public class CodeHistoryController {
	
}
