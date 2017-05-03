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

> 请登录开发者平台（尚未注册？请点击这里），进入“管理中心”，确认自己的App Key和App Secret。

```
	String appKey = "5589931241";
	String secret = "db16adf193f2448ba0ec0260e0c968f3";  
	//请替换为自己的 App Key 和 App secret  
```
- Step 2: 确认请求参数

> 查看API文档，确认请求参数。

```
	String apiUrl = "http://api.dianping.com/v1/business/find_businesses";  
	paramMap.put("city", "上海");  
	paramMap.put("latitude", "31.21524");  
	paramMap.put("longitude", "121.420033");  
	paramMap.put("category", "美食");  
	paramMap.put("region", "长宁区");  
	paramMap.put("limit", "20");  
	paramMap.put("radius", "2000");  
	paramMap.put("offset_type", "0");  
	paramMap.put("has_coupon", "1");  
	paramMap.put("has_deal", "1");  
	paramMap.put("keyword", "泰国菜");  
	paramMap.put("sort", "7");  
	paramMap.put("format", "json"); 

```
- Step 3: 确认请求参数

> 调用点评API需要生成加密的请求签名，以防止API被盗用。开发者需要根据请求参数、App Key、App Secret生成签名， 注意这里使用Apache-Codec进行SHA1签名。

```
	StringBuilder stringBuilder = new StringBuilder();  
	  
	// 对参数名进行字典排序  
	String[] keyArray = paramMap.keySet().toArray(new String[0]);  
	Arrays.sort(keyArray);  
	// 拼接有序的参数名-值串  
	stringBuilder.append(appKey);  
	for (String key : keyArray)  
	{  
	    stringBuilder.append(key).append(paramMap.get(key));  
	}  
	String codes = stringBuilder.append(secret).toString();  
	String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();  

```
- Step 4: 拼接请求URL 

> 将App Key、参数、生成的签名（即sign）以及API的访问路径（即apiUrl）拼接成一个URL。

```
	// 添加签名  
	stringBuilder = new StringBuilder();  
	stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);  
	for (Entry≶String, String> entry : paramMap.entrySet())  
	{  
	    stringBuilder.append('&').append(entry.getKey()).append('=').append(entry.getValue());  
	}  
	String queryString = stringBuilder.toString();  

```
- Step 5: 发起请求并获得返回结果  

> 根据API的属性，采用HTTP请求向服务器发起请求（目前所有API都使用GET方式获取数据）并获取响应结果。这里使用Apache-HttpClient，注意请求之前需要将请求参数进行UTF-8编码。

```
	StringBuffer response = new StringBuffer();  
	HttpClientParams httpConnectionParams = new HttpClientParams();  
	httpConnectionParams.setConnectionManagerTimeout(1000);  
	HttpClient client = new HttpClient(httpConnectionParams);  
	HttpMethod method = new GetMethod(apiUrl);  
	  
	BufferedReader reader = null;  
	String encodeQuery = URIUtil.encodeQuery(queryString, "UTF-8"); // UTF-8 请求  
	method.setQueryString(encodeQuery);  
	client.executeMethod(method);  
	reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));  
	String line = null;  
	while ((line = reader.readLine()) != null)  
	{  
	    response.append(line).append(System.getProperty("line.separator"));  
	}  
	reader.close();  
	method.releaseConnection();  
	System.out.println(response.toString());

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
