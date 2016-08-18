package com.dsky.baas.gameinvite.service.impl;

import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.service.IGameInviteRMIService;

@Service("gameInviteRMIService")
public class GameInviteRMIServiceImpl implements IGameInviteRMIService {

	@Override
	public String test() {

		return "123";
	}




}
