package com.dsky.baas.gameinvite.service;

import com.dsky.baas.gameinvite.model.InvitedCode;

public interface IInvitedCodeService {

	public String createInvitedCode(int playerId,int gameId,String deviceId,int actId);
	public String createInvitedCode(int playerId,int gameId,int actId);
	public InvitedCode getInvitedCodeByCode(String code) throws Exception ;
	public InvitedCode getInvitedCodeByPlayerIdAndGameIdAndActId(int playerId,int gameId,int actId);
	public InvitedCode getInvitedCodeByDeviceId(String deviceId);
	public void addApplyCountByCode(String code);
	public int getApplyCountByCode(String code);
	
}
