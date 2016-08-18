package com.dsky.baas.gameinvite.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.service.IPointsServiceRMIService;
import com.dsky.baas.pointsservice.rmi.IPointsPoolService;

@Service("pointsServiceRMIService")
public class PointsServiceRMIServiceImpl implements IPointsServiceRMIService,ApplicationContextAware {
	private final Logger log = Logger.getLogger(PointsServiceRMIServiceImpl.class);
	private ApplicationContext ac;
	private IPointsPoolService pointsPoolService;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
		
		pointsPoolService = (IPointsPoolService)ac.getBean("pointsServerRMIServiceClient");
	}
	@Override
	public void applyCode(int playerIdA, int shouldPointsA,
			int addPlayerAPoints, int playerIdB, int shouldPointsB,
			int addPlayerBPoints, int gameId, int actId, String invitedCode) {
		
		pointsPoolService.addPoints(playerIdA, shouldPointsA, addPlayerAPoints, playerIdB, shouldPointsB, addPlayerBPoints, gameId, actId, invitedCode);
		
	}

}
