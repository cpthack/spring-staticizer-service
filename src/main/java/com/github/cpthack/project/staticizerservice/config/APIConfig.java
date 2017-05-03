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
package com.github.cpthack.project.staticizerservice.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <b>APIConfig.java</b></br>
 * 
 * <pre>
 * API配置文件读取类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 3, 2017 2:23:16 PM
 * @since JDK 1.7
 */
@Configuration
@ConfigurationProperties(prefix = "apiConfig") // 接收api.yml中的apiConfig下面的属性
public class APIConfig {
	
	private String					  name;							   	// 接收配置文件中name里面的属性值
	
	private List<Map<String, String>> keySecretMap = new ArrayList<>(); // 接收配置文件中keySecretMap里面的属性值

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, String>> getKeySecretMap() {
		return keySecretMap;
	}

	public void setKeySecretMap(List<Map<String, String>> keySecretMap) {
		this.keySecretMap = keySecretMap;
	}
	
}
