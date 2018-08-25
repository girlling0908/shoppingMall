package cn.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.E3Result;
import cn.e3mall.common.JsonUtils;
import cn.e3mall.sso.service.TokenService;

@Controller
public class TokenController {

	@Autowired
	private TokenService tokenService;

	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	private String getUserByToken(@PathVariable String token,String callback) {
		E3Result userByToken = tokenService.getUserByToken(token);
		//是否为jsonp请求
		if(StringUtils.isNotBlank(callback))
		{
			String strResult=callback+"("+JsonUtils.objectToJson(userByToken)+");";
		     return strResult;
		}
		return  JsonUtils.objectToJson(userByToken);
	}
}
