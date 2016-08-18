package com.dsky.baas.gameinvite.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dsky.baas.configservice.model.ApiResultBean;
import com.dsky.baas.gameinvite.lib.ApiResultCode;
import com.dsky.baas.gameinvite.lib.ApiResultObject;
import com.dsky.baas.gameinvite.lib.ApiResultPacker;
import com.dsky.baas.gameinvite.model.InvitedCode;
import com.dsky.baas.gameinvite.service.ICodeMessageI18N;
import com.dsky.baas.gameinvite.service.IConfigServiceRMIService;
import com.dsky.baas.gameinvite.service.IDeviceInvitedApplyService;
import com.dsky.baas.gameinvite.service.IDeviceInvitedService;
import com.dsky.baas.gameinvite.service.IInvitedCodeService;
import com.dsky.baas.gameinvite.service.IInvitedPeopleService;
import com.dsky.baas.gameinvite.service.IPointsServiceRMIService;
import com.dsky.baas.gameinvite.service.impl.CodeMessageI18N;
import com.dsky.baas.gameinvite.util.CommonUtil;

/**
 * @author zen.wang
 */

@Controller
@RequestMapping("/v1/invite_code")
public class InvitedCodeController {

	private Logger log = Logger.getLogger(InvitedCodeController.class);

	private ICodeMessageI18N codeMessageI18N;

	private IInvitedCodeService invitedCodeService;
	private IInvitedPeopleService invitedPeopleService;

	private IDeviceInvitedService deviceInvitedService;
	private IDeviceInvitedApplyService deviceInvitedApplyService;

	private IConfigServiceRMIService configServiceRMIService;
	private IPointsServiceRMIService pointsServiceRMIService;

	public ICodeMessageI18N getCodeMessageI18N() {
		return codeMessageI18N;
	}

	@Autowired
	public void setCodeMessageI18N(ICodeMessageI18N codeMessageI18N) {
		this.codeMessageI18N = codeMessageI18N;
	}

	public IPointsServiceRMIService getPointsServiceRMIService() {
		return pointsServiceRMIService;
	}

	@Autowired
	public void setPointsServiceRMIService(
			IPointsServiceRMIService pointsServiceRMIService) {
		this.pointsServiceRMIService = pointsServiceRMIService;
	}

	public IDeviceInvitedApplyService getDeviceInvitedApplyService() {
		return deviceInvitedApplyService;
	}

	@Autowired
	public void setDeviceInvitedApplyService(
			IDeviceInvitedApplyService deviceInvitedApplyService) {
		this.deviceInvitedApplyService = deviceInvitedApplyService;
	}

	public IDeviceInvitedService getDeviceInvitedService() {
		return deviceInvitedService;
	}

	@Autowired
	public void setDeviceInvitedService(
			IDeviceInvitedService deviceInvitedService) {
		this.deviceInvitedService = deviceInvitedService;
	}

	public IInvitedPeopleService getInvitedPeopleService() {
		return invitedPeopleService;
	}

	@Autowired
	public void setInvitedPeopleService(
			IInvitedPeopleService invitedPeopleService) {
		this.invitedPeopleService = invitedPeopleService;
	}

	public IInvitedCodeService getInvitedCodeService() {
		return invitedCodeService;
	}

	@Autowired
	public void setInvitedCodeService(IInvitedCodeService invitedCodeService) {
		this.invitedCodeService = invitedCodeService;
	}

	public IConfigServiceRMIService getConfigServiceRMIService() {
		return configServiceRMIService;
	}

	@Autowired
	public void setConfigServiceRMIService(
			IConfigServiceRMIService configServiceRMIService) {
		this.configServiceRMIService = configServiceRMIService;
	}

