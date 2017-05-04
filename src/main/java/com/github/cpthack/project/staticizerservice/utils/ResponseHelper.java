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
package com.github.cpthack.project.staticizerservice.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.cpthack.project.staticizerservice.bean.ResponseResult;
import com.github.cpthack.project.staticizerservice.config.APIConfig;
import com.github.cpthack.project.staticizerservice.exception.BussinessException;

/**
 * <b>ResponseHelper.java</b></br>
 * 
 * <pre>
 * 请求返回辅助工具
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 29, 2017 9:36:15 PM
 * @since JDK 1.7
 */
@Component
public class ResponseHelper {
	
	@Autowired
	private APIConfig		 apiConfig;
	
	private static APIConfig apiConfigStatic;
	
	@PostConstruct
	public void init() {
		ResponseHelper.apiConfigStatic = apiConfig;
	}
	
	/**
	 * 
	 * <b>failure</b> <br/>
	 * <br/>
	 * 
	 * 返回错误信息，默认错误状态码500<br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param msg
	 *            错误信息
	 * 
	 *
	 */
	public static void failure(String msg) {
		throw new BussinessException("500", msg);
	}
	
	/**
	 * 
	 * <b>response</b> <br/>
	 * <br/>
	 * 
	 * 根据状态码返回特殊信息，状态码在application.yml文件中进行配置 <br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param code
	 *            状态码
	 *
	 */
	public static void response(String code) {
		throw new BussinessException(code, apiConfigStatic.getCodeMsg(code));
	}
	
	/**
	 * 
	 * <b>success</b> <br/>
	 * <br/>
	 * 
	 * 返回数据，默认将obj对象存储在返回体中content的obj属性中<br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param obj
	 *            void
	 *
	 */
	public static void success(Object obj) {
		throw new BussinessException(obj);
	}
	
	public static void success(String key, Object obj) {
		ResponseResult result = new ResponseResult();
		result.setContent(key, obj);
		BussinessException excepiton = new BussinessException();
		excepiton.setResult(result);
		throw excepiton;
	}
	
}
