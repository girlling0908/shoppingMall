package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;


//用户注册
@Controller
public class RegisterController {

	
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/page/register")
	private String register(){
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	private E3Result checkData(@PathVariable String param,@PathVariable int type){
		return registerService.checkData(param, type);
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	private E3Result register(TbUser user){
		E3Result result = registerService.register(user);
	    return result;
	}
}
