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
package com.github.cpthack.project.staticizerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.cpthack.project.staticizerservice.utils.ResponseHelper;

/**
 * <b>StaticizerController.java</b></br>
 * 
 * <pre>
 * 静态化服务控制器(静态化实现 对外Rest API类)，职责：
 * 1、只负责处理接受参数；
 * 2、规范已知的参数规则；
 * 3、返回结果值；
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 29, 2017 3:53:34 PM
 * @since JDK 1.7
 */
@RestController
public class StaticizerController {
	
	@Autowired
	private StaticizerControllerHelper controllerHelper;
	
	@RequestMapping(value = "${routes.controller.staticizer.pc}",
	        method = { RequestMethod.POST, RequestMethod.GET })
	public void pc(String url, String requestHeaderJson) {
		String result = controllerHelper.pc(url, requestHeaderJson);
		ResponseHelper.success(result);
		
		// response.setContentType("text/html;charset=UTF-8");
		// try {
		// response.getWriter().write(result);
		// response.getWriter().close();
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// }
	}
	
	@RequestMapping(value = "${routes.controller.staticizer.mobile}",
	        method = { RequestMethod.POST, RequestMethod.GET })
	public void mobile(String url, String requestHeaderJson) {
		
		String result = controllerHelper.mobile(url, requestHeaderJson);
		ResponseHelper.success(result);
	}
	
}
