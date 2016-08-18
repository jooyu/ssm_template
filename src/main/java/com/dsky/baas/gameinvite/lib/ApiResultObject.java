package com.dsky.baas.gameinvite.lib;

import java.util.HashMap;

public class ApiResultObject {
	private Integer code = 0;
	private String msg = "";
	private Object data;
	private Object ext;
	public ApiResultObject(){
		data = new HashMap<String,Object>();
		ext = new HashMap<String,Object>();
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getExt() {
		return ext;
	}
	public void setExt(Object ext) {
		this.ext = ext;
	}

}
