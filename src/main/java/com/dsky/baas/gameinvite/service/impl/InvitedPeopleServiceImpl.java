package com.dsky.baas.gameinvite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.dao.InvitedPeopleMapper;
import com.dsky.baas.gameinvite.model.InvitedPeople;
import com.dsky.baas.gameinvite.service.IInvitedPeopleService;

@Service("invitedPeopleService")
public class InvitedPeopleServiceImpl implements IInvitedPeopleService {
	
	private InvitedPeopleMapper invitedPeopleMapper;

	public InvitedPeopleMapper getInvitedPeopleMapper() {
		return invitedPeopleMapper;
	}
	@Autowired
	public void setInvitedPeopleMapper(InvitedPeopleMapper invitedPeopleMapper) {
		this.invitedPeopleMapper = invitedPeopleMapper;
	}
	
	/** 
	 * 创建邀请关系
	 */
	@Override
	public void createInvitedPeople(int invitedPlayerId, int playerId,  int gameId, int actId) {
		InvitedPeople invitedPeople = new InvitedPeople();
		int tablePostfixNum = invitedPlayerId%10;
		invitedPeople.setTablePostfix(tablePostfixNum+"");
		invitedPeople.setPlayerId(playerId);
		invitedPeople.setInvitedPlayerId(invitedPlayerId);
		invitedPeople.setGameId(gameId);
		invitedPeople.setActId(actId);
		invitedPeople.setCreateAt((int)(System.currentTimeMillis()/1000));
		invitedPeople.setDeleteAt(0);
		invitedPeopleMapper.insertSelective(invitedPeople);
	}
	/**
	 * 检查用户是否已经被别人邀请
	 */
	@Override
	public boolean isAlreadyInvited(int invitedPlayerId,
			int gameId,int actId) {
		int tablePostfixNum = invitedPlayerId%10;//邀请码发出者ID
		
		InvitedPeople invitedPeople = new InvitedPeople();
		invitedPeople.setTablePostfix(tablePostfixNum+"");
		invitedPeople.setInvitedPlayerId(invitedPlayerId);
		invitedPeople.setGameId(gameId);
		invitedPeople.setActId(actId);
		if(null==invitedPeopleMapper.selectByInvitedPlayerIdAndGameId(invitedPeople)){
			return false;
		}else{
			return true;
		}
	}
}
