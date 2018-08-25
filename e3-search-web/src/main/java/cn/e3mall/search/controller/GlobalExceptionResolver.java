package cn.e3mall.search.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


//定义一个全局异常处理器
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	
	private static final Logger logger=Logger.getLogger(GlobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//打印到控制台
		ex.printStackTrace();
		//写日志
		logger.debug("系统出错误了。。。");
		logger.info("出错误了");
		logger.error("出错了",ex);
		//发邮件，发短信
		
		
		//显示错误页面
		ModelAndView model=new ModelAndView();
		model.setViewName("error/exception");
		
		return model;
	}

}
