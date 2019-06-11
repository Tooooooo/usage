### 目标:
需要对加密和解密使用相同密钥的加密算法。由于其速度快，对称性加密通常在消息发送方需要加密大量数据时使用。对称性加密也称为密钥加密。

在对称加密算法中常用的算法有：DES、3DES、TDEA、Blowfish、RC2、RC4、RC5、IDEA、SKIPJACK等。

为了简单和统一, 我们在开发时使用

> AES（Advanced Encryption Standard）：高级加密标准，是下一代的加密算法标准，速度快，安全级别高，支持128、192、256、512位密钥的加密

作为标准算法

### 核心组件:

> com.jrwhjd.security.crypto.AESCoder

### 依赖:
> JRJDBase.jar

### 使用方法:

#### 获得密钥

通过 AESCoder 的静态方法

    public static byte[] initKey(int bits)

获得一个随机的密钥, 其中"bits"的有效值是 128/192/256. 加解密双方都需持有该密钥. 

方法返回密钥的是字节数组类型, 如需要字符串形态的密钥可以用 base64 编码:

```java
Base64.getEncoder().encodeToString(initKey(bits))
```

#### 解密

用一组重载的解密方法解密

```java
public static byte[] decrypt(byte[] data, byte[] key)
public static String decrypt(String data, String key)
```
两个方法的参数用意一样, 类型不同. 第二个方法主要解密字符串, 第一个可用于解密文件或流数据

#### 加密

用一组重载的方法加密

```java
public static String encrypt(String data, String key)
public static String encrypt(byte[] data, String key)
public static byte[] encrypt(byte[] data, byte[] key)
```
参数和解密方法差不多, 不再赘述


### 示例:

```java
public static void main(String[] args)
      throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
    String key = Base64.getEncoder().encodeToString(initKey(192));
    String text = "my password is )97gb23_234";
    String encrypted = AESCoder.encrypt(text, key);
    System.out.println(encrypted);  // 每次运行的结果都会不一样
    String decrypted = AESCoder.decrypt(encrypted, key);
    System.out.println(decrypted);  // my password is )97gb23_234
    System.out.println(decrypted.equals(text));  // true
  }
```

### 注意事项:

美国的出口限制，JDK自带的加密算法位数都不超过256位，需要手工替换。 

[下载地址](https://www.oracle.com/technetwork/cn/java/javase/downloads/jce8-download-2133166-zhs.html)

[替换方法](https://blog.csdn.net/hfhwfw/article/details/68557238)


对称加密的安全性不仅取决于加密算法本身，密钥管理的安全性更是重要。

因为加密和解密都使用同一个密钥，如何把密钥安全地传递到解密者手上并且妥善保管是很关键的问题

### 参考链接:
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/security/crypto/AESCoder.java)