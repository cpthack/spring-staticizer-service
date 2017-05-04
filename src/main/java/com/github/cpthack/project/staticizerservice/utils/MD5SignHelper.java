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

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * <b>MD5SignHelper.java</b></br>
 * 
 * <pre>
 * MD5签名和验签
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 3, 2017 8:41:57 PM
 * @since JDK 1.7
 */
public class MD5SignHelper {
	
	/**
	 * 
	 * <b>md5</b> <br/>
	 * <br/>
	 * 
	 * 将字符串MD5加码 生成32位md5码<br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param inStr
	 * @return String
	 *
	 */
	public static String md5(String inStr) {
		try {
			return DigestUtils.md5Hex(inStr.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误");
		}
	}
	
	/**
	 * 
	 * <b>sign</b> <br/>
	 * <br/>
	 * 
	 * 签名字符串<br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param params
	 * @param appSecretName
	 * @param appSecret
	 * @return String
	 *
	 */
	public static String sign(Map<String, String> params, String appSecretName, String appSecret) {
		StringBuilder valueSb = new StringBuilder();
		params.put(appSecretName, appSecret);
		// 将参数以参数名的字典升序排序
		Map<String, String> sortParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entrys = sortParams.entrySet();
		// 遍历排序的字典,并拼接value1+value2......格式
		for (Entry<String, String> entry : entrys) {
			valueSb.append(entry.getValue());
		}
		params.remove(appSecretName);
		return md5(valueSb.toString());
	}
	
}
