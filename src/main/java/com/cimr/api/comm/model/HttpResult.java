package com.cimr.api.comm.model;


public class HttpResult {
	
	private boolean success;

	private Object data;

	private String error;

	public HttpResult(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}

	public HttpResult(boolean success, String error) {
		this.success = success;
		this.error = error;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
