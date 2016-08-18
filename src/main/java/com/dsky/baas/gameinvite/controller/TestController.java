package com.dsky.baas.gameinvite.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dsky.baas.configservice.model.ApiResultBean;
import com.dsky.baas.configservice.rmi.IGameConfig;
import com.dsky.baas.gameinvite.service.ICodeMessageI18N;
import com.dsky.baas.gameinvite.service.IDeviceInvitedService;
import com.dsky.baas.gameinvite.service.IIdGeneratorService;
import com.dsky.baas.gameinvite.service.IPointsServiceRMIService;

 

@Controller
@RequestMapping("/test")
public class TestController implements ApplicationContextAware{
	private final Logger log = Logger.getLogger(TestController.class);
	private IIdGeneratorService idGeneratorService;
	private IDeviceInvitedService  iDeviceInvitedService;
	private ICodeMessageI18N codeMsgI18n;
	private IPointsServiceRMIService pointsServiceRMIService;
	private ApplicationContext ac;
	
	

	
	public IPointsServiceRMIService getPointsServiceRMIService() {
		return pointsServiceRMIService;
	}
	@Autowired
	public void setPointsServiceRMIService(IPointsServiceRMIService pointsServiceRMIService) {
		this.pointsServiceRMIService = pointsServiceRMIService;
	}
	public ICodeMessageI18N getCodeMsgI18n() {
		return codeMsgI18n;
	}
	@Autowired
	public void setCodeMsgI18n(ICodeMessageI18N codeMsgI18n) {
		this.codeMsgI18n = codeMsgI18n;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}
	
	public IDeviceInvitedService getiDeviceInvitedService() {
		return iDeviceInvitedService;
	}
	@Autowired
	public void setiDeviceInvitedService(IDeviceInvitedService iDeviceInvitedService) {
		this.iDeviceInvitedService = iDeviceInvitedService;
	}

	public IIdGeneratorService getIdGeneratorService() {
		return idGeneratorService;
	}

	@Autowired
	public void setIdGeneratorService(IIdGeneratorService idGeneratorService) {
		this.idGeneratorService = idGeneratorService;
	}
	
	@RequestMapping("/rmi2")
	public String rmi2(HttpServletRequest req){
		pointsServiceRMIService.applyCode(1, 2, 1, 2, 2, 2, 1, 1, "aaa");
		
		return null;
	}
	
	@RequestMapping("/rmi")
	public String rmi(HttpServletRequest req){
		IGameConfig rmi = (IGameConfig)ac.getBean("configServerRMIService");
		ApiResultBean apiBean = rmi.getOption("963258741N","中国大陆");
		
		
		
		log.debug(JSON.toJSON(apiBean));
		
		
//		RequestContext requestContext = new RequestContext(req);
		String msg = codeMsgI18n.getMessage("该游戏上没有进行活动");
		
		log.debug(msg);
		log.debug("language");
		return "123";
	}
	
	@RequestMapping("/show")
	public String show(HttpServletRequest req){
		System.out.println("hello~~~~~~~~~~~");
		//DeviceInvitedService deviceInvitedService=new DeviceInvitedService();
    	int codecount=iDeviceInvitedService.getDeviceCodeCount("16CD9C25-D889-4C1B-9C93-7C5E0BD48567",3333,1);
    	System.out.println("code个数"+codecount);
    
    	iDeviceInvitedService.getDeviceCountAdd("16CD9C25-D889-4C1B-9C93-7C5E0BD48567",3333,1);
    	
	

		return "testShow";
		
	}

	



}
