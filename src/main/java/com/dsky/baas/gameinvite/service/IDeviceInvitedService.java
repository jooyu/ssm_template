package com.dsky.baas.gameinvite.service;

public interface IDeviceInvitedService {

	public int getDeviceCodeCount(String deviceId,int gameId ,int actId);
	
	public int getDeviceCountAdd(String deviceId,int gameId,int actId);
}
