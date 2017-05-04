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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <b>CustomWebMvcConfigurerAdapter.java</b></br>
 * 
 * <pre>
 * 拦截器统一管理类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 3, 2017 8:03:31 PM
 * @since JDK 1.7
 */
@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	
	@Autowired
	private SecurityCheckInterceptor securityCheckInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// 这里添加要拦截的Controller请求，我这里设置的拦截所有请求(注意，拦截器默认只拦截Controller的请求)
		registry.addInterceptor(securityCheckInterceptor).addPathPatterns("/**");
		
	}
	
}
