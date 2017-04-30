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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <b>StaticizerApplication.java</b></br>
 * 
 * <pre>
 * 程序启动入口类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 29, 2017 10:57:01 AM
 * @since JDK 1.7
 */
@SpringBootApplication
public class StaticizerApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(StaticizerApplication.class, args);
		
	}
}
