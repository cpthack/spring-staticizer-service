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
package com.github.cpthack.project.staticizerservice.service;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

import com.github.cptahck.commons.staticizer.core.StaticizerClient;
import com.github.cptahck.commons.staticizer.core.StaticizerClientFactory;

/**
 * <b>StaticizerService.java</b></br>
 * 
 * <pre>
 * 静态化业务逻辑处理类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 29, 2017 10:46:13 PM
 * @since JDK 1.7
 */
@Service
public class StaticizerService {
	private final static StaticizerClient client = StaticizerClientFactory.getClient();
	
	public String getPageSource(String url) {
		return getPageSource(url, null);
	}
	
	public String getPageSource(String url, LinkedHashMap<String, String> requestHeaders) {
		return client.getPageSource(url, requestHeaders).getPageSource();
	}
	
	public String getMobilePageSource(String url) {
		return client.getMobilePageSource(url).getPageSource();
	}
}
