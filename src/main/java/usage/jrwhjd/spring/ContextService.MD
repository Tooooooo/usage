### 目标:
快速获取 spring 定义的 Bean.

### 核心组件
> com.jrwhjd.infrastructure.context.ContextService

### 依赖:
JRJDBase.jar

### 使用方法:
```
读取 ApplicationContext 里定义的 Bean, 并返回转型后的特定型别的对象.
主要方法为
getBean(Class<T> type, String name)
或者
getBean(String name, Class<T> type)

参数:
type – 要返回的特定型别的class
name – Bean's Name
Type parameters:
<T> – 要返回的对象型别

返回:
Type 参数所对应的特定型别的对象
```
其中 userBean 是 spring 中定义的 bean name

### 示例:
首先在 spring 配置文件中配置 bean:
```xml
<bean name="userBean" class="foo.bar.User"/>
```
然后可以在代码的方法中使用如下片段获取定义的 userBean:

```java
User service = ContextService.getBean("userBean", User.class);
```

### 注意事项:
需要在 spring 配置文件中加入:
```xml
<!-- 注入 spring 上下文, 便于快速得到 Bean -->
<bean class="com.jrwhjd.infrastructure.context.ContextService" lazy-init="false" scope="singleton"/>
```

### 参考链接
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/infrastructure/context/ContextService.java)