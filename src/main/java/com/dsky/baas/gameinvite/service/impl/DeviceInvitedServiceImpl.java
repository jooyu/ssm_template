package com.dsky.baas.gameinvite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.baas.gameinvite.util.DeviceIdUtil;
import com.dsky.baas.gameinvite.dao.DeviceInvitedMapper;
import com.dsky.baas.gameinvite.model.DeviceInvited;
import com.dsky.baas.gameinvite.service.IDeviceInvitedService;
@Service("iDeviceInvitedService")
public class DeviceInvitedServiceImpl implements IDeviceInvitedService{
	
	private DeviceInvitedMapper deviceInvitedMapper;
	public DeviceInvitedMapper getDeviceInvitedMapper() {
		return deviceInvitedMapper;
	}
	@Autowired
	public void setDeviceInvitedMapper(DeviceInvitedMapper deviceInvitedMapper) {
		this.deviceInvitedMapper = deviceInvitedMapper;
	}

	/**
	 * 通过设备id获得该邀请码的个数
	 * @param  string deviceId ,int gameId, int actId
	 * @author eaves.zhu 2016.8.1
	 * @return 返回邀请码个数
	 */
	@Override
	public int getDeviceCodeCount(String deviceId,int gameId,int actId) {
			int count=0;
			long crc32=DeviceIdUtil.getCRC32(deviceId);
			String tablePostfix=(int) (crc32%10)+"";
			System.out.println(tablePostfix);
			DeviceInvited deviceInvited =new DeviceInvited();
			deviceInvited.setTablePostfix(tablePostfix);
			deviceInvited.setDeviceId(deviceId);
			deviceInvited.setGameId(gameId);
			deviceInvited.setActId(actId);
			System.out.println(deviceInvited);
			DeviceInvited DeviceInvitedRs=deviceInvitedMapper.selectByDevice(deviceInvited);
			if(DeviceInvitedRs==null)
			{
				return count;
				
			}
			else
			{
				count=DeviceInvitedRs.getCount();
				return count;
			}
			
	}
	
	/**
	 * 如果没有记录，那么写入并count数增加1 ,如果有记录那么更新字段count+1
	 * @param gameid，deviceId，actId
	 * @author eaves.zhu 2016.8.1
	 * @return  -1 fail  1 success
	 */
	@Override
	public int getDeviceCountAdd(String deviceId,int gameId,int actId){
		int count=0;
		long crc32=DeviceIdUtil.getCRC32(deviceId);
		String tablePostfix=(int) (crc32%10)+"";
		DeviceInvited deviceInvited =new DeviceInvited();
		deviceInvited.setTablePostfix(tablePostfix);
		deviceInvited.setDeviceId(deviceId);
		deviceInvited.setGameId(gameId);
		deviceInvited.setActId(actId);
		//如果为0 insert
		DeviceInvited DeviceInvitedRs=deviceInvitedMapper.selectByDevice(deviceInvited);
		if(DeviceInvitedRs==null)
		{
			deviceInvited.setCount(1);
			count=deviceInvitedMapper.insert(deviceInvited);
			
		}
		else
		{
			deviceInvited.setCount(DeviceInvitedRs.getCount());
			count=deviceInvitedMapper.updateByDeviceKeySelective(deviceInvited);
			
		}
	
		if(count<0)
		{
			return  -1;
		}
		return 1;
		
	}

}
