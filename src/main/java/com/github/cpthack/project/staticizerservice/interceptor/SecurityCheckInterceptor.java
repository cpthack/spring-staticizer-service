/**
 * Copyright (c) 2013-2020, cpthack 成佩涛 (cpt@jianzhimao.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cpthack.project.staticizerservice.interceptor;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.cpthack.project.staticizerservice.config.APIConfig;
import com.github.cpthack.project.staticizerservice.exception.AssertHelper;
import com.github.cpthack.project.staticizerservice.utils.MD5SignHelper;
import com.github.cpthack.project.staticizerservice.utils.ResponseHelper;

/**
 * <b>SecurityCheckInterceptor.java</b></br>
 * 
 * <pre>
 * 接口安全校验统一拦截器
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 3, 2017 7:59:28 PM
 * @since JDK 1.7
 */
@Component
public class SecurityCheckInterceptor implements HandlerInterceptor {
	
	private static final Logger	logger = LoggerFactory.getLogger(SecurityCheckInterceptor.class);
	
	@Autowired
	private APIConfig			apiConfig;
	
	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		/**
		 * 解析请求
		 */
		String url = request.getRequestURL().toString();// 请求域名
		String method = request.getMethod();// 请求方法
		String uri = request.getRequestURI();// 请求地址
		String queryString = request.getQueryString();// 请求地址中的参数
		Map<String, String[]> ParameterMap = request.getParameterMap();// 请求体中所有的参数集合
		
		Map<String, String> paramMap = new HashMap<String, String>();// 转化参数集合
		logger.debug(String.format("请求参数, "
				+ "url: %s, method: %s, uri: %s, params: %s", url, method, uri, queryString));
		for (String name : ParameterMap.keySet()) {
			
			// 拼接参数值字符串并进行utf-8解码，防止中文乱码
			paramMap.put(name, URLDecoder.decode(ParameterMap.get(name)[0], "UTF-8"));
			
			// logger.debug("requestBody中的请求数据，KEY = [" + name + "]");
			// logger.debug("requestBody中的请求数据，VALUE = [" + ParameterMap.get(name)[0] + "]");
		}
		
		String appKey = paramMap.get(apiConfig.getAppKeyName());
		AssertHelper.notBlank(appKey, "appkey不能为空.");
		String appSecret = apiConfig.getAppSecret(appKey);
		logger.info("appKey = [" + appKey + "],appSecret =[" + appSecret + "]");
		if (!verify(appSecret, paramMap)) {
			ResponseHelper.response("403");
		}
		
		return true;
	}
	
	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}
	
	/**
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
	public boolean verify(String appSecret, Map<String, String> paramMap) throws Exception {
		
		String sign = paramMap.get(apiConfig.getSignName());
		if (sign == null) {
			ResponseHelper.failure("请求中没有带签名");
		}
		if (paramMap.get(apiConfig.getTimestampName()) == null) {
			ResponseHelper.failure("请求中没有带时间戳");
		}
		
		paramMap.remove(apiConfig.getSignName());// 移除签名数据，不加入签名参数集合中
		
		paramMap.put(apiConfig.getAppSecretName(), appSecret);// 加入密钥值
		
		// 将参数以参数名的字典升序排序
		Map<String, String> sortParams = new TreeMap<String, String>(paramMap);
		Set<Entry<String, String>> entrys = sortParams.entrySet();
		
		// 遍历排序的字典,并拼接value1+value2......格式
		StringBuilder valueSb = new StringBuilder();
		for (Entry<String, String> entry : entrys) {
			valueSb.append(entry.getValue());
		}
		
		String mysign = MD5SignHelper.md5(valueSb.toString());
		if (mysign.equals(sign)) {
			return true;
		}
		else {
			return false;
		}
		
	}
}
