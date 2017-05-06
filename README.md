<!-- 该代码在生成html网页时，可以辅助生成左侧悬浮的目录导航 code start -->
<script type="text/javascript" src="https://cpthack.github.io/self-dev-conf/api/doc-api.js"></script>
<!-- 该代码在生成html网页时，可以辅助生成左侧悬浮的目录导航 code end -->

# spring-staticizer-service接口文档

-   **spring-staticizer-service** 是一个提供给站点实现静态化的服务。基于spring boot、[java-staticizer](https://github.com/cpthack/java-staticizer)、[JBrowserDriver](https://github.com/MachinePublishers/jBrowserDriver)等开源项目实现，使用方式简单。

-   基于spring boot 启动方式，可以独立部署，以restful api提供服务，支持跨平台使用，环境依赖：jdk1.8+。
-   在线文档地址[点此](https://cpthack.github.io/spring-staticizer-service/api/)
   
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
#### 3.1.1 获取静态文件内容(支持PC访问的站点)

- **请求URL**
>[http://localhost:8080/service/staticizer/pc](#)

- **请求方式** 
>**POST**

- **请求参数**

| 请求参数   | 是否必须 | 参数类型| 限制长度| 参数说明  |
| :-------- |  :--------| :--------| :--------| :------ |
| appKey | YES | String| 255 | 接口appKey，应用的唯一标识|
| timestamp | YES | String| 20 | 服务器当前时间，1970-01-01开始的时间戳，毫秒为单位，示例：1493999757188|
| sign | YES | String| 255 | MD5签名,将URL中每个参数值和appSecret（appSecret在服务端配置文件中可查看密钥）按照参数名称升序，拼接然后md5转码 详见MD5签名规则|
| url | YES | String| 500 | 待静态化的目标站点URL|
| requestHeaderJson | NO | Json| 1000 | 请求头信息JSON数组，一般使用方式，程序获取到客户端请求中的请求头信息之后，转成LinkHashMap<String,String>数组数据，并转成JSON对象，以达到能够真正模拟客户端访问的状态|

- **返回数据**
 
| 参数   | 是否必须 | 参数类型| 限制长度| 参数说明  |
| :-------- |  :--------| :--------| :--------| :------ |
| code | YES | String| 25 | 状态码，000表示常规的成功状态.500表示常规的失败状态，其他状态码请参考[4.2 接口返回码说明](#)|
| msg | YES | String| 100 | 返回的处理消息.|
| content | NO | Object| N/A | 返回的具体数据值.一般从content中取出obj属性的数据，示例：result.getContent().get("obj")|

- **返回示例**

> 请求成功示例

```
{
	"code":"200",
	"msg":"request is successfully!",
	"content":{
		"obj":"<html class=\"pixel-ratio-1\"><head>\n\t<title>兼职猫</title>\n\t \n\n\n<meta charset=\"utf-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">\n<link rel=\"shortcut icon\" href=\"/favicon.ico\">\n<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n<meta content=\"telephone=no\" name=\"format-detection\">\n<meta content=\"email=no\" name=\"format-detection\">\n<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\">\n<link rel=\"icon\" href=\"http://oss.aliyuncs.com/jianzhimao/web-res/icon/jianzhimao-logo-min.png\" type=\"image/x-icon\">\n<link rel=\"shortcut icon\" href=\"http://oss.aliyuncs.com/jianzhimao/web-res/icon/jianzhimao-logo-min.png\" type=\"image/x-icon\">\n<link rel=\"stylesheet\" href=\"https://img.jianzhimao.com/static/m3/common/light7/css/light7.css?v=0e6a8c6\">\n<link rel=\"stylesheet\" href=\"https://img.jianzhimao.com/static/m3/common/light7/css/light7-swiper.min.css?v=aab697c\">\n<link rel=\"stylesheet\" href=\"https://img.jianzhimao.com/static/m3/static/css/job.css?v=58a22cd\">\n\n\n</head>\n\n<body>\n<div id=\"job_index_page\" class=\"page-group \"><div id=\"page_index\" class=\"page page-current page-inited\"></body></html>"
		}
}
```
> 请求失败示例

```
{
    "code": "500",
    "msg": "appkey不能为空.",
    "content": null
}

```

#### 3.1.2 获取静态文件内容(仅支持手机端访问的站点)

- **请求URL**
> [http://localhost:8080/service/staticizer/mobile](#)


- **请求方式** 
>**POST**

- **请求参数**

| 请求参数   | 是否必须 | 参数类型| 限制长度| 参数说明  |
| :-------- |  :--------| :--------| :--------| :------ |
| appKey | YES | String| 255 | 接口appKey，应用的唯一标识|
| timestamp | YES | String| 20 | 服务器当前时间，1970-01-01开始的时间戳，毫秒为单位，示例：1493999757188|
| sign | YES | String| 255 | MD5签名,将URL中每个参数值和appSecret（appSecret在服务端配置文件中可查看密钥）按照参数名称升序，拼接然后md5转码 详见MD5签名规则|
| url | YES | String| 500 | 待静态化的目标站点URL|
| requestHeaderJson | NO | Json| 1000 | 请求头信息JSON数组，一般使用方式，程序获取到客户端请求中的请求头信息之后，转成LinkHashMap<String,String>数组数据，并转成JSON对象，以达到能够真正模拟客户端访问的状态|

- **返回数据**
 
| 参数   | 是否必须 | 参数类型| 限制长度| 参数说明  |
| :-------- |  :--------| :--------| :--------| :------ |
| code | YES | String| 25 | 状态码，000表示常规的成功状态.500表示常规的失败状态，其他状态码请参考[4.2 接口返回码说明](#)|
| msg | YES | String| 100 | 返回的处理消息.|
| content | NO | Object| N/A | 返回的具体数据值.一般从content中取出obj属性的数据，示例：result.getContent().get("obj")|

- **返回示例**
  
> 请求成功示例

```
{
	"code":"200",
	"msg":"request is successfully!",
	"content":{
		"obj":"<html class=\"pixel-ratio-1\"><head>\n\t<title>兼职猫</title>\n\t \n\n\n<meta charset=\"utf-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">\n<link rel=\"shortcut icon\" href=\"/favicon.ico\">\n<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n<meta content=\"telephone=no\" name=\"format-detection\">\n<meta content=\"email=no\" name=\"format-detection\">\n<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\">\n<link rel=\"icon\" href=\"http://oss.aliyuncs.com/jianzhimao/web-res/icon/jianzhimao-logo-min.png\" type=\"image/x-icon\">\n<link rel=\"shortcut icon\" href=\"http://oss.aliyuncs.com/jianzhimao/web-res/icon/jianzhimao-logo-min.png\" type=\"image/x-icon\">\n<link rel=\"stylesheet\" href=\"https://img.jianzhimao.com/static/m3/common/light7/css/light7.css?v=0e6a8c6\">\n<link rel=\"stylesheet\" href=\"https://img.jianzhimao.com/static/m3/common/light7/css/light7-swiper.min.css?v=aab697c\">\n<link rel=\"stylesheet\" href=\"https://img.jianzhimao.com/static/m3/static/css/job.css?v=58a22cd\">\n\n\n</head>\n\n<body>\n<div id=\"job_index_page\" class=\"page-group \"><div id=\"page_index\" class=\"page page-current page-inited\"></body></html>"
		}
}
```
> 请求失败示例

```
{
    "code": "500",
    "msg": "appkey不能为空.",
    "content": null
}

```

##  四、常见问题指引

### 4.1 问题反馈


-	appKey、appSecret可以在classpath:conf/application.yml文件中增加配置，可以不限增加多个appKey、appSecret数组对数据，示例如下：

```	
	keySecretMap:
    - appKey: test
      appSecret: xxxx3333xxx
    - appKey: cpthack
      appSecret: aaaabbbbcccc
```	
-  静态化服务对外API路由支持可配置，目前配置项主要存在classpath:application.properties文件中，你可以直接在application.properties文件中自定义路由名称，示例如下：

```
	#系统全局路由配置
	#配置静态化staticizerController中index方法的对外API名称(常规pc站点)
	routes.controller.staticizer.pc=/service/staticizer/pc
	#配置静态化staticizerController中index方法的对外API名称(仅移动站点访问)
	routes.controller.staticizer.mobile=/service/staticizer/mobile

```
-  如果您在开发过程中遇到问题，可以直接email到我的邮箱：1044559878@qq.com.
	

### 4.2 接口返回码说明

#### 4.2.1 系统级错误码

| 错误代码 | 返回msg | 详细描述 |
|:------:|:-------------|:---------|
| 000 | 请求成功. | 表示本次请求成功. |
| 403 | 禁止访问.签名失败. | 表示本次请求中对于参数的签名存在问题. |
| 500 | 请求失败，服务器发生异常. | 请求失败,一般会返回特定的msg. |

#### 4.2.2 业务级错误码

> 待补充.


## 五、反馈与建议

- GitHub地址：[@http://www.github.com/cpthack](http://www.github.com/cpthack)
- 邮箱：<1044559878@qq.com>
