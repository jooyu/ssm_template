package ssm.service;

import ssm.dao.User;

public interface IUserService {
	
	//获得所有用户信息，业务层面
	public User getInfo();

}