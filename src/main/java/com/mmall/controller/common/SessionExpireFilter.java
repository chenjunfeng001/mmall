package com.mmall.controller.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;

public class SessionExpireFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String loginToken = CookieUtil.readLoginToken(httpServletRequest);
		if(StringUtils.isNotEmpty(loginToken)) {
			String userJsonStr = RedisPoolUtil.get(loginToken);
			User user = JsonUtil.string2Obj(userJsonStr, User.class);
			if(user !=null) {
				//user不为空，重置session过期时间
				RedisPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
			}
		}
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {

	}

}
