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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.cpthack.project.staticizerservice.bean.ResponseResult;

/**
 * <b>CommonExceptionAdvice.java</b></br>
 * 
 * <pre>
 * 全局异常处理总控制类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date Apr 29, 2017 9:16:34 PM
 * @since JDK 1.7
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionAdvice {
	private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	// /**
	// * 400 - Bad Request
	// */
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// @ExceptionHandler(MissingServletRequestParameterException.class)
	// public ResponseResult
	// handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
	// logger.error("缺少请求参数", e);
	// return null;
	// }
	
	// /**
	// * 400 - Bad Request
	// */
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// @ExceptionHandler(HttpMessageNotReadableException.class)
	// public ResponseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException
	// e) {
	// logger.error("参数解析失败", e);
	// return null;
	// }
	
	// /**
	// * 400 - Bad Request
	// */
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// @ExceptionHandler(MethodArgumentNotValidException.class)
	// public ResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException
	// e) {
	// logger.error("参数验证失败", e);
	// BindingResult result = e.getBindingResult();
	// FieldError error = result.getFieldError();
	// String field = error.getField();
	// String code = error.getDefaultMessage();
	// String message = String.format("%s:%s", field, code);
	// return null;
	// }
	
	// /**
	// * 400 - Bad Request
	// */
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// @ExceptionHandler(BindException.class)
	// public ResponseResult handleBindException(BindException e) {
	// logger.error("参数绑定失败", e);
	// BindingResult result = e.getBindingResult();
	// FieldError error = result.getFieldError();
	// String field = error.getField();
	// String code = error.getDefaultMessage();
	// String message = String.format("%s:%s", field, code);
	// return null;
	// }
	
	// /**
	// * 400 - Bad Request
	// */
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// @ExceptionHandler(ConstraintViolationException.class)
	// public ResponseResult handleServiceException(ConstraintViolationException e) {
	// logger.error("参数验证失败", e);
	// Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	// ConstraintViolation<?> violation = violations.iterator().next();
	// String message = violation.getMessage();
	// return null;
	// }
	
	// /**
	// * 400 - Bad Request
	// */
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// @ExceptionHandler(ValidationException.class)
	// public ResponseResult handleValidationException(ValidationException e) {
	// logger.error("参数验证失败", e);
	// return null;
	// }
	
	// /**
	// * 405 - Method Not Allowed
	// */
	// @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	// @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	// public ResponseResult
	// handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
	// logger.error("不支持当前请求方法", e);
	// return null;
	// }
	
	// /**
	// * 415 - Unsupported Media Type
	// */
	// @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	// @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	// public ResponseResult handleHttpMediaTypeNotSupportedException(Exception e) {
	// logger.error("不支持当前媒体类型", e);
	// return null;
	// }
	
	/**
	 * 自定义业务错误处理
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BussinessException.class)
	public ResponseResult handleServiceException(BussinessException e) {
		ResponseResult result = e.getResult();
		if (result != null) {
			return result;
		}
		result = new ResponseResult();
		if (e.getCode() != null)
			result.setCode(e.getCode());
		if (e.getMessage() != null)
			result.setMsg(e.getMessage());
		if (e.getObj() != null)
			result.setContent(e.getObj());
		return result;
	}
	
	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseResult handleException(Exception e) {
		logger.error("捕获到未知异常信息：", e);
		
		ResponseResult result = new ResponseResult();
		result.setCode("500");
		result.setMsg(e.getMessage());
		
		return result;
	}
	
	// /**
	// * 操作数据库出现异常:名称重复，外键关联
	// */
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	// @ExceptionHandler(DataIntegrityViolationException.class)
	// public ResponseResult handleException(DataIntegrityViolationException e) {
	// logger.error("操作数据库出现异常:", e);
	// return null;
	// }
}
