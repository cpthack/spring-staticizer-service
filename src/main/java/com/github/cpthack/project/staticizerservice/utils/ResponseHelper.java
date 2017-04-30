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

import com.github.cpthack.project.staticizerservice.bean.ResponseResult;
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
public class ResponseHelper {
	
	public static ResponseResult failure(String msg) {
		return new ResponseResult().setCode("500").setMsg(msg);
	}
	
	public static void Success(Object obj) {
		throw new BussinessException(obj);
	}
	
	public static void success(String key,Object obj){
		
	}
}
