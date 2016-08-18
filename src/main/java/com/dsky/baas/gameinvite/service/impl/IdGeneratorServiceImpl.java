package com.dsky.baas.gameinvite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.dao.IdGeneratorMapper;
import com.dsky.baas.gameinvite.model.IdGenerator;
import com.dsky.baas.gameinvite.service.IIdGeneratorService;

@Service("idGeneratorService")
public class IdGeneratorServiceImpl implements IIdGeneratorService {

	private IdGeneratorMapper idGeneratorMapper;
	public IdGeneratorMapper getIdGeneratorMapper() {
		return idGeneratorMapper;
	}
	@Autowired
	public void setIdGeneratorMapper(IdGeneratorMapper idGeneratorMapper) {
		this.idGeneratorMapper = idGeneratorMapper;
	}
	@Override
	public long generate() {
		IdGenerator idGeneratorModel = new IdGenerator();
		idGeneratorModel.setCreateTime((int)(System.currentTimeMillis()/1000));
		idGeneratorMapper.insertSelective(idGeneratorModel);
		return idGeneratorModel.getId();
	}

}
