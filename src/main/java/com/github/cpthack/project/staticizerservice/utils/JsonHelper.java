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

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <b>JsonHelper.java</b></br>
 * 
 * <pre>
 * Json处理辅助工具类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 1, 2017 6:47:29 PM
 * @since JDK 1.7
 */
public class JsonHelper {
	
	private static final ObjectMapper mapper = new ObjectMapper();;
	
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException("转换json字符失败!");
		}
	}
	
	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return (T) mapper.readValue(json, clazz);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("将json字符转换为对象时失败!");
		}
	}
}
