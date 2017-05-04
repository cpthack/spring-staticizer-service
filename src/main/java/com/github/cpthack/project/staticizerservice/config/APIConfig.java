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
package com.github.cpthack.project.staticizerservice.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <b>APIConfig.java</b></br>
 * 
 * <pre>
 * API配置文件读取类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 3, 2017 2:23:16 PM
 * @since JDK 1.7
 */
@Configuration
@ConfigurationProperties(prefix = "apiConfig") // 接收api.yml中的apiConfig下面的属性
public class APIConfig {
	
	/**
	 * 自定义API配置属性
	 */
	private String					  name;
	/**
	 * 开发者账号唯一标识码的参数名称
	 */
	private String					  appKeyName;
	/**
	 * 密钥的参数名称
	 */
	private String					  appSecretName;
	
	/**
	 * MD5签名的字符串的参数名称
	 */
	private String					  signName;
	
	/**
	 * 时间戳的参数名称
	 */
	private String					  timestampName;
	
	/**
	 * 返回状态码的参数名称
	 */
	private String					  codeName;
	
	/**
	 * 返回信息的参数名称
	 */
	private String					  msgName;
	
	/**
	 * appKey&appSecret配对 集合信息
	 */
	private List<Map<String, String>> keySecretMap = new ArrayList<>();
	
	/**
	 * 错误码错误信息集合
	 */
	private List<Map<String, String>> codeMsgMap   = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTimestampName(String timestampName) {
		this.timestampName = timestampName;
	}
	
	public List<Map<String, String>> getKeySecretMap() {
		return keySecretMap;
	}
	
	public void setKeySecretMap(List<Map<String, String>> keySecretMap) {
		this.keySecretMap = keySecretMap;
	}
	
	public String getAppKeyName() {
		return appKeyName;
	}
	
	public void setAppKeyName(String appKeyName) {
		this.appKeyName = appKeyName;
	}
	
	public String getAppSecretName() {
		return appSecretName;
	}
	
	public void setAppSecretName(String appSecretName) {
		this.appSecretName = appSecretName;
	}
	
	public String getSignName() {
		return signName;
	}
	
	public void setSignName(String signName) {
		this.signName = signName;
	}
	
	public String getTimestampName() {
		return timestampName;
	}
	
	public String getCodeName() {
		return codeName;
	}
	
	public String getMsgName() {
		return msgName;
	}
	
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	
	public List<Map<String, String>> getCodeMsgMap() {
		return codeMsgMap;
	}
	
	public void setCodeMsgMap(List<Map<String, String>> codeMsgMap) {
		this.codeMsgMap = codeMsgMap;
	}
	
	/**
	 * 
	 * <b>getValueByKey</b> <br/>
	 * <br/>
	 * 
	 * 根据相关key值从List<Map<String, String>>中获取相对应的value值<br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param mapList
	 * @param keyName
	 * @param keyValue
	 * @param valueName
	 * @return String
	 *
	 */
	private String getValueByKey(List<Map<String, String>> mapList, String keyName, String keyValue, String valueName) {
		for (Map<String, String> map : mapList) {
			for (String key : map.keySet()) {
				if (!keyValue.equals(map.get(keyName)))
					continue;
				return map.get(valueName);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * <b>getAppSecret</b> <br/>
	 * <br/>
	 * 
	 * 根据开发者账号唯一标识码获取加密密钥<br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param appKey
	 * @return String
	 *
	 */
	public String getAppSecret(String appKey) {
		List<Map<String, String>> keySecretMapList = getKeySecretMap();
		String keyName = this.getAppKeyName();
		String keyValue = appKey;
		String valueName = this.getAppSecretName();
		return getValueByKey(keySecretMapList, keyName, keyValue, valueName);
	}
	
	/**
	 * 
	 * <b>getCodeMsg</b> <br/>
	 * <br/>
	 * 
	 * 根据错误码代号获取错误信息<br/>
	 * 
	 * @author cpthack cpt@jianzhimao.com
	 * @param code
	 * @return String
	 *
	 */
	public String getCodeMsg(String code) {
		List<Map<String, String>> keySecretMapList = getCodeMsgMap();
		String keyName = this.getCodeName();
		String keyValue = code;
		String valueName = this.getMsgName();
		return getValueByKey(keySecretMapList, keyName, keyValue, valueName);
	}
	
}
