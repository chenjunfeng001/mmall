package com.mmall.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 全局异常处理类
 * 
 * @author lichen
 *
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) {
		logger.error("{} Exception", httpServletRequest.getRequestURI(), e);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

		ModelAndView modelAndView = new ModelAndView(jsonView);
		modelAndView.addObject("status", ResponseCode.ERROR.getCode());
		modelAndView.addObject("msg", "接口异常，请查看服务器端日志异常信息");
		modelAndView.addObject("data", e.toString());
		return modelAndView;
	}

}
