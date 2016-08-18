package com.dsky.baas.gameinvite.service;

public interface IInvitedPeopleService {

	/**
	 * 创建邀请关系
	 * @param playerId
	 * @param invitedPlayerId
	 * @param gameId
	 */
	public void createInvitedPeople(int invitedPlayerId, int playerId, int gameId,int actId);
	/**
	 * 检查用户是否已经被邀请
	 * @param playerId
	 * @param invitedPlayerId
	 * @param gameId
	 * @return boolean
	 */
	public boolean isAlreadyInvited(int invitedPlayerId, int gameId,int actId);

}
