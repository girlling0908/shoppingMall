package cn.e3mall.order.Intecepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.CookieUtils;
import cn.e3mall.common.E3Result;
import cn.e3mall.common.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

public class LoginIntecepter implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private CartService cartService;
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 从request中取Cookie
		String token = CookieUtils.getCookieValue(request, "token");
		// 如果不存在，用户登录

		if (StringUtils.isBlank(token)) {
			response.sendRedirect("http//localhost:8088/page/login?redirect=" + request.getRequestURI());
			return false;
		}
		// 登录之后跳转到当前请求的url
		E3Result result = tokenService.getUserByToken(token);
		// 如果token存在，需要调用SSO系统服务，根据token取用户信息
		if (result.getStatus() != 200) {
			// 取不到
			response.sendRedirect("http//localhost:8088/page/login?redirect=" + request.getRequestURI());
			return false;
		}

		// 取得到
		TbUser user = (TbUser) result.getData();
		request.setAttribute("user", user);
		
		String jsonCartList = CookieUtils.getCookieValue(request, "cart",true);
		
		if(jsonCartList!=null)
		{
			//合并购物车
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCartList, TbItem.class));
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
