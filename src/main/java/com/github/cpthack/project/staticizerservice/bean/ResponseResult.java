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
package com.github.cpthack.project.staticizerservice.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>ResponseResult.java</b></br>
 * 
 * <pre>
 * 请求返回实体
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 29, 2017 9:32:37 PM
 * @since JDK 1.7
 */
public class ResponseResult {
	
	private String					code   = "200";
	
	private String				msg	   = "Request is successfully!";
	
	private Map<String, Object>	contentMap = new HashMap<String, Object>();
	
	public String getCode() {
		return code;
	}
	
	public ResponseResult setCode(String code) {
		this.code = code;
		return this;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public ResponseResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	
	public Map<String, Object> getContent() {
		return contentMap;
	}
	
	public ResponseResult setContent(Object obj) {
		contentMap.put("obj", obj);
		return this;
	}
	
	public ResponseResult setContent(String key,Object obj){
		contentMap.put(key, obj);
		return this;
	}
	
}
