package ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ssm.service.IUserService;
/**
 * 
 * create by zhuyu  
 * 只做一个获取数据的流程
 */
@Controller
@RequestMapping("/user")
public class UserController  {
	@Autowired
	private IUserService userService;
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public String hello()
	{
		return "hello";
		
	}

//找到users.jsp
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView getTeemo() {
		ModelAndView modelAndView = new ModelAndView();
		//获得username
		modelAndView.addObject("userName", userService.getInfo().getName());
		modelAndView.setViewName("user");
		return modelAndView;
	}



}
