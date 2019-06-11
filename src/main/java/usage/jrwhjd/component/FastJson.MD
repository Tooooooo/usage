### 目标:
Fastjson是一个Java语言编写的高性能功能完善的JSON库。可以简单迅速的将 JSON 字符串转换为 java 对象, 反之亦然.

### 核心组件
>com.alibaba.fastjson.JSON

### 依赖:
```gradle
compile group: 'com.alibaba', name: 'fastjson', version: '1.2.57'
```

### 使用方法:
在代码中调用 com.alibaba.fastjson.JSON 的各个 static 方法. 有时还可以配合 com.alibaba.fastjson.annotation 包中的注解来增强功能.


### 示例:

#### 基本序列化
序列化就是把JavaBean对象转化成JSON格式的文本。
```java
Object o = "JSON String";
String text = JSON.toJSONString(o);
```
在这个例子中，调用JSON.toJSONString方法，把对象o转化为JSON格式的文本。

#### 日期格式化
fastjson直接支持日期类型数据的格式化，包括java.util.Date、java.sql.Date、java.sql.Timestamp、java.sql.Time。

缺省情况下，fastjson将Date类型序列化为long，这个使得序列化和反序列化的过程不会导致时区问题。fastjson还提供了基于格式化输出的SerializerFeature，例如
例如：
```java
Date date = new  Date(1324138987429L);
System.out.println(JSON.toJSONString(date, SerializerFeature.WriteDateUseDateFormat));
```

#### 使用@JSONField Annotation
在某些场景，你可能需要定制序列化输出，比如说，希望序列化采用之后采用"ID"，而不是"id"，你可以使用@JSONField这个Annotation。
```java
public  class  User {
    @JSONField(name="ID")
    public  int  getId() { ... }
}

User user = ...;
JSON.toJSONString(user); // {"ID":234}
```

#### 反序列化
反序列化就是把JSON格式的文本转化为Java Bean对象。

##### 指定Class信息反序列化
通过指定类型信息，可以很方便的将"JSON文本"反序列化为"Java Bean"对象，例如：
```java
String text = ...; // {"r":255,"g":0,"b":0,"alpha":255}
Color color = JSON.parseObject(text, Color.class);
```

##### 类型集合的反序列化
这个接口类似于parseObject
```java
String text = ...; // [{ ... }, { ... }]
List<User> users = JSON.parseArray(text, User.class);
```

##### 泛型的反序列化
如果你需要返回一个带泛型的对象，例如List<User>、Map<String, User>，你可以使用TypeReference来传入类型信息。
```java
String text = ...; // {"name":{"name":"ljw",age:18}}
Map<String, User> userMap = JSON.parseObject(text, new  TypeReference<Map<String, User>>() {});
```

##### 组合类型集合的反序列化
比如在网络协议中，经常会存在这样的组合:

> [{/*header*/}, {/*body*/}]

fastjson对这种结构的反序列化有专门支持。

```java
String text = ...; // [{/* header */}, {/* body */}]
Type[] types = new  Type[] {Header.class, Body.class};
List<Object> list = JSON.parseArray(text, types);
Header header = (Header) list.get(0);
Body body = (Body) list.get(1);
```

### 注意事项:

### 参考链接
[https://github.com/alibaba/fastjson/wiki/Quick-Start-CN](https://github.com/alibaba/fastjson/wiki/Quick-Start-CN)