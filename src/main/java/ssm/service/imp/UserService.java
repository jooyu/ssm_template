package ssm.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.dao.User;
import ssm.mapper.UserMapper;
import ssm.service.IUserService;

/**
 * 
 * create by zhuyu
 */
@Service("userService")
public class UserService  implements IUserService {
	@Autowired
	private UserMapper userMapper;
	//专注业务层面
	public User getInfo() {
		
		return userMapper.getUserName();
		//获取用户信息 通过Usermapper实现具体的crud操作
		
		
	}






}
