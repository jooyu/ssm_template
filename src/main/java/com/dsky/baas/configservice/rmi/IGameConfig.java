package com.dsky.baas.configservice.rmi;

import com.dsky.baas.configservice.model.ApiResultBean;

public interface IGameConfig {
	public boolean test();
	public ApiResultBean getOption(String gameId,String location);
	public ApiResultBean getOptionById(String id);


}

