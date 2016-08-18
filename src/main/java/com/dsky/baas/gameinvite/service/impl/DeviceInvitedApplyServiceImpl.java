package com.dsky.baas.gameinvite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.util.DeviceIdUtil;
import com.dsky.baas.gameinvite.dao.DeviceInvitedApplyMapper;
import com.dsky.baas.gameinvite.model.DeviceInvitedApply;
import com.dsky.baas.gameinvite.service.IDeviceInvitedApplyService;
@Service("iDeviceInvitedApplyService")
public class DeviceInvitedApplyServiceImpl implements IDeviceInvitedApplyService{
	
	private DeviceInvitedApplyMapper deviceInvitedApplyMapper;
	
	public DeviceInvitedApplyMapper getDeviceInvitedApplyMapper() {
		return deviceInvitedApplyMapper;
	}
	@Autowired
	public void setDeviceInvitedApplyMapper(
			DeviceInvitedApplyMapper deviceInvitedApplyMapper) {
		this.deviceInvitedApplyMapper = deviceInvitedApplyMapper;
	}
	

	/**
	 * 通过设备id和code获得该邀请码的个数
	 * @param  string deviceId ,String code
	 * @author eaves.zhu 2016.8.11
	 * @return 返回邀请码在该设备的个数
	 */
	
	@Override
	public int getDeviceCodeApplyCount(String deviceId, String code) {
		int count=0;
		long crc32=DeviceIdUtil.getCRC32(deviceId);
		String tablePostfix=(int) (crc32%10)+"";
		DeviceInvitedApply deviceInvitedApply =new DeviceInvitedApply();
		deviceInvitedApply.setTablePostfix(tablePostfix);
		deviceInvitedApply.setDeviceId(deviceId);
		deviceInvitedApply.setCode(code);
		
		//如果为0 insert
		DeviceInvitedApply DeviceInvitedApplyRs=deviceInvitedApplyMapper.selectByDeviceAndCode(deviceInvitedApply);
	
		if(DeviceInvitedApplyRs==null)
		{
			return count;
			
		}
		else
		{
			return DeviceInvitedApplyRs.getApplyCount();
			
		}
	}
	/**
	 * 如果没有记录，那么写入并apply_count数增加1 ,如果有记录那么更新字段apply_count+1
	 * @param string deviceId，String code
	 * @author eaves.zhu 2016.8.11
	 * @return  -1 fail  1 success
	 */
	@Override
	public int getDeviceCountApplyAdd(String deviceId, String code) {
		int count=0;
		long crc32=DeviceIdUtil.getCRC32(deviceId);
		String tablePostfix=(int) (crc32%10)+"";
		DeviceInvitedApply deviceInvitedApply =new DeviceInvitedApply();
		deviceInvitedApply.setTablePostfix(tablePostfix);
		deviceInvitedApply.setDeviceId(deviceId);
		deviceInvitedApply.setCode(code);
		
		//如果为0 insert
		DeviceInvitedApply DeviceInvitedApplyRs=deviceInvitedApplyMapper.selectByDeviceAndCode(deviceInvitedApply);
		
		if(DeviceInvitedApplyRs==null)
		{
			deviceInvitedApply.setApplyCount(1);
			count=deviceInvitedApplyMapper.insert(deviceInvitedApply);
			
		}
		else
		{
			deviceInvitedApply.setApplyCount(count);
			count=deviceInvitedApplyMapper.updateByDeviceKeyAndCodeSelective(deviceInvitedApply);
			
		}
	
		if(count<0)
		{
			return  -1;
		}
		return 1;
	}




}
