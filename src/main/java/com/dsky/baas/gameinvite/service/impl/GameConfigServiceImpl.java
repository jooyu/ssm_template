package com.dsky.baas.gameinvite.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.dao.GameConfigMapper;
import com.dsky.baas.gameinvite.model.GameConfig;
import com.dsky.baas.gameinvite.service.IGameConfigService;

@Service("gameConfigService")
public class GameConfigServiceImpl implements IGameConfigService {

	private GameConfigMapper gameConfigMapper;

	public GameConfigMapper getGameConfigMapper() {
		return gameConfigMapper;
	}
	@Autowired
	public void setGameConfigMapper(GameConfigMapper gameConfigMapper) {
		this.gameConfigMapper = gameConfigMapper;
	}

	@Override
	public List<GameConfig> getGameConfigByGameId(int gameId) {

		return gameConfigMapper.selectByGameId(gameId);
	}
	@Override
	public GameConfig getByGameIdAndOptionName(int gameId,String optionName) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("gameId", gameId);
		param.put("optionName", optionName);
		return gameConfigMapper.selectByGameIdAndOptionName(param);
	}	
}
