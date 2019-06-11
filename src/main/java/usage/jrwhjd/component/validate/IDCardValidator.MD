### 目标:
中国大陆地区15,18位身份证编码验证, 不仅仅是验证身份证的数码格式, 同时也验证身份证号码是否有效.

### 核心组件:
> com.jrwhjd.infrastructure.validate.IDCardValidator

### 依赖:
> JRJDBase.jar

### 使用方法:
```java
public static boolean validate(String idcard)
```
### 示例:
```java
  public static void main(String[] args) {
    System.out.println(validate("420104198205221621"));  // false
    System.out.println(validate("420104198205221620"));  // true
  }
```

### 注意事项:
不仅仅是验证身份证的数码格式, 同时也验证身份证号码是否有效

### 参考链接:
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/infrastructure/validate/IDCardValidator.java)