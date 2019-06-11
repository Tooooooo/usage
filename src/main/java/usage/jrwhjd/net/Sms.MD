### 目标:
通过提供商的短信服务向特定手机号码发送短信. 

### 核心组件
**com.jrwhjd.sms 包下**
- com.jrwhjd.sms.SmsService
- com.jrwhjd.sms.SmsBuilder
- com.jrwhjd.sms.SmsSender

### 依赖:
> JRJDBase.jar

> toolbox.jar 及其相关依赖配置

### 使用方法:
在 spring 配置文件中配置 com.jrwhjd.sms.SmsService 接口的实例, 例如
```xml
<!-- 被动短信服务(没有冷却时间和每日发送次数限制)  -->
  <bean name="netbossPhoneSmsServicePassive" class="com.jrwhjd.sms.netboss.SmsServiceNetboss"
    p:user="${sms.username.netboss}"
    p:password="${sms.password.netboss}"/>

<!-- 主动短信服务(没有冷却时间和每日发送次数限制) -->
  <bean name="netbossPhoneSmsService" class="com.jrwhjd.sms.netboss.SmsServiceNetboss"
    p:user="${sms.username.netboss}"
    p:password="${sms.password.netboss}"
    p:coolerDuration="${sms.cooler_duration}"
    p:sendLimit="${sms.limit_day}" />
```
这里的"主动短信"是指可以由客户主动触发的短信发送动作, 比如"遗忘密码"需要经由短信发送验证码, 因此需要控制冷却时间和每日发送次数.
"被动短信" 是由系统事件触发, 不和客户有直接联系的短信发送动作, 比如"转账后扣款提醒"的短信.

- 冷却时间 -- 两次短信发送之间的最小间隔时间
- 每日发送限制 -- 限定客户每天可以发送的短信条数

可以直接在<bean>元素中直接设置 user / passowrd 等属性, 但更好的实践是通过 spring 的占位符特性, 通过 properties 配置文件来载入配置的值
```xml
<c:property-placeholder
      location="classpath:/conf_db/jdbc_${SERVER_ENV}.properties,classpath*:/config/${SERVER_ENV}/*.properties"
      file-encoding="UTF-8" />
```
实际的配置在 properties 文件中:
```properties
# 短信身份
sms.username.netboss = zaizb
sms.password.netboss = zaizb123@

# 短信服务设置
# 同一手机号(主动发送模式下)连续发送需要的时间间隔(秒)
sms.cooler_duration=60
# 每个手机号(主动发送模式下)每天最大发送的短信条数
sms.limit_day=10
```

最后在 Java 代码中完成发送逻辑:
```java
service = ContextService.getBean("netbossPhoneSmsService", SmsService.class);
SmsBuilder builder = service.getSmsBuilder();

Sms sms = builder
        .smsText(p.getString("sms.text.netboss", null)) // 从配置文件的"sms.text.netboss"项中获取文案(可包含有占位符变量,也可没有)
                                                        // sms.text.netboss = Congratulations\,you awarded for ${prize} bonus.
        .fillText("prize", "$10000")  // 取代上述的占位符变量, 组成实际发送的文本
        .addReceivers("18907182125").build();  // 指定接收短信的手机号码, 可以重复执行指定更多号码
SmsSender sender = service.getSmsSender();
sender.send(sms);
```
### 示例:
```java
public static void main(String[] args) throws SmsCheckException {
    Preference p = Preference.current();
    try {
      p.loadFile(new ClassPathResource("config.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    SmsService service = SmsServiceFactory.getService(AvailableSmsService.netboss);

    service.setUser(p.getString("sms.security.username.netboss", null));
    service.setPassword(p.getString("sms.security.password.netboss", null));

    service = ContextService.getBean("netbossPhoneSmsService", SmsService.class);
    SmsBuilder builder = service.getSmsBuilder();

    Sms sms = builder
        .smsText(p.getString("sms.text.netboss", null))
        .fillText("prize", "$10000")
        .addReceivers("18907182125").build();
    SmsSender sender = service.getSmsSender();
    sender.send(sms);
    DirectoryWatch.watching = false;
  }
```

### 注意事项:
目前只提供了netboss(汉货)的具体实现. 如果后面有其它的短信平台, 需要按照接口规范开发其它实现

### 参考链接
[源码地址](https://gitlab.ctbiyi.com/jrjd/toolbox/tree/master/src/main/java/com/jrwhjd/sms)