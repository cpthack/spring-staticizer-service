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
package com.github.cpthack.project.staticizerservice.exception;

import org.apache.commons.lang3.StringUtils;

import com.github.cpthack.project.staticizerservice.utils.ResponseHelper;

/**
 * <b>AssertHelper.java</b></br>
 * 
 * <pre>
 * 断言式校验辅助工具
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 4, 2017 2:34:01 PM
 * @since JDK 1.7
 */
public class AssertHelper {
	
	/**
	 * 
	 * <b>notNull </b> <br/>
	 * 
	 * When the param "object" is null ,Then throw new BussinessException(message). <br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param object
	 * @param message
	 *            void
	 *
	 */
	public static void notNull(Object object, String message) {
		if (null == object) {
			ResponseHelper.failure(message);
		}
	}
	
	/**
	 * 
	 * <b>notBlank </b> <br/>
	 * 
	 * if a CharSequence is whitespace, empty ("") or null Then "throw new
	 * BussinessException(message)".<br/>
	 * 
	 * <pre>
	 * AssertHelper.notBlank(null)      = throw new BussinessException(message)
	 * AssertHelper.notBlank("")        = throw new BussinessException(message)
	 * AssertHelper.notBlank(" ")       = throw new BussinessException(message)
	 * AssertHelper.notBlank("bob")     = true
	 * AssertHelper.notBlank("  bob  ") = true
	 * </pre>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param cs
	 * @param message
	 *            void
	 *
	 */
	public static boolean notBlank(CharSequence cs, String message) {
		if (StringUtils.isBlank(cs)) {
			ResponseHelper.failure(message);
		}
		return true;
	}
	
	/**
	 * 
	 * <b>isTrue </b> <br/>
	 * 
	 * When the param "isTrue" is flase ,Then throw new BussinessException(message). <br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param isTrue
	 * @param message
	 *            void
	 *
	 */
	public static void isTrue(boolean isTrue, String message) {
		if (!isTrue) {
			ResponseHelper.failure(message);
		}
	}
}
