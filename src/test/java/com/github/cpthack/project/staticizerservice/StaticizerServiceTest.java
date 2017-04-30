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

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * <b>StaticizerServiceTest.java</b></br>
 * 
 * <pre>
 * TODO(这里用一句话描述这个类的作用)
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 30, 2017 11:54:39 AM
 * @since JDK 1.7
 */
public class StaticizerServiceTest {
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			String url = "http://localhost:8080/service/staticizer/pc?url=https://m.jianzhimao.com";
			pcTest(url);
			System.out.println("完成第[" + (i + 1) + "]次请求");
		}
	}
	
	public static void pcTest(String url) {
		OkHttpClient client = new OkHttpClient();
		long startTime = System.currentTimeMillis();
		Request request = new Request.Builder().url(url).get().build();
		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.body().string());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + " ms");
	}
}
