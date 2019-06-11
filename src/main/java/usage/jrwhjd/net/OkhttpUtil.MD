### 目标:
封装/简化 http 协议的调用.

### 核心组件:
> com.jrwhjd.infrastructure.web.OkHttpUtil

### 依赖:
> JRJDBase.jar

```gradle
compile group: 'com.squareup.okhttp3', name: 'okhttp', version: '3.11.0'
```

### 使用方法:

OkHttpUtil 提过若干静态方法用于 get 和 post 方法的提交:

#### public static Content get(String url)
直接用 get 方法去请求一个 url

#### public static Content post(String url, String mediaTypeStr, String data)
向 url 参数发起 post 请求, data 是提交的数据(我们通常用一个json). mediaTypeStr 是请求的 MIME 类型:

- text/plain
- text/html
- image/jpeg
- image/png
- audio/mpeg
- audio/ogg
- audio/*
- video/mp4
- application/*
- application/json
- application/javascript
- application/ecmascript
- application/octet-stream

[了解更多 MIME](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Basics_of_HTTP/MIME_types)

#### public static Content post(String url, Map<String, String> data)
向 url 参数发起 post 请求, data 是以 form 表单方式(application/x-www-form-urlencoded;charset=UTF-8)提交的数据

#### public static Content post(String url, Map<String, String> header, Map<String, String> data)
向 url 参数发起 post 请求, data 是以 form 表单方式(application/x-www-form-urlencoded;charset=UTF-8)提交的数据.
该方法允许通过 header 参数自定义请求的头信息.

#### Content
以上方法返回 OkHttpUtil 的内部静态类 Content, 用于封装 http 请求返回的信息, 包括:
- httpCode -- http 请求响应的状态码
- type -- http 响应的 content type
- headers -- http 响应的头信息
- data -- http 响应返回的原始数据(字节数组)
- string() -- http 响应的数据的字符串形式(可转换为字符串的类型,比如json)
- isEmpty() -- 判断是否返回的空数据

以上各个重载方法还有"强制从不安全的连接返回数据"的 OpenSecure 版本, 主要用于通过 https 访问证书有异常的网址, 具体见源码

### 示例:

get 方法示例
```java
try {
    OkHttpUtil.Content content=OkHttpUtil.get("http://www.baidu.com");
    if (content.getHttpCode()<200){
        log.warn("http request incomplete");
        return content.getMessage();
    }
    String resultStr=new String(content.string());
    log.info(resultStr);
    return resultStr;
} catch (IOException e) {
    e.printStackTrace();
    return null;
}

```

post 方法示例
```java
String cat="tom";
Map<String,String> map=new HashMap<>();
map.put("cat",cat);
OkHttpUtil.Content content = OkHttpUtil.post("http://picclife.bxt189.com/s/su", map);
if (content.getHttpCode()<200){
  System.out.print(content.getMessage());
}
System.out.print(content.string());

```

### 注意事项:

### 参考链接:
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/infrastructure/web/OkHttpUtil.java)