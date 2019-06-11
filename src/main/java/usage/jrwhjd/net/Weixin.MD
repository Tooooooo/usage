### 目标:

封装了微信开发者api的调用, 使业务开发无需考虑微信调用的技术细节.

### 核心组件:

> com.jrwhjd.weixin.WeixinCom

### 依赖:

> JRJDBase.jar

> toolbox.jar 及其相关依赖配置

### 使用方法:
WeixinCom 封装了如下常用的api调用:
- 获取微信开发的 access token: getAccessToken()  [参考链接](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183);
- 从微信网页授权获取用户openid: retrieveWeixinOpenid(String code)  [参考链接](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842);
- 获取微信网页开发(jsapi)的签名内容对象: getWeixinJSAPISignature(String url)  [参考链接](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115);
- 通过openid获得用户微信用户信息: retrieveUserInfo(String openid)  [参考链接](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839);

#### access token
access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。access_token的有效期目前为2个小时，需定时刷新，**重复获取将导致上次获取的access_token失效**。

微信建议公众号开发者使用中控服务器统一获取和刷新access_token，其他业务逻辑服务器所使用的access_token均来自于该中控服务器，不应该各自去刷新，否则容易造成冲突，导致access_token覆盖而影响业务.

WeixinCom 已通过配置的方式使用中控服务器统一获取和刷新access_token, 业务开发人员无需关心底层的技术细节

#### opneid
openid 是用户在公众号里的唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID

业务开发人员可通过 retrieveWeixinOpenid(String code) 来获取, 其中 code 参数是由客户微信提供给前端认真的授权码.

openid 和公众号对应, 是不变的, 应保存到数据库

#### jsapi 签名对象

主要提供给前端, 用于前端在调用微信的诸多功能性接口时的认证.

### 示例:

spring 配置文件
```xml
  <!-- 微信通信组件, 封装常用的微信开发者api -->
  <bean name="weixinCom" class="com.jrwhjd.weixin.WeixinCom">
    <constructor-arg name="appName" value="${weixin.appName}"/>
    <constructor-arg name="url" value="${weixin.gainWeixinAccessTokenServiceUrl}" type="java.net.URL"/>
    <property name="appid" value="${weixin.appid}"/>
    <property name="secret" value="${weixin.secret}" />
  </bean>
```

对应的 properties 配置文件
```properties
weixin.appid=wx58c29f862daf4900
weixin.secret=59c01199e3e9d71e34aadf6ea144c791
# gainWeixinAccessTokenServiceUrl 和 appName 用于从 weixin access token 集中管理服务中获取 access token
weixin.appName=hubeixinchan
weixin.gainWeixinAccessTokenServiceUrl=http://58.53.209.99:8080/weixintoken/urf7g2svqhd66d84
```

### 注意事项:

尽管 WeixinCom 目标是封装一些繁琐的接口调用使开发者能节省一部分开发时间, 但开发者还是应了解微信开发的概念和相关规范, 这样才能对组件的各个方法有更透彻的了解.

### 参考链接:
[源码地址](https://gitlab.ctbiyi.com/jrjd/toolbox/tree/master/src/main/java/com/jrwhjd/weixin)