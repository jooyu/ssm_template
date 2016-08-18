package com.dsky.baas.gameinvite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.dsky.baas.gameinvite.dao.InvitedCodeMapper;
import com.dsky.baas.gameinvite.model.InvitedCode;
import com.dsky.baas.gameinvite.service.IIdGeneratorService;
import com.dsky.baas.gameinvite.service.IInvitedCodeService;
import com.dsky.baas.gameinvite.service.IRedisPoolService;
import com.dsky.baas.gameinvite.util.ShareCodeUtil;

@Service("invitedCodeService")
public class InvitedCodeServiceImpl implements IInvitedCodeService {

	private InvitedCodeMapper invitedCodeMapper;
	private IRedisPoolService redisPoolService;
	private IIdGeneratorService idGenerateorService;
	
	
	public InvitedCodeMapper getInvitedCodeMapper() {
		return invitedCodeMapper;
	}
	@Autowired
	public void setInvitedCodeMapper(InvitedCodeMapper invitedCodeMapper) {
		this.invitedCodeMapper = invitedCodeMapper;
	}
	public IRedisPoolService getRedisPoolService() {
		return redisPoolService;
	}
	@Autowired
	public void setRedisPoolService(IRedisPoolService redisPoolService) {
		this.redisPoolService = redisPoolService;
	}
	public IIdGeneratorService getIdGenerateorService() {
		return idGenerateorService;
	}
	@Autowired
	public void setIdGenerateorService(IIdGeneratorService idGenerateorService) {
		this.idGenerateorService = idGenerateorService;
	}


	
	
	@Override
	public String createInvitedCode(int playerId, int gameId ,String deviceId,int actId){

		
		//生成邀请码 + 校验码
		String tablePostfix = (playerId%10)+"";
		String checkCode = ShareCodeUtil.idToCode(tablePostfix);
		long id = idGenerateorService.generate();
		String invitedCodeStr = ShareCodeUtil.idToCode(id);
		String codeStr = invitedCodeStr+checkCode;
		//入库
		InvitedCode invitedCodeInsertObj = new InvitedCode();
		invitedCodeInsertObj.setTablePostfix(tablePostfix);
		invitedCodeInsertObj.setGameId(gameId);
		invitedCodeInsertObj.setPlayerId(playerId);
		invitedCodeInsertObj.setCreateAt((int)(System.currentTimeMillis()/1000));
		invitedCodeInsertObj.setDeviceId(deviceId);
		invitedCodeInsertObj.setActId(actId);
		invitedCodeInsertObj.setCode(codeStr);
		
		invitedCodeMapper.insertSelective(invitedCodeInsertObj);
		return codeStr;
		
	}
	@Override
	public String createInvitedCode(int playerId, int gameId,int actId) {
		return createInvitedCode(playerId, gameId, "",actId);
	}



	/** 
	 * 通过code查询邀请码详情
	 * @throws Exception 
	 * 
	 * 
	 */
	@Override
	public InvitedCode getInvitedCodeByCode(String code) throws Exception {
		Jedis connect = redisPoolService.getConnect();
		String cacheKey = "invited_code:code:"+code;
		String cacheString = connect.get(cacheKey);
		InvitedCode invitedCodeObj;
		
		if(cacheString==null || cacheString.isEmpty() || cacheString.equals("") || cacheString.equals("null") ){
			InvitedCode invitedCodeParam = new InvitedCode();
			invitedCodeParam.setCode(code);
			String codeEndStr = code.substring(code.length()-1);//获得校验码
			
			long n = ShareCodeUtil.codeToId(codeEndStr);//校验码转换为10进制
			if(n>9 || n<0){
				throw new Exception("校验码不合法");
			}
			invitedCodeParam.setTablePostfix(n+"");
			invitedCodeObj = invitedCodeMapper.selectByCode(invitedCodeParam);
			
			if(invitedCodeObj!=null){
				invitedCodeObj.setTablePostfix(invitedCodeParam.getTablePostfix());
				connect.setex(cacheKey, 1*60*60, JSON.toJSONString(invitedCodeObj));
			}
		}else{
			invitedCodeObj = JSON.parseObject(cacheString, InvitedCode.class);
		}
		
		return invitedCodeObj;
		
	}
	/** 
	 * 通过玩家id和游戏id获取验证码信息
	 * @param playerId
	 * @param gameId
	 * @param actId
	 * @return
	 */
	@Override
	public InvitedCode getInvitedCodeByPlayerIdAndGameIdAndActId(int playerId,
			int gameId,int actId) {
		InvitedCode selectInvitedCodeObj = new InvitedCode();
		selectInvitedCodeObj.setTablePostfix((playerId%10)+"");
		selectInvitedCodeObj.setPlayerId(playerId);
		selectInvitedCodeObj.setGameId(gameId);
		selectInvitedCodeObj.setActId(actId);
		return invitedCodeMapper.selectByPlayerIdAndGameIdAndActId(selectInvitedCodeObj);
	}
	/**
	 * 通过设备id获得邀请码
	 * @param deviceId
	 * @return
	 */
	@Override
	public InvitedCode getInvitedCodeByDeviceId(String deviceId) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 给邀请码被使用数量+1
	 */
	@Override
	public void addApplyCountByCode(String code) {
		String codeEndStr = code.substring(code.length()-1);//获得校验码
		long n = ShareCodeUtil.codeToId(codeEndStr);//校验码转换为10进制
		
		InvitedCode invitedCodeObj = new InvitedCode();
		invitedCodeObj.setTablePostfix(n+"");
		invitedCodeObj.setCode(code);

		
		invitedCodeMapper.addApplyCountByCode(invitedCodeObj);
	}
	/**
	 * 获得邀请码的被使用次数
	 */
	@Override
	public int getApplyCountByCode(String code) {
		String codeEndStr = code.substring(code.length()-1);//获得校验码
		long n = ShareCodeUtil.codeToId(codeEndStr);//校验码转换为10进制
		InvitedCode invitedCodeObj = new InvitedCode();
		invitedCodeObj.setTablePostfix(n+"");
		invitedCodeObj.setCode(code);
		
		InvitedCode invitedCodeRes = invitedCodeMapper.selectByCode(invitedCodeObj);
		if(invitedCodeRes==null){
			return -1;
		}else{
			return invitedCodeRes.getApplyCount();
		}
		
	}


}
