package com.cimr.api.code.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.code.model.Terminal_1_Info;
import com.cimr.api.code.service.RealTimeDateService;
import com.cimr.api.comm.model.TerimalModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@Api(description="实时数据相关操作",tags= {"latestData"})
@RestController
@RequestMapping("/latest_data")
public class TerminalLastData {

	@Autowired
	private RealTimeDateService realTimeDateService;
	
	@ApiOperation(value = "获取最新数据,只查询给定字段"			
			)	
	@RequestMapping(value="/app/{signal}/include",method=RequestMethod.POST)
	public List<Map<String,Object>> getDateInclude(
			@RequestParam("fields") String[] fields,
			@PathVariable("signal") String signal,
			@RequestBody List<TerimalModel> termimals) {
	
		return realTimeDateService.getAllDateInclude(termimals,signal,fields);
	}
	
	@ApiOperation(value = "获取全部最新数据，排除给定字段"			
			)	
	
	@RequestMapping(value="/app/{signal}/exclude",method=RequestMethod.POST)
	public List<Map<String,Object>> getDateExclude(
			@RequestParam("fields") String[] fields,
			@PathVariable("signal") String signal,
			@RequestBody List<TerimalModel> termimals) {
	
		return realTimeDateService.getAllDateExclude(termimals,signal,fields);
	}
	
	
	
	@ApiOperation(value = "获取全部最新数据"			
			)	
	
	@RequestMapping(value="/app/{signal}/all",method=RequestMethod.POST)
	public List<Map<String,Object>> getAllDate(
			@PathVariable("signal") String signal,
			@RequestBody List<TerimalModel> termimals) {
	
		return realTimeDateService.getAllDate(termimals,signal);
	}
	
	
	@ApiOperation(value = "获取全部最新数据,统计其中boolean类型的数量",notes="countIncludeType与includeType默认为exclude "		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "includeType", value = "查询字段类型：排除或包含", required = false,allowableValues="INCLUDE,EXCLUDE"),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "fields", value = "需要查询的字段或排除的字段", required = false,allowMultiple=true),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "countIncludeType", value = "统计字段类型：排除或包含", required = false,allowableValues="INCLUDE,EXCLUDE"),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "countFields", value = "需要统计的字段或排除的字段", required = false,allowMultiple=true)
		}) 
	@RequestMapping(value="/app/booleancount/{signal}/all",method=RequestMethod.POST)
	public List<Map<String,Object>> getAllDateBoolean(
			@PathVariable("signal") String signal,
			@RequestBody List<TerimalModel> termimals,
			@RequestParam(name="includeType",required=false) String includeType,
			@RequestParam(name="fields",required=false) String[] fields,
			@RequestParam(name="countIncludeType",required=false) String countIncludeType,
			@RequestParam(name="countFields",required=false) String[] countFields) {
	
		return realTimeDateService.getDateBoolean(termimals,signal,includeType,fields,countIncludeType,countFields);
	}
}
