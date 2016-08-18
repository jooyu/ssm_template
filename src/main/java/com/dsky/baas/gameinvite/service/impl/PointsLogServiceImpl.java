package com.dsky.baas.gameinvite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.dao.PointsLogMapper;
import com.dsky.baas.gameinvite.service.IPointsLogService;

@Service("pointsLogService")
public class PointsLogServiceImpl implements IPointsLogService {

	private PointsLogMapper pointsLogMapper;
	
	@Override
	public void createNewTableIfNotExists(String tableName) {
		// TODO Auto-generated method stub
		pointsLogMapper.createNewTableIfNotExists(tableName);
	}

	public PointsLogMapper getPointsLogMapper() {
		return pointsLogMapper;
	}
	@Autowired
	public void setPointsLogMapper(PointsLogMapper pointsLogMapper) {
		this.pointsLogMapper = pointsLogMapper;
	}
	

}
