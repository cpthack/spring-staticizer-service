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
package com.github.cpthack.project.staticizerservice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.cpthack.project.staticizerservice.bean.ResponseResult;
import com.github.cpthack.project.staticizerservice.utils.JsonHelper;
import com.github.cpthack.project.staticizerservice.utils.MD5SignHelper;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * <b>StaticizerServiceTest.java</b></br>
 * 
 * <pre>
 * 静态服务测试类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 30, 2017 11:54:39 AM
 * @since JDK 1.7
 */
public class StaticizerServiceTest {
	
	public static void main(String[] arg) {
		String serviceUrl = "http://localhost:8080/service/staticizer/pc";
		// pcTest(serviceUrl);
		mobileTest(serviceUrl);
		
	}
	
	private static void mobileTest(String serviceUrl) {
		
		// 构造参数列表
		String url = "https://m.jianzhimao.com/job";
		String timestamp = String.valueOf(System.currentTimeMillis());
		String appKey = "test";
		String appSecret = "xxxx3333xxx";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("url", url);
		paramsMap.put("timestamp", timestamp);
		paramsMap.put("appKey", appKey);
		
		// 自定义添加请求头信息
		Map<String, String> requestHeadersMap = new LinkedHashMap<String, String>();
		requestHeadersMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
		requestHeadersMap.put("Cookie", "ipcity=guangzhou; isw=1; isp=1; ism=1; UM_distinctid=15b991d8b504-0d54f3feabfe08-143d655c-13c680-15b991d8b51109; gr_user_id=759e16e4-e3f9-41c2-a693-e2ee6773bf6f; Hm_lvt_447f87add4dbd73deca17a45d8536dbd=1493304116,1493639091,1493640218,1493734506; JSESSIONID=5CE5267F02C4808AC6D03C39482DE696; gr_session_id_b0d18e1f996f733e=eedd0007-ea93-48c6-ab46-dbff5d37e975; gr_cs1_eedd0007-ea93-48c6-ab46-dbff5d37e975=user_id%3A; CNZZDATA1254075128=1655583782-1492924156-%7C1493908763; Hm_lvt_c48dcbb8f7a6cb176845ad3439189ed0=1492924831,1493389363,1493912467; Hm_lpvt_c48dcbb8f7a6cb176845ad3439189ed0=1493912467; m_location_info=%2C%E5%B9%BF%E5%B7%9E%2C");
		String requestHeaderJson = JsonHelper.toJson(requestHeadersMap);
		paramsMap.put("requestHeaderJson", requestHeaderJson);
		
		// 添加参数签名信息
		String sign = MD5SignHelper.sign(paramsMap, "appSecret", appSecret);
		paramsMap.put("sign", sign);
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			getPageSource(serviceUrl, paramsMap);
			System.out.println("完成第[" + (i + 1) + "]次请求");
		}
		System.out.println("总的请求完成时间, time = [" + (System.currentTimeMillis() - startTime) / 1000 + "] s.");
	}
	
	private static void pcTest(String serviceUrl) {
		
		String url = "https://www.jianzhimao.com";
		String timestamp = String.valueOf(System.currentTimeMillis());
		String appKey = "test";
		String appSecret = "xxxx3333xxx";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("url", url);
		paramsMap.put("timestamp", timestamp);
		paramsMap.put("appKey", appKey);
		String sign = MD5SignHelper.sign(paramsMap, "appSecret", appSecret);
		paramsMap.put("sign", sign);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			getPageSource(serviceUrl, paramsMap);
			System.out.println("完成第[" + (i + 1) + "]次请求");
		}
		System.out.println("总的请求完成时间, time = [" + (System.currentTimeMillis() - startTime) / 1000 + "] s.");
	}
	
	private static void getPageSource(String baseUrl, Map<String, String> paramsMap) {
		OkHttpClient client = new OkHttpClient();
		
		long startTime = System.currentTimeMillis();
		
		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
		for (String name : paramsMap.keySet()) {
			formEncodingBuilder.add(name, paramsMap.get(name));
		}
		RequestBody formBody = formEncodingBuilder.build();
		Request request = new Request.Builder().url(baseUrl).post(formBody).build();
		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.body().string());
			ResponseResult result = JsonHelper.toObject(response.body().string(), ResponseResult.class);
			System.out.println("获取到的返回信息如下：");
			System.out.println("code = [" + result.getCode() + "]");
			System.out.println("msg = [" + result.getMsg() + "]");
			if (null != result.getContent())
				System.out.println("content = [" + result.getContent().get("obj") + "]");
			
		}
		catch (Exception e) {
//			e.printStackTrace();
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + " ms");
	}
}
