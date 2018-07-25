package com.cimr.api.code.model.base;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

/**
 * 保存指令发送记录
 * @author Administrator
 *
 */
@Table(name="td_code_history")
@Entity
public class CodeHistory extends BaseModel{

	@Id
	private Long id;
	
	/**
	 * 最终发送的消息
	 */
	@Lob
	private String messageJson;
	
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageJson() {
		return messageJson;
	}

	public void setMessageJson(String messageJson) {
		this.messageJson = messageJson;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
