package com.mmall.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtil {
	private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
	private final static String COOKIE_DOMAIN = ".happymmall.com";
	private final static String COOKIE_NAME = "mmall_login_token";
	
	public static String readLoginToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				logger.info("read cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
				if(StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
					logger.info("return cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
					return cookie.getValue();
				}
			}
		}
		return null;
		
	}
	
	
	
	
}
