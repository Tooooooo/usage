### 目标:
对密码(或其它有不可逆运算要求的数据)进行消息摘要运算, 以起到隐藏明文的目的.

### 核心组件:
> com.jrwhjd.security.provider.encoding.MessageDigestPasswordEncoder

### 依赖:
> JRJDBase.jar

### 使用方法:
支持 "SHA-1", "MD5", "SHA-256", and "SHA-512" 几种算法.

#### 以重载的构造方法实例化 MessageDigestPasswordEncoder:

public MessageDigestPasswordEncoder(String algorithm);
public MessageDigestPasswordEncoder(String algorithm, boolean encodeHashAsBase64);

"algorithm" 指定对密码加密的算法, 取 "SHA-1", "MD5", "SHA-256", "SHA-512" 其中之一.
由于 MD5 较容易破解, 因此一般使用 "SHA-256". 需要注意的是, 一旦指定了算法, 在项目的整个生命期内最好不要更换, 以免引起前后加密的数据冲突.

"encodeHashAsBase64" 是指定加密后的密码是否以 base64 编码, 如否则采用16进制字符串编码.

#### 执行加密

public String encodePassword(String raw, String salt)

"raw" 是需要加密的原始密码;

"salt" 是盐值, 用于增强加密强度, 可以为 null [具体解释](https://libuchao.com/2013/07/05/password-salt)

#### 校验密码

public boolean isPasswordValid(String encPass, String rawPass, String salt)

"encPass" 是已经加密后的密码, 一般是从数据库中读取

"rawPass" 是要用于比较的密码明文, 一般是用户登录时输入

"salt" 是盐值, 可以为 null


### 示例:

```java
  public static void main(String[] args) {
    String password = "88bj34a@c31";
    String salt = "398f1s";
    MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-256", true);
    String enPwd = encoder.encodePassword(password, salt);
    System.out.println(enPwd);   // 2znrcwYxT6HZ7daQwVD+hFjalW6cEi2/0ymC8HhDigc=
    System.out.println(encoder.isPasswordValid(enPwd, password, salt));  // true
  }
```

### 注意事项:
最好将 MessageDigestPasswordEncoder 配置成为 Bean 以重用, 提高性能

### 参考链接:
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/security/provider/encoding/MessageDigestPasswordEncoder.java)