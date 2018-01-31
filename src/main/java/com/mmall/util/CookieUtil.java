package com.mmall.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtil {
	private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
	private final static String COOKIE_DOMAIN = ".happymmall.com";
	private final static String COOKIE_NAME = "mmall_login_token";

	public static String readLoginToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				logger.info("read cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
				if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
					logger.info("return cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
					return cookie.getValue();
				}
			}
		}
		return null;

	}

	public static void writeLoginToken(HttpServletResponse response, String token) {
		Cookie cookie = new Cookie(COOKIE_NAME, token);
		cookie.setDomain(COOKIE_DOMAIN);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 60 * 24 * 365);// 单位秒，如果是-1：永久
		logger.info("write cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
		response.addCookie(cookie);
	}

	public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
					cookie.setDomain(COOKIE_DOMAIN);
					cookie.setPath("/");
					cookie.setMaxAge(0);// 为0，删除
					logger.info("del cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
					response.addCookie(cookie);
					return;
				}
			}
		}

	}

}
