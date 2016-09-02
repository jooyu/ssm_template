package com.dsky.baas.configservice.rmi;

import com.dsky.baas.configservice.model.ApiResultBean;

//这里写你调用的人的接口
public interface IGameConfig {
	public boolean test();
	public ApiResultBean getOption(String gameId,String location);
	public ApiResultBean getOptionById(String id);


}

