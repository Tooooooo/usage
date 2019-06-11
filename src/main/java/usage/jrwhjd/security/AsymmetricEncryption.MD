### 目标:
非对称加密算法是一种密钥的保密方法。 非对称加密算法需要两个密钥：公开密钥（publickey:简称公钥）和私有密钥（privatekey:简称私钥）。 公钥与私钥是一对，如果用公钥对数据进行加密，只有用对应的私钥才能解密。 因为加密和解密使用的是两个不同的密钥，所以这种算法叫作非对称加密算法。

非对称加密算法实现机密信息交换的基本过程是：
1. 甲方生成一对密钥并将公钥公开
2. 需要向甲方发送信息的其他角色(乙方)使用该密钥(甲方的公钥)对机密信息进行加密后再发送给甲方
3. 甲方再用自己私钥对加密后的信息进行解密
4. 甲方想要回复乙方时正好相反，使用乙方的公钥对数据进行加密，同理，乙方使用自己的私钥来进行解密。

另一方面，甲方可以使用自己的私钥对机密信息进行签名后再发送给乙方；乙方再用甲方的公钥对甲方发送回来的数据进行验签。
甲方只能用其私钥解密由其公钥加密后的任何信息。

[图解非对称加密的数字签名和验签过程](https://blog.csdn.net/xiangwanpeng/article/details/70834060)

非对称加密算法的保密性比较好，它消除了最终用户交换密钥的需要。

### 核心组件:
> com.jrwhjd.security.crypto.RSACoder

### 依赖:

> JRJDBase.jar

### 使用方法:
现有 服务器-S 和 客户机-C , 如果 C 想向 S 发送一段机密文字 t, 那么需要执行如下步骤:

1. C 执行 RSACoder.genKeyPair() 生成公钥私钥对 keypair;
2. C 用 RSACoder.getPrivateKeyEncoded() 传入 keypair 来获得私钥;
3. 同理, C 可用 RSACoder.getPublicKeyEncoded() 传入 keypair 来获得公钥, 准备好公钥后可随时向 S 提供;
4. C 用 RSACoder.encryptByPrivateKey() 方法, 传入 t 和 私钥, 获得加密后的数据(字节数组), 如需要可用 base64 编码;
5. (可选, 为了保证 t 的完整性) C 以 RSACoder.sign() , 传入 t 和私钥来获得数字签名
6. C 将密文发送给 S (如果有数字签名则需要把密文和数字签名封装后发送, 比如用一个 Map);
7. S 拿到 C 发送的密文(或加有数字签名的封装);
8. S 解密文就需要 C 的公钥, 因此向 C 请求公钥(C 之前已准备好);
9. S 拿到 C 的公钥后, 使用 RSACoder.decryptByPublicKey() 传入密文和 C 的公钥, 得到 t;
10. (可选) 如果 C 的发送包含有数字签名, S 可用 RSACoder.verify() 传入密文,C 的公钥以及数字签名来验证消息的是否未经过篡改;

如果 S 想回复 C, 同样需要执行上述步骤, 只不过 C 和 S 对调一下

### 示例:

```java
  public static void main(String[] args) throws Exception {
    Map<String, Object> keyMap = genKeyPair();
    String publicKey = getPublicKeyEncoded(keyMap);
    String privateKey = getPrivateKeyEncoded(keyMap);
    byte[] encrypted = encryptByPublicKey(
        "道可道 非常道 名可名 非常名 无名天地之始 有名万物之母"
            .getBytes(), publicKey);
    byte[] decrypted = decryptByPrivateKey(encrypted, privateKey);
    System.out.println(new String(decrypted));
  }
```

### 注意事项:

美国的出口限制，JDK自带的加密算法位数都不超过256位，需要手工替换。 

[下载地址](https://www.oracle.com/technetwork/cn/java/javase/downloads/jce8-download-2133166-zhs.html)

[替换方法](https://blog.csdn.net/hfhwfw/article/details/68557238)


### 参考链接:
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/security/crypto/RSACoder.java)