<!-- 该代码在生成html网页时，可以辅助生成左侧悬浮的目录导航 code start -->
<script type="text/javascript" src="https://cpthack.github.io/self-dev-conf/api/doc-api.js"></script>
<!-- 该代码在生成html网页时，可以辅助生成左侧悬浮的目录导航 code end -->

# spring-staticizer-service接口文档

-   **spring-staticizer-service** 是一个提供给站点实现静态化的服务。基于spring boot、[java-staticizer](https://github.com/cpthack/java-staticizer)、[JBrowserDriver](https://github.com/MachinePublishers/jBrowserDriver)等开源项目实现，使用方式简单。

-   基于spring boot 启动方式，可以独立部署，以restful api提供服务，支持跨平台使用，环境依赖：jdk1.8+。
   
-------------------

## 目录

[TOC]


##  一、新手指引

- Step 1: 获取App Key和App Secret

> 请在静态化服务配置文件[application.yml](https://github.com/cpthack/spring-staticizer-service/blob/master/src/main/resources/config/application.yml)，确认自己的App Key和App Secret，可以继续往配置文件中新增配置项。

```
	String appKey = "cpthack";
	String secret = "aaaabbbbcccc";  
	//请替换为自己的 App Key 和 App secret  
```
- Step 2: 确认请求参数

> 查看API文档，确认请求参数。

```
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

```
- Step 3: 确认请求参数

> 调用加密API生成加密的请求签名，以防止API被盗用。开发者需要根据请求参数、App Key、App Secret生成签名， 注意这里使用Apache-Codec进行MD5签名。

```
	StringBuilder valueSb = new StringBuilder();
	params.put(appSecretName, appSecret);
	// 将参数以参数名的字典升序排序
	Map<String, String> sortParams = new TreeMap<String, String>(params);
	Set<Entry<String, String>> entrys = sortParams.entrySet();
	// 遍历排序的字典,并拼接value1+value2......格式
	for (Entry<String, String> entry : entrys) {
		valueSb.append(entry.getValue());
	}
	params.remove(appSecretName);
	return md5(valueSb.toString());

```

- Step 4: 发起请求并获得返回结果  

> 根据API的属性，采用HTTP请求向服务器发起请求（目前所有API都使用POST方式获取数据）并获取响应结果。这里使用okHttp,将App Key、参数、生成的签名（即sign)、时间戳（即timestamp）作为参数，并针对特定API的访问路径（即serviceUrl）进行请求。

```
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

```

##  二、应用接入规范

待后期补充。

##  三、API文档

### 3.1 静态化相关接口说明
#### 3.1.1 增加用户

- **请求URL**
>[api/v2/operating/addUser](#)

- **请求方式** 
>**POST**

- **请求参数**

| 请求参数   |  参数类型 | 参数说明  |
| :-------- | :--------| :------ |
| uid|  Long,**不可为空**| 用户UID|
| status|   Integer,可为空|  用户可用性，默认可用 **-1.不可使用 1.可用**|

- **返回数据**
 
| 请求参数   |  参数类型 | 参数说明  |
| :-------- | :--------| :------ |
| success|   boolean|  请求成功与否|
| code|   Integer|  执行结果code|
| message|   String|  执行结果消息|

- **返回示例**

```
{
  "success": true,
  "code": 200,
  "message": "操作正确"
}
```

#### 3.1.2 查看用户

- **请求URL**
> [api/v2/operating/userList](#)


- **请求方式** 
>**GET**

- **请求参数**

| 请求参数      |    参数类型 |  参数说明  |
| :-------- | :--------| :------ |
| status|   Integer,可为空| 用户可用性 **-1.不可使用 1.可用**|
| curPage|   Integer,可为空|  当前页|
| pageSize|   Integer,可为空|  每页显示数量|

- **返回数据**

| 返回参数   |  参数类型 |  参数说明 |
| :-------- | :--------| :------ |
| success|   boolean|  请求成功与否|
| code|   Integer|  执行结果code|
| message|   String|  执行结果消息|
| results|   Object|  执行结果集|

- **返回示例**
  
```
{
  "success": true,
  "code": 200,
  "message": "操作正确",
  "results": [
    {
      "createdTs": 1471928107000,
      "updatedTs": 1471928107000,
      "id": 3,
      "uid": 23333,
      "useStatus": 1,(使用状态)
      "nickName": "成佩涛",
      "avatar": "http://7xljdd.com2.z0.glb.qiniucdn.com/fa767b500c58206f5f213a0fe4187e47.jpg"
    }
  ],
  "curPage": 1,
  "pageCount": 2,
  "count": 2,
  "hasRecords": true
}
```

#### 3.1.3 修改用户

- **请求URL**
> [api/v2/operating/updateuser](#)


- **请求方式** 
>**POST**

- **请求参数**

| 请求参数    |    参数类型 |  参数与说明  |
| :-------- | :--------| :------ |
| uid|   Long,**不可为空**| 用户UID|
| status|   Integer,可为空| 用户可用性 **-1.不可使用 1.可用**|

- **返回数据**

| 返回参数  |   参数类型 |  参数说明 |
| :-------- | :--------| :------ |
| success|   boolean|  执行成功与否|
| code|   Integer| 执行结果code|
| message|   String|  执行结果消息|

- **返回示例**
   
```
{
  "success": true,
  "code": 200,
  "message": "操作正确"
}
```

#### 3.1.4 删除用户

- **请求URL**
> [api/v2/operating/deleteuser](#)


- **请求方式** 
>**POST**

- **请求参数**

| 请求参数   |   参数类型 |  参数说明  |
| :-------- | :--------| :------ |
| uid|   Long,**不可为空**| 用户UID|

- **返回数据**

| 返回参数   |   参数类型 |   参数说明  |
| :-------- | :--------| :------ |
| success|   boolean| 请求成功与否|
| code|   Integer|  执行结果code|
| message|   String| 执行结果消息|

- **返回示例**
  
```
{
  "success": true,
  "code": 200,
  "message": "操作正确"
}
```


##  四、常见问题指引

### 4.1 问题反馈
	
	第三方开发者（简称ISV）可以通过淘宝开放平台（简称TOP）提供的ISV支持中心获取TOP技术人员的技术支持服务。
	
	通过ISV支持中心，您可以：
	
	  自助搜索和查看常见问题
	  在线提交问题给技术人员
	  在线提交任何需求和建议
	通过支持中心中获取技术支持是免费的，但是需要您注册成为TOP的开发者。
	
	如何注册成为开发者，请参考文档《注册成为开发者》。
	
	联系官方QQ：1044559878


### 4.2 接口返回码说明

#### 4.2.1 系统级错误码

| 错误代码 | 返回msg | 详细描述 |
|:------:|:-------------|:---------|
| 400 | 系统错误，请稍候再试 | 请求参数有误 |
| 401 | 系统错误，请稍候再试 | 用户未登录 |
| 404 | 系统错误，请稍候再试 | 资源未找到 |
| 405 | 系统错误，请稍候再试 | 请求方法不支持 |
| 500 | 系统错误，请稍候再试 | 服务器错误 |

#### 4.2.2 业务级错误码

| 错误代码 | 返回msg|详细描述 |
|:-------:|:-----|:--------|
| 1010    | xxxx   | xxxx |


## 五、反馈与建议

- GitHub地址：[@http://www.github.com/cpthack](http://www.github.com/cpthack)
- 邮箱：<1044559878@qq.com>
