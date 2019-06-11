### 目标:
项目运行过程中, 在 tomcat 控制台监控到每次调用接口时传递的参数.

### 核心组件
> com.jrwhjd.infrastructure.console.ParameterDisplayConsole

### 依赖:
JRJDBase.jar

### 使用方法:
在 web.xml 中配置 ParameterDisplayConsole 过滤器

```xml
<!-- 在控制台打印接口的请求参数. 仅用于开发调试, 部署到正式环境前需要去掉改配置项 -->
  <filter>
    <filter-name>ParameterDisplayConsole</filter-name>
    <filter-class>com.jrwhjd.infrastructure.console.ParameterDisplayConsole</filter-class>
    <init-param>
      <!-- 以'/'开头的相对url, 多个url之间用','分开 -->
      <param-name>excludeUrls</param-name>
      <param-value>/pro/login,/pro/uploadFile</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>ParameterDisplayConsole</filter-name>
    <servlet-name>mvc</servlet-name>
  </filter-mapping>
```
调用 web app 中的接口时, 可以从 tomcat 控制台观察到接口调用的参数

### 示例:

### 注意事项:
需要在 web.xml 中做如下配置才能使 ParameterDisplayConsole 生效

```xml
  <!-- 监听器配置 -->
  <!-- WebAppContextListener 需要在其它监听器前面配置, 尤其是 spring ContextLoaderListener -->
  <listener>
    <listener-class>com.jrwhjd.infrastructure.web.WebAppContextListener</listener-class>
  </listener>
  
  <!-- ENABLE_PARAMETER_CONSOLE_PRINT 参数用于开启 ParameterDisplayConsole 过滤器 -->
  <context-param>
    <param-name>webAppPreparing</param-name>
    <param-value>
      ENABLE_PARAMETER_CONSOLE_PRINT
    </param-value>
  </context-param>
```

### 参考链接
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/infrastructure/console/ParameterDisplayConsole.java)