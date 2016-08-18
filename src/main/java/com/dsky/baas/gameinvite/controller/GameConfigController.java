package com.dsky.baas.gameinvite.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsky.baas.gameinvite.lib.ApiResultObject;
import com.dsky.baas.gameinvite.lib.ApiResultPacker;
import com.dsky.baas.gameinvite.model.GameConfig;
import com.dsky.baas.gameinvite.service.IGameConfigService;

@Controller
@RequestMapping("/game_config")
public class GameConfigController {

	private IGameConfigService gameConfigService;

	public IGameConfigService getGameConfigService() {
		return gameConfigService;
	}

	@Autowired
	public void setGameConfigService(IGameConfigService gameConfigService) {
		this.gameConfigService = gameConfigService;
	}

	@RequestMapping("/get_option_all")
	public @ResponseBody ApiResultObject getOptionAll(HttpServletRequest req) {

		String gameIdStr = req.getParameter("game_id");

		if (gameIdStr == null) {
			return ApiResultPacker.packToApiResultObject(100, "game_id不能为空 ");
		}
		int gameId;
		try {
			gameId = Integer.parseInt(gameIdStr);
		} catch (Exception e) {
			return ApiResultPacker.packToApiResultObject(100, "game_id不能为空 ");
		}

		List<GameConfig> gameConfigList = gameConfigService
				.getGameConfigByGameId(gameId);
		// req.setAttribute("json", ApiResultPacker.packToJSON(gameConfigList));
		return ApiResultPacker.packToApiResultObject(gameConfigList);
	}
	
	@RequestMapping("/get_option")
	public @ResponseBody ApiResultObject getOption(HttpServletRequest req) {
		String optionNameStr = req.getParameter("option_name");
		String gameIdStr = req.getParameter("game_id");

		if (gameIdStr == null) {
			return ApiResultPacker.packToApiResultObject(100, "game_id不能为空 ");
		}
		if (optionNameStr == null) {
			return ApiResultPacker.packToApiResultObject(100, "option_name不能为空 ");
		}
		int gameId;
		try {
			gameId = Integer.parseInt(gameIdStr);
		} catch (Exception e) {
			return ApiResultPacker.packToApiResultObject(100, "game_id不能为空 ");
		}
		

		GameConfig gameConfig = gameConfigService
				.getByGameIdAndOptionName(gameId, optionNameStr);
		// req.setAttribute("json", ApiResultPacker.packToJSON(gameConfigList));
		return ApiResultPacker.packToApiResultObject(gameConfig);
	}
}