	/**
	 * 生成邀请码 step RPC获得游戏的可用活动 --> 获得当前用户在设个设备的这个活动中的邀请码，如果有就返回 -->
	 * 判断此设备是否已经到达邀请码生成的上限 --> 生成邀请码 -->给设备 游戏 活动生成表 记录邀请码+1
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/generator")
	public @ResponseBody ApiResultObject generator(HttpServletRequest req) {

		Map<String, String> cookieMap = CommonUtil.parseHeaderCookie(req);
		if (cookieMap == null) {
			return ApiResultPacker
					.packToApiResultObject(ApiResultCode.AUTH_FAIL,
							codeMessageI18N.getMessage("鉴权失败"));
		}

		log.debug("generator controller cookieMap");
		log.debug(JSON.toJSON(cookieMap));

		String deviceId = req.getParameter("udid");
		if (deviceId == null || deviceId.isEmpty()) {
			deviceId = req.getParameter("UDID");
		}
		if (deviceId == null || deviceId.isEmpty()) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_DEVICE_ID,
					codeMessageI18N.getMessage("不能缺少设备ID参数"));
		}

		String gameIdStr = cookieMap.get("gid");
		log.debug(gameIdStr);
		log.debug("gameIdStr");
		if (gameIdStr == null) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_GAME_ID,
					codeMessageI18N.getMessage("不能缺少游戏ID参数"));
		}
		int gameId;
		try {
			gameId = Integer.parseInt(gameIdStr);
		} catch (Exception e) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_GAME_ID,
					codeMessageI18N.getMessage("不能缺少游戏ID参数"));
		}
		log.debug(gameId);
		log.debug("gameId");

		String playerIdStr = cookieMap.get("uid");
		log.debug(playerIdStr);
		log.debug("playerIdStr");
		if (playerIdStr == null) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_PLAYER_ID,
					codeMessageI18N.getMessage("不能缺少玩家ID参数"));
		}
		int playerId;
		try {
			playerId = Integer.parseInt(playerIdStr);
		} catch (Exception e) {

			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_PLAYER_ID,
					codeMessageI18N.getMessage("不能缺少玩家ID参数"));
		}
		log.debug(playerId);
		log.debug("playerId");

		String code;
		String actIdStr;
		// 去配置服务器查询此游戏的当前活动
		ApiResultBean apiResBean = configServiceRMIService
				.getActBeanByGameId(gameIdStr);
		if (apiResBean.getCode() > 0) { // 发生错误
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.NO_ACT_IS_AVAILABLE,
					codeMessageI18N.getMessage("没有可用的活动"));
		} else {
			actIdStr = apiResBean.getId().toString();
		}
		if (actIdStr == null) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.NO_ACT_IS_AVAILABLE,
					codeMessageI18N.getMessage("没有可用的活动"));
		}
		int actId = Integer.parseInt(actIdStr);

		int canGenerateCodeLevel = apiResBean.getLevel();
		if (canGenerateCodeLevel > 0) { // 限制了生成邀请码的等级
			String levelStr = req.getParameter("level");
			if (levelStr == null || levelStr.isEmpty()) {
				return ApiResultPacker.packToApiResultObject(
						ApiResultCode.MISS_REQUIRE_PARAM_LEVEL,
						codeMessageI18N.getMessage("等级参数错误"));
			}
			int level = 0;
			try {
				level = Integer.parseInt(levelStr);
			} catch (Exception e) {
				return ApiResultPacker.packToApiResultObject(
						ApiResultCode.MISS_REQUIRE_PARAM_LEVEL,
						codeMessageI18N.getMessage("等级参数错误"));
			}
			if (level < canGenerateCodeLevel) {
				return ApiResultPacker.packToApiResultObject(
						ApiResultCode.LEVEL_LOW,
						codeMessageI18N.getMessage("等级过低"));
			}
		}

		// -- 获得当前用户在设个设备的这个活动中的邀请码
		InvitedCode existsInvitedCode = invitedCodeService
				.getInvitedCodeByPlayerIdAndGameIdAndActId(playerId, gameId,
						actId);
		if (existsInvitedCode != null) {
			code = existsInvitedCode.getCode();
		} else {

			int deviceCreateCountMax = apiResBean.getDeviceCount();
			// 判断此设备是否已经到达邀请码生成的上限
			int existsCount = deviceInvitedService.getDeviceCodeCount(deviceId,
					gameId, actId);
			log.debug(deviceCreateCountMax);
			log.debug(existsCount);
			log.debug("device count max");
			if (existsCount >= deviceCreateCountMax) {
				return ApiResultPacker.packToApiResultObject(
						ApiResultCode.CODE_GENERATE_DEVICE_UPPER_LIMIT,
						codeMessageI18N.getMessage("邀请码生成超过上限"));
			}

			// 创建邀请码
			code = invitedCodeService.createInvitedCode(playerId, gameId,
					deviceId, actId);

			// 给device的生成数量 +1
			deviceInvitedService.getDeviceCountAdd(deviceId, gameId, actId);
		}
		// 获得分享icon
		String shareIconUrl = apiResBean.getImgUrl();
		if (shareIconUrl == null)
			shareIconUrl = "";

		// 获得分享title
		String shareTitle = apiResBean.getTitle();
		if (shareTitle == null)
			shareTitle = "";

		// 获得分享details
		String shareDetails = apiResBean.getRedeemDesc();
		if (shareDetails == null)
			shareDetails = "";

		// 获得分享url
		String shareH5url = apiResBean.getH5Url();
		// 处理分享url参数
		if (shareH5url != null) {
			if (-1 == shareH5url.indexOf("?")) {// don't found the ?
				shareH5url += "?invite_code=" + code;
			} else {
				shareH5url += "&invite_code=" + code;
			}
		} else {
			shareH5url = "";
		}

		Map<String, Object> resData = new HashMap<String, Object>();
		resData.put("invite_code", code);
		resData.put("share_h5url", shareH5url);
		resData.put("share_des", shareDetails);
		resData.put("share_title", shareTitle);
		resData.put("share_icon_url", shareIconUrl);
		log.debug(JSON.toJSONString(resData));
		log.debug("generator show data");
		return ApiResultPacker.packToApiResultObject(resData);
	}

	/**
	 * 使用邀请码 step: 获得req邀请码--> 查询邀请码信息，判断邀请码是否存在--> 判断此用户是否在此游戏中已经被邀请-->
	 * -->rpc调用判断此邀请码活动是否还在活动期内--> rpc调用获得游戏的活动的邀请码被使用的次数限制-->
	 * 判断邀请码在此设备上使用次数是否超过限制--> -->给邀请码使用次数+1 -->给游戏 邀请码 使用表 +1 --> TODO rpc
	 * 通知积分服务器给发生了邀请码被使用事件 -->具体加多少分由积分服务器决定-->
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/apply")
	public @ResponseBody ApiResultObject apply(HttpServletRequest req) {

		Map<String, String> cookieMap = CommonUtil.parseHeaderCookie(req);
		if (cookieMap == null) {
			return ApiResultPacker
					.packToApiResultObject(ApiResultCode.AUTH_FAIL,
							codeMessageI18N.getMessage("鉴权失败"));
		}

		String inviteCodeStr = req.getParameter("invite_code");
		if (inviteCodeStr == null || inviteCodeStr.isEmpty()) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_INVITE_CODE,
					codeMessageI18N.getMessage("不能缺少邀请码参数"));
		}

		String playerIdStr = cookieMap.get("uid");
		if (playerIdStr == null || playerIdStr.isEmpty()) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_PLAYER_ID,
					codeMessageI18N.getMessage("不能缺少玩家ID参数"));
		}
		int invitedPlayerId;
		try {
			invitedPlayerId = Integer.parseInt(playerIdStr);
		} catch (Exception e) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_PLAYER_ID,
					codeMessageI18N.getMessage("不能缺少玩家ID参数"));
		}

		String deviceId = req.getParameter("udid");
		if (deviceId == null || deviceId.isEmpty()) {
			deviceId = req.getParameter("UDID");
		}
		if (deviceId == null || deviceId.isEmpty()) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.MISS_REQUIRE_PARAM_DEVICE_ID,
					codeMessageI18N.getMessage("不能缺少设备ID参数"));
		}
		// 新需求：只有在注册的时候邀请码，没有level限制，这句注释掉
		// String level = req.getParameter("level");

		InvitedCode invitedCode = null;
		try {
			invitedCode = invitedCodeService
					.getInvitedCodeByCode(inviteCodeStr); // 获得验证码
		} catch (Exception e) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.NORMAL_ERROR,
					codeMessageI18N.getMessage(e.getMessage()));
		}

		if (invitedCode == null) {
			return ApiResultPacker.packToApiResultObject(
					ApiResultCode.CODE_NOT_EXISTS,
					codeMessageI18N.getMessage("邀请码不存在"));
		} else {

			// 判断用户在否已经在这个游戏里使用了这个邀请码被对方邀请过
			if (invitedPeopleService.isAlreadyInvited(invitedPlayerId,
					invitedCode.getGameId(), invitedCode.getActId())) {
				return ApiResultPacker.packToApiResultObject(
						ApiResultCode.PLAYER_HAVE_BEEN_INVITED,
						codeMessageI18N.getMessage("玩家已经被邀请"));
			}

			// --判断actId对应的活动是否还在活动期内
			if (!configServiceRMIService.isUnderAvailable(invitedCode
					.getActId())) {
				return ApiResultPacker.packToApiResultObject(
						ApiResultCode.ACT_NO_AVAILABLE,
						codeMessageI18N.getMessage("活动当前不可用"));
			}

			ApiResultBean apiResBean = configServiceRMIService
					.getActBeanByActId(invitedCode.getActId());

			// 获得设备 邀请码使用的次数
			int deviceDodeApplyCount = deviceInvitedApplyService
					.getDeviceCodeApplyCount(deviceId, inviteCodeStr);
			if(deviceDodeApplyCount>=1){ // TODO 临时判断代码
				return ApiResultPacker.packToApiResultObject(
						ApiResultCode.CODE_APPLY_DEVICE_UPPER_LIMIT,
						codeMessageI18N.getMessage("邀请码生成超过上限"));
			}
			
			
			// RPC 获得配置服务器配置的允许使用次数
			// --判断邀请码被使用的上限
			int codeApplyCountMax = apiResBean.getRecommandCount(); // 需要到配置服务器读取
			// int codeApplyCountMax = configServiceRMIService
			// .getDeviceApplyCodeCountMax(invitedCode.getActId()); //
			// 需要到配置服务器读取
			boolean isOverApplyCountMax = false;// 是否超过使用上限
			// 获得当前code被使用的次数，并判断邀请码的使用是否超过限制
			int applyCount = invitedCodeService
					.getApplyCountByCode(inviteCodeStr);
			if (applyCount >= codeApplyCountMax) {
				isOverApplyCountMax = true;
			}

			// 调用邀请关系service增加邀请关系
			invitedPeopleService.createInvitedPeople(invitedPlayerId,
					invitedCode.getPlayerId(), invitedCode.getGameId(),
					invitedCode.getActId());
			// 给邀请码使用次数+1
			invitedCodeService.addApplyCountByCode(inviteCodeStr);

			// 给游戏 邀请码 使用表 +1
			deviceInvitedApplyService.getDeviceCountApplyAdd(deviceId,
					inviteCodeStr);

			// TODO 最后，给双发加积分 -- 通过RPC调用
			int shouldA = apiResBean.getPromoterA(); // 邀请码拥有者
			int addPointsA = shouldA;
			if (isOverApplyCountMax)
				addPointsA = 0;
			int shouldB = apiResBean.getPromoterB();
			int addPointsB = shouldB;
			pointsServiceRMIService.applyCode(invitedCode.getPlayerId(),
					shouldA, addPointsA, invitedPlayerId, shouldB, addPointsB,
					invitedCode.getGameId(), invitedCode.getActId(),
					invitedCode.getCode());

			return ApiResultPacker.packToApiResultObject(0, "");
		}

	}

}
