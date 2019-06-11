### 目标:
提供自建的短网址服务.

### 核心组件:
> s.war

### 依赖:
```gradle
'org.springframework:spring-webmvc:4.2.2.RELEASE',
'org.springframework:spring-tx:4.2.2.RELEASE',
'org.springframework:spring-jdbc:4.2.2.RELEASE',
'org.slf4j:slf4j-api:1.7.22',
'org.apache.logging.log4j:log4j-core:2.8',
'org.apache.logging.log4j:log4j-api:2.8',
'org.apache.logging.log4j:log4j-slf4j-impl:2.8',
'org.projectlombok:lombok:1.16.12',
'javax.servlet:javax.servlet-api:3.1.0',
'com.fasterxml.jackson.core:jackson-databind:2.8.6',
'com.fasterxml.jackson.core:jackson-core:2.8.6',
'com.fasterxml.jackson.core:jackson-annotations:2.8.6',
'org.apache.commons:commons-lang3:3.5',
'commons-configuration:commons-configuration:1.10',
'commons-collections:commons-collections:3.2.2',
'commons-io:commons-io:2.5',
'com.alibaba:druid:1.0.27',
'cglib:cglib-nodep:3.2.4',
// db
"mysql:mysql-connector-java:5.1.40"
```

### 使用方法:
调用web接口生成短网址
```web
https://服务ip:端口/s/su
```
用表单(application/x-www-form-urlencoded)模式提交 url 参数, url 为需要缩短的原始网址.

接口会返回:
```json
{
  "code": "0",
  "tinyurl": "http://服务ip:端口/s/r?u=24u"
}
```
其中 "tinyurl" 即为经转换后的短网址.

"服务ip和端口"可在程序的 config_${SERVER_ENV}.properties 配置文件中用 shorturl.resolverHost 项来设置

### 示例:

### 注意事项:
短网址可部署在任意服务器上. 部署完毕后需要修改 config_${SERVER_ENV}.properties

### 参考链接:
[源码地址](https://gitlab.ctbiyi.com/jrjd/shorturl/tree/master/src/main/java/com/jrwhjd/surl)