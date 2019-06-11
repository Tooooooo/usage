### 目标:
整合模板化的消息发送能力, 使开发者能方便迅速的发送模板化的短信/微信等类型的消息.

### 核心组件
核心抽象接口

    com.jrwhjd.message.templated.TemplatedMessageService
    
提供了若干重载的 sendMessage 方法用于发送消息:
```java
  /**
   * 向当前登录用户发送模板消息.
   */
  boolean sendMessage(String template, String title, Map<String,String> variables)
      throws SendMessageException;

  /**
   * 向指定目标发送模板消息.<br> 模板消息应在配置文件中配置. 不同的消息类型应有不同的配置方式.
   */
  boolean sendMessage(String destination, String template, String title,
      Map<String, String> variables) throws SendMessageException;

  /**
   * 群发模板消息.
   */
  Object[] sendMessage(String[] destinations, String template, String title, Map<String,String> variables)
      throws SendMessageException;

```

    com.jrwhjd.message.templated.sms.AbstractSmsTemplatedMessageService
    
实现了 TemplatedMessageService 接口并具备了并物化了手机短信的发送能力. 开发人员需要继承该类实现与具体业务相关的安全认证和获取接收者方法.
由于是通过短信发送消息, 因此需要提供一个短信服务(同在toolbox.jar)的具体实现: SmsService . 详见 Sms.MD

    com.jrwhjd.message.templated.weixin.AbstractWeixinTemplatedMessageService
实现了 TemplatedMessageService 接口并具备了并物化了微信模板化消息的发送能力. [微信模板化消息](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277)
开发人员需要继承该类实现与具体业务相关的安全认证和获取接收者方法.
由于是通过微信公众号发送消息, 因此需要提供一个设置了具体安全认证信息的微信通信工具包(同在toolbox.jar): Weixin.MD 

### 依赖:
> JRJDBase.jar

> toolbox.jar 及其相关依赖配置

### 使用方法:
如果要通过短信发送模板化消息, 使用者需要继承

> com.jrwhjd.message.templated.sms.AbstractSmsTemplatedMessageService

实现与实际业务相关的方法.
下面是一个范例:

```java
/**
 * 向用户手机发送模板短消息. 模板消息应在配置文件中配置:
 * <pre>
 *   # 内容短信模板
 *   message.sms.template.{template} = Congratulations\,you awarded for {prize} bonus.
 * </pre>
 *
 * @author 涂鼎 [eMail: tuding27@gmail.com]
 * @version 0.1 [2019/1/21]
 */
@Slf4j
public class SmsTemplatedMessageService extends AbstractSmsTemplatedMessageService implements
    InitializingBean {

  @Setter
  private LoginService loginService;


  /**
   * 子类可以灵活实现获取当前登录者 Authentication 认证对象的方法.
   * @return 安全认证对象
   */
  @Override
  protected Authentication getCurrentAuthentication() {
    Authentication auth = SecurityHolder.getAuthentication();
    if (null == auth) {
      log.warn("无法获取当前登录用户, 发送失败");
      return null;
    }
    return auth;
  }


  /**
   * 本方法需要实现如何从用户的身份对象获得消息的发送目标, 如手机号码,用户的微信openid.
   * @param principal 用户的身份对象, 可以是用户的标识或用户账号
   * @return 该用户的手机号或微信 openid 或其它用户标识, 用户要根据需要完成不同的实现 
   */
  @Override
  protected String getDestinationFromPrincipal(Object principal) {
    if (principal instanceof Integer) {
      return this.loginService.getById((Integer)principal).getPhoneNo();
    }
    if (principal instanceof String) {
      return this.loginService.getByUsername((String)principal).getPhoneNo();
    }
    return null;
  }


  @Override
  public void afterPropertiesSet() {
    super.afterPropertiesSet();
    Assert.notNull(this.loginService, "loginService must be set");
  }
}
```

同理, 如果要通过微信发送模板化消息, 使用者需要继承

> com.jrwhjd.message.templated.weixin.AbstractWeixinTemplatedMessageService

实现与实际业务相关的方法. 获得用户的 weixin openid.

完成了配置, 然后可在实际业务中调用 sendMessage 方法给特定用户发送模板化的消息, 如:

> sendMessage("18932578531", "hello", null, vs);

其中 "18932578531" 是接收者的手机号(如果发送微信模板化消息则需要提供openid);

"hello" 是定义在 properties 文件中的模板化消息的项名: message.sms.template.hello , 前缀是 "message.sms.template", 如需改变前缀可通过 setTemplatePrefix 方法来设置

"vs" 是提供具体数据的 javabean 或 map, 用于填充 "message.sms.template.hello" 项的占位符变量

### 示例:
上面的使用使用方法说明采用的是手机短信模式的模板化消息发送, 以下示例将使用大同小异的微信模板化消息的发送.

spring 配置文件
```xml
  <!-- 微信通信组件, 封装常用的微信开发者api -->
  <bean name="weixinCom" class="com.jrwhjd.weixin.WeixinCom">
    <constructor-arg name="appName" value="${weixin.appName}"/>
    <constructor-arg name="url" value="${weixin.gainWeixinAccessTokenServiceUrl}" type="java.net.URL"/>
    <property name="appid" value="${weixin.appid}"/>
    <property name="secret" value="${weixin.secret}" />
  </bean>
  
  <bean name="weixinTemplatedMessageService" class="com.jrwhjd.zaizb.message.WeixinTemplatedMessageService">
    <property name="loginService" ref="loginService" />
    <property name="weixinCom" ref="weixinCom"/>
  </bean>
  
  <bean class="com.jrwhjd.zaizb.user.controller.UserController">
    <property name="messageService" ref="weixinTemplatedMessageService"/>
  </bean>
```

对应的 properties 配置文件
```properties
weixin.appid=wx58c29f862daf4900
weixin.secret=59c01199e3e9d71e34aadf6ea144c791
# gainWeixinAccessTokenServiceUrl 和 appName 用于从 weixin access token 集中管理服务中获取 access token
weixin.appName=hubeixinchan
weixin.gainWeixinAccessTokenServiceUrl=http://58.53.209.99:8080/weixintoken/urf7g2svqhd66d84

# 微信模板的ID
message.weixin.template.hello.id = XLL6hOvfbbNlhcbkccky61yyMEKKVRr-uYzlb61hiJw
# 点击模板消息后转到的地址
message.weixin.template.hello.url = http://limabao.bxt189.com/xxxxxx
# 微信显示模板消息panel顶部主题装饰色
message.weixin.template.hello.topcolor = #A29F19
message.weixin.template.hello.colors = first:#478383,keyword1:#F74E23,keyword2:#8D4821,remark:#394821
```

调用服务的 java 代码片段
```java
Map<String, String> vs = new HashMap<>();
vs.put("password", pwd);
sendMessage(user.getPhoneNo(), "hello", null, vs);
```

### 注意事项:
模板化消息的发送服务配置项较多, 容易混淆, 如有问题可随时问我(QQ:200161, TEL: 18907182125)

### 参考链接
[源码地址](https://gitlab.ctbiyi.com/jrjd/toolbox/tree/master/src/main/java/com/jrwhjd/message/templated)