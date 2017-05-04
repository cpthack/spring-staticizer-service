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
package com.github.cpthack.project.staticizerservice.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.cpthack.project.staticizerservice.service.StaticizerService;
import com.github.cpthack.project.staticizerservice.utils.JsonHelper;

/**
 * <b>StaticizerControllerHelper.java</b></br>
 * 
 * <pre>
 * StaticizerController的逻辑处理辅助类，主要用于调度各个细粒度的service
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 4, 2017 9:34:23 PM
 * @since JDK 1.7
 */
@Component
public class StaticizerControllerHelper {
	
	@Autowired
	private StaticizerService staticizerService;
	
	public String pc(String url, String requestHeaderJson) {
		
		if (null == requestHeaderJson) {
			return staticizerService.getPageSource(url);
		}
		
		return getPageSource(url, requestHeaderJson);
	}
	
	public String mobile(String url, String requestHeaderJson) {
		
		if (null == requestHeaderJson) {
			return staticizerService.getMobilePageSource(url);
		}
		
		return getPageSource(url, requestHeaderJson);
	}
	
	protected String getPageSource(String url, String requestHeaderJson) {
		LinkedHashMap<String, String> requestHeaders = JsonHelper.toObject(requestHeaderJson, LinkedHashMap.class);
		return staticizerService.getPageSource(url, requestHeaders);
	}
}
