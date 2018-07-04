package com.cimr.api.code.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.dao.CommandsDao;
import com.cimr.api.code.dao.DictDao;
import com.cimr.api.code.model.Commands;

@Service
public class CommandsService {

	
	@Autowired
	private CommandsDao commandsDao;
	
	@Autowired
	private DictDao dictDao;
	
	@Autowired
	private CodeProperties codeProperties;
	
	
	/**
	 * 根据指令id获取指令内容 指令内容必须可查
	 * @param id
	 * @return
	 */
	public String getCommandsById(String id) {
		Commands t = new Commands();
		t.setId(id);
		t.setAppLicense(codeProperties.getAppLicenseCode());
		Commands res = commandsDao.find(t);
		if(res!=null) {
			return res.getContents();
		}		
		return null;
		
	}
	
	
}
