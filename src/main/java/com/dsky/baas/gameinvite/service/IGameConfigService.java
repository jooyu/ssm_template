package com.dsky.baas.gameinvite.service;

import java.util.List;

import com.dsky.baas.gameinvite.model.GameConfig;

public interface IGameConfigService {
	public List<GameConfig> getGameConfigByGameId(int id);
	public GameConfig getByGameIdAndOptionName(int gameId,String optionName);
}
