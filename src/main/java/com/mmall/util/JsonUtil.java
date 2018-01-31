package com.mmall.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mmall.pojo.User;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// 取消默认转换时间戳形式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 忽略空bean转Json的错误
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
		// 忽略 在json字符串中存在，但是在Java对象中不存对应属性的情况
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	}

	public static <T> String obj2String(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.warn("Parse Object to String error", e);
			return null;
		}
	}

	public static <T> String obj2StringPretty(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String) obj
					: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			logger.warn("Parse Object to String error", e);
			return null;
		}
	}

	public static <T> T string2Obj(String str, Class<T> clazz) {
		if (StringUtils.isEmpty(str) || clazz == null) {
			return null;
		}
		try {
			return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			logger.warn("Parse String to Object error", e);
			return null;
		}
	}

	public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
		if (StringUtils.isEmpty(str) || typeReference == null) {
			return null;
		}
		try {
			return (T) (typeReference.getType().equals(String.class) ? str
					: objectMapper.readValue(str, typeReference));
		} catch (Exception e) {
			logger.warn("Parse String to Object error", e);
			return null;
		}
	}

	public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
		JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
		try {
			return objectMapper.readValue(str, javaType);
		} catch (Exception e) {
			logger.warn("Parse String to Object error", e);
			return null;
		}
	}

	public static void main(String[] args) {
		User user = new User();
		user.setId(1);
		user.setAnswer("hell");
		user.setPassword("123");
		
		String obj2String = obj2String(user);
		//System.out.println(obj2String);
		
		String str = "{\"id\":2,\"username\":\"陈俊\",\"password\":\"123\",\"email\":null,\"phone\":null,\"question\":null,\"answer\":\"hell\",\"role\":null,\"createTime\":null,\"updateTime\":null}\n" + 
				"";
		User user2 = string2Obj(str, User.class);
		//System.out.println(user2.getUsername());
		List<User> arrayList = new ArrayList<>();
		arrayList.add(user2);
		arrayList.add(user);
		String obj2String2 = obj2String(arrayList);
		//System.out.println(obj2String2);
		String listStr = "[{\"id\":2,\"username\":\"陈俊\",\"password\":\"123\",\"email\":null,\"phone\":null,\"question\":null,\"answer\":\"hell\",\"role\":null,\"createTime\":null,\"updateTime\":null},{\"id\":1,\"username\":null,\"password\":\"123\",\"email\":null,\"phone\":null,\"question\":null,\"answer\":\"hell\",\"role\":null,\"createTime\":null,\"updateTime\":null}]\n" + 
				"";
		
		List<User> arrayList2 = string2Obj(listStr, new TypeReference<List<User>>() {} );
		for (User u : arrayList2) {
			//System.out.println(u.getId());
		}
		List<User> userList = string2Obj(listStr, List.class, User.class);
		for (User u : userList) {
			System.out.println(u.getId());
		}
		
	}
}
