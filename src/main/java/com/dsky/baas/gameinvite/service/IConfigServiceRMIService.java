package com.dsky.baas.gameinvite.service;

import com.dsky.baas.configservice.model.ApiResultBean;

public interface IConfigServiceRMIService {

	public String getActIdByGameId(String gameId);
	public String getActIdByGameId(String gameId,String location);
	public String getActIdByGameId(int gameId);
	public String getActIdByGameId(int gameId,String location);
	
	public ApiResultBean getActBeanByActId(int actId);
	public ApiResultBean getActBeanByGameId(String gameId,String location);
	public ApiResultBean getActBeanByGameId(String gameId);
	
	public boolean isUnderAvailable(int actId);
	public int getDeviceCreateCountMaxByActId(int actId);
	public int getDeviceApplyCodeCountMax(int actId);
	
	public int getCanGenerateCodeLevel(int actId);
	
}
