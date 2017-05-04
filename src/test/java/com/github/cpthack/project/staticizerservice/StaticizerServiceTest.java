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
		
		String baseUrl = "http://localhost:8080/service/staticizer/pc";
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
		for (int i = 0; i < 10; i++) {
			Test(baseUrl, paramsMap);
			System.out.println("完成第[" + (i + 1) + "]次请求");
		}
		System.out.println("总的请求完成时间, time = [" + (System.currentTimeMillis() - startTime)/1000 + "] s.");
	}
	
	public static void Test(String baseUrl, Map<String, String> paramsMap) {
		OkHttpClient client = new OkHttpClient();
		long startTime = System.currentTimeMillis();
		
		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
		for (String name : paramsMap.keySet()) {
			formEncodingBuilder.add(name, paramsMap.get(name));
		}
		RequestBody formBody = formEncodingBuilder.build();
		Request request = new Request.Builder().url(baseUrl).get().post(formBody).build();
		try {
			Response response = client.newCall(request).execute();
			ResponseResult result = JsonHelper.toObject(response.body().string(), ResponseResult.class);
			System.out.println("获取到的返回信息如下：");
			System.out.println("code = [" + result.getCode() + "]");
			System.out.println("msg = [" + result.getMsg() + "]");
			if (null != result.getContent())
				System.out.println("content = [" + result.getContent() + "]");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + " ms");
	}
}
