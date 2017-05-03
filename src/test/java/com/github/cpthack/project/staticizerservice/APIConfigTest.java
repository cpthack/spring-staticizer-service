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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.cpthack.project.staticizerservice.config.APIConfig;

/**
 * <b>APIConfigTest.java</b></br>
 * 
 * <pre>
 * APIConfig配置器测试类
 * </pre>
 *
 * @author cpthack cpt@jianzhimao.com
 * @date May 3, 2017 4:12:30 PM
 * @since JDK 1.7
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class APIConfigTest {
	
	private static final Logger	logger = LoggerFactory.getLogger(APIConfigTest.class);
	
	@Autowired
	private APIConfig			apiConfig;
	
	@Test
	public void test() {
		assertThat(apiConfig).isNotNull();
		List<Map<String, String>> keySecretMapList = apiConfig.getKeySecretMap();
		assertThat(keySecretMapList).isNotNull();
		for (Map<String, String> keySecretMap : keySecretMapList) {
			for (String appKey : keySecretMap.keySet()) {
				logger.info("读取到：appKey = [" + appKey + "]");
				logger.info("读取到：appSecret = [" + keySecretMap.get(appKey) + "]");
			}
		}
	}
}
