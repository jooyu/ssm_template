package com.dsky.baas.gameinvite.service;

public interface IPointsServiceRMIService {

	public void applyCode(int playerIdA, int shouldPointsA,	int addPlayerAPoints,
			int playerIdB, int shouldPointsB, int addPlayerBPoints,
			int gameId, int actId, String invitedCode);
}
