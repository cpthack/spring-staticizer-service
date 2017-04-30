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

/**
 * <b>BussinessException.java</b></br>
 * 
 * <pre>
 * 业务逻辑异常类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 29, 2017 9:54:43 PM
 * @since JDK 1.7
 */
public class BussinessException extends ApplicationException {
	
	private static final long serialVersionUID = -873631143382257801L;
	
	private Object obj = null;
	
	public BussinessException(Throwable t) {
		super(t);
	}
	
	public BussinessException(String message) {
		super(message);
	}
	
	public BussinessException(String message,Throwable t) {
		super(message,t);
	}
	
	
	public BussinessException(Object obj){
		this.obj=obj;
	}

	public Object getObj() {
		return obj;
	}
	
}
