# 金融研发中心开发人员常用复用汇编

汇集常用的代码和知识点供开发人员备查. 避免在开发中的重复开发, 提高效率, 统一开发风格. 筛选技术方案的原则是高效, 可靠, 稳定并经过项目检验的.

起始阶段的内容单薄, 欢迎同时们踊跃提交自己称手的组件和工具, 也可以针对已有内容提出自己的想法, 互利共赢.

任何想法请在项目的**议题(issue)** 中提交.

{{TOC}}

### - 常用组件

##### - MS OFFICE

利用一些开源框架对 ms office 格式文件编程

###### - excel 操作

利用 jxls 框架实现 excel 文件的导出和数据导入. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/office/Excel.MD)

##### - 数据有效性验证
通过多种方式提供数据验证技术

###### - 注解注入式数据验证
避免显式编写的验证代码, 提供统一的验证风格. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/validate/JSR380Validator.MD)

###### - 日常字符串格式验证
如是否数字, 是否是手机号码等等. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/validate/UniversalValidator.MD)

###### - 大陆居民身份证有效性验证
不仅仅校验位数, 还校验了身份证编码的有效性. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/validate/IDCardValidator.MD)

##### - FastJSON 使用参考
使用阿里巴巴的 fastjson 工具对 json 的序列化和反序列化. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/FastJson.MD)

##### - 随机对象
简短的, 各种开发中需要生成随机数的解决方法, 甚至还包括随机日期和随机密码生成. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/Random.MD)

##### - 字符串高效操作
尽管 StringUtils 是使用频率最高的工具类. 但你肯定还没有充分使用它! 这里做了归纳 [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/StringUtils.MD)

##### - 模板化消息(短信&微信)
目标是: 配置了模板及消息服务后, 通过一行代码实现模板化消息的发送. 终端开发人员不用考虑发送的技术细节. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/TemplatedMessage.MD)

##### - 压缩与解压缩
从编程实现数据的压缩和解压缩. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/component/Zip.MD)

---

### - 网络相关

##### - HTTP 解析纯干货
简单的图解Http协议内容. 了解 Servlet 请求和响应的本质是 web 开发的基础, 一定要了解. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/net/http/Core%20HTTP%20.MD)

##### - OkHttpUtil
http协议请求的封装, 使开发者可以很方便的发起请求并处理返回结果. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/net/OkhttpUtil.MD)

---

### - 安全性有关

##### - 密码加密(消息摘要)
使用统一的密码加密工具, 简单快捷. 主要功能是密码的加密和核验. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/security/PasswordCodec.MD)

##### - 密码强度评估
使用几种常用的检查策略评估用户输入的密码, 判断其强度是否合格. [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/security/PasswordEval.MD) 

##### - 对称加密
对称加密算法已封装好为工具类, 可以直接简单的通过几个方法实现对数据对称加密.  [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/security/SymmetricEncryption.MD)

##### - 非对称加密
帮助理解非对称加密的概念. 同时非对称加密算法已封装好为工具类, 可以直接简单的通过几个方法实现数据传输过程中的非称加密.  [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/security/AsymmetricEncryption.MD)

---

### - 服务器相关

##### - 在 tomcat 控制台(日志文件)打印请求参数
该功能便于开发调试阶段观察请求参数以排查问题, 正式上线前应关闭.  [进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/server/ConsoleParameter.MD)

---

### - 中间件技术

##### - spring around 

###### - ContextService
用于**临时**获取 spring context 中定义的 bean.

这里**临时**的含义在于: 使用 ContextService 的类本身不是 spring 定义的 bean 但却需要使用 spring context 中定义的 bean. 如果两者都是 spring 管理的类, 则应优先使用依赖注入.

[进入](https://gitlab.ctbiyi.com/jrjd/usage/blob/master/src/main/java/usage/jrwhjd/spring/ContextService.MD)


