package com.dsky.baas.gameinvite.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.dsky.baas.configservice.model.ApiResultBean;
import com.dsky.baas.configservice.rmi.IGameConfig;
import com.dsky.baas.gameinvite.service.IConfigServiceRMIService;
import com.dsky.baas.gameinvite.service.IRedisPoolService;
import com.dsky.baas.gameinvite.util.CommonUtil;

@Service("configServiceRMIService")
public class ConfigServiceRMIServiceImpl implements IConfigServiceRMIService,
		ApplicationContextAware {
	private ApplicationContext ac;
	private IGameConfig rmiObject;
	private IRedisPoolService redisPool;
	private final Logger log = Logger.getLogger(ConfigServiceRMIServiceImpl.class);

	public IRedisPoolService getRedisPool() {
		return redisPool;
	}
	@Autowired
	public void setRedisPool(IRedisPoolService redisPool) {
		this.redisPool = redisPool;
	}

	/**
	 * 转换str到时间戳
	 * 
	 * @param DTString
	 * @return
	 */
	private int parseConfigServiceDateTimeStringToUnixTimeStamp(String DTString) {

		String format = "yyyy-MM-dd HH:mm:ss.SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		int returnTS = 0;
		try {
			Date parsedDate = sdf.parse(DTString);
			returnTS = (int) (parsedDate.getTime() / 1000);
		} catch (ParseException e) {
			return 0;
		}
		log.debug("parseConfigServiceDateTimeStringToUnixTimeStamp");
		log.debug(returnTS);
		return returnTS;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
		rmiObject = (IGameConfig) ac.getBean("configServerRMIServiceClient");
	}

	@Override
	public String getActIdByGameId(String gameId) {

		return getActIdByGameId(gameId, "中国大陆");

	}

	@Override
	public String getActIdByGameId(String gameId, String location) {
		ApiResultBean res = getActBeanByGameId(gameId, location);
		if (res.getCode() > 0) { // 发生错误
			return null;
		} else {
			return res.getId().toString();
		}
	}

	@Override
	public String getActIdByGameId(int gameId) {
		return getActIdByGameId(gameId + "");
	}

	@Override
	public String getActIdByGameId(int gameId, String location) {
		return getActIdByGameId(gameId + "", location);

	}

	@Override
	public ApiResultBean getActBeanByGameId(String gameId, String location) {
		Jedis rdsClient = redisPool.getConnect();
		String theKey = "gameinvite:RMI:configServiceApiResultBean:gameid="+gameId+"|location="+location;
		String cachedContent = rdsClient.get(theKey);
		ApiResultBean apiResBean = null;
		if(cachedContent!=null && !cachedContent.isEmpty()){
			apiResBean = JSON.parseObject(cachedContent, ApiResultBean.class);
			
		}
		if(apiResBean == null){
			apiResBean = rmiObject.getOption(gameId, location);
			cachedContent = JSON.toJSONString(apiResBean);
			rdsClient.setex(theKey, 1*60, cachedContent);
		}
		log.debug("getActBeanByGameId getActBeanByActId");
		log.debug(gameId);
		log.debug(location);
		log.debug(JSON.toJSONString(apiResBean));
		return apiResBean;
	}

	@Override
	public ApiResultBean getActBeanByGameId(String gameId) {
		return getActBeanByGameId(gameId,"中国大陆");
	}

	/**
	 * 返回活动当前是否可用状态
	 */
	@Override
	public boolean isUnderAvailable(int actId) {
		ApiResultBean res = getActBeanByActId(actId);
		log.debug("isUnderAvailable getActBeanByActId");
		log.debug(actId);
		log.debug(JSON.toJSONString(res));
		int now = CommonUtil.getNowTimeStamp();
		if (res.getCode() > 0) {
			return false;
		} else if (parseConfigServiceDateTimeStringToUnixTimeStamp(res
				.getBeginTime()) <= now
				&& parseConfigServiceDateTimeStringToUnixTimeStamp(res
						.getEndTime()) >= now) {
			return true;

		}else{
			return false;
		}
	}

	@Override
	public ApiResultBean getActBeanByActId(int actId) {
		Jedis rdsClient = redisPool.getConnect();
		String theKey = "gameinvite:RMI:configServiceApiResultBean:actid="+actId;
		String cachedContent = rdsClient.get(theKey);
		ApiResultBean apiResBean = null;
		if(cachedContent!=null && !cachedContent.isEmpty()){
			apiResBean = JSON.parseObject(cachedContent, ApiResultBean.class);
			
		}
		if(apiResBean == null){
			apiResBean = rmiObject.getOptionById(actId + "");
			cachedContent = JSON.toJSONString(apiResBean);
			rdsClient.setex(theKey, 1*60, cachedContent);
		}
		log.debug("getActBeanByActId getActBeanByActId");
		log.debug(actId);
		log.debug(JSON.toJSONString(apiResBean));
		return apiResBean;
		
	}

	/**
	 * 通过aid获得每个code在一设备上可使用量的上限
	 */
	@Override
	public int getDeviceApplyCodeCountMax(int actId) {
		ApiResultBean res = getActBeanByActId(actId);
		log.debug("getDeviceApplyCodeCountMax getActBeanByActId");
		log.debug(actId);
		log.debug(JSON.toJSONString(res));
		if(res.getCode()>0){
			return -1;
		}else{
			return res.getRecommandCount();
		}
	}

	/**
	 * 通过aid获得action在设备上可以生成多少个邀请码
	 */
	@Override
	public int getDeviceCreateCountMaxByActId(int actId) {
		ApiResultBean res = getActBeanByActId(actId);
		log.debug("getDeviceCreateCountMaxByActId getActBeanByActId");
		log.debug(actId);
		log.debug(JSON.toJSONString(res));
		if(res.getCode()>0){
			return -1;
		}else{
			return res.getDeviceCount();
		}

	}

	/**
	 * 获得可以生成邀请码的等级
	 * @param actId
	 * @return
	 */
	@Override
	public int getCanGenerateCodeLevel(int actId) {
		ApiResultBean res = getActBeanByActId(actId);
		log.debug("getCanCreateCodeLevel getActBeanByActId");
		log.debug(actId);
		log.debug(JSON.toJSONString(res));
		if(res.getCode()>0){
			return -1;
		}else{
			return res.getLevel();
		}
	}
	


}
