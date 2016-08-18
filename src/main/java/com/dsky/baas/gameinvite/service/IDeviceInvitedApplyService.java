package com.dsky.baas.gameinvite.service;

public interface IDeviceInvitedApplyService {

	public int getDeviceCodeApplyCount(String deviceId,String code);
	
	public int getDeviceCountApplyAdd(String deviceId,String code);
}
