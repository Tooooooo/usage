### 目标:
实现对字符串的常用规则校验.

### 核心组件
> com.jrwhjd.infrastructure.validate.UniversalValidator

### 依赖:
> JRJDBase.jar

### 使用方法:
直接使用 UniversalValidator 的各个静态方法, 传入字符串参数校验其有效性:
- isEmail           判断字段是否为有效的 Email 地址
- isMobile          是否是有效的手机号码
- isMobileLazy      宽松的判断是否为手机号码. 仅检验是否是 '1'开头,位数是否是11位以及是否全部为数字.
- isUrl             是否为有效的Url
- isNumber          参数字符串是否表达了有效的数字(正负整数 正负浮点数)
- isStrNumber       字符串是否全部由数字字符组成
- isInteger         是否是可转换为整数
- isIntegerNegative 参数字符串是否表示了一个负整数
- isIntegerPositive 参数字符串是否表示了一个正整数
- isDateCHNFormat   是否是 yyyy-MM-dd 格式的日期字符串
- isAge             参数字符串是否表述了有效的人的年龄(0-120)
- isLengOut         参数字符串是否超长
- isZIP             是否为有效的邮政编码
- isLetter          字符串是否全为英文字母
- isLetterNumberStr 字符串是否是仅由英文字母+数字组成
- isSafeStr         字符串是否是一个安全字符串(仅由英文字母+数字+下划线组成)
- isSafeString      字符串是否是一个符合长度要求(参数需要传入长度要求)安全字符串(仅由英文字母+数字+下划线组成)

### 示例:

```java
public static void main(String[] args) {
    System.out.println("UniversalValidator.isEmail(\"some#alicom\"): " + UniversalValidator.isEmail("some#alicom"));
    System.out.println("UniversalValidator.isEmail(\"hello@163.com\"): " + UniversalValidator
        .isEmail("hello@163.com"));
    System.out.println("UniversalValidator.isMobile(\"2392338223910\"): " + UniversalValidator
        .isMobile("23938223910"));
    System.out.println("UniversalValidator.isMobile(\"18907182125\"): " + UniversalValidator
        .isMobile("18907182125"));
    System.out.println("UniversalValidator.isMobileLazy(\"65653259\"): " + UniversalValidator
        .isMobileLazy("65653259"));
    System.out.println("UniversalValidator.isMobileLazy(\"13797099649\"): " + UniversalValidator
        .isMobileLazy("13797099649"));
    System.out.println("UniversalValidator.isUrl(\"http:/helloworld\"): " + UniversalValidator
        .isUrl("http:/helloworld"));
    System.out.println("UniversalValidator.isUrl(\"http://helloworld.com\"): " + UniversalValidator
        .isUrl("http://helloworld.com"));
    System.out.println("UniversalValidator.isNumber(\"4d8839.3838\"): " + UniversalValidator
        .isNumber("4d8839.3838"));
    System.out.println(
        "UniversalValidator.isNumber(\"1.2938\"): " + UniversalValidator.isNumber("1.2938"));
    System.out.println(
        "UniversalValidator.isStrNumber(\"3.2928\"): " + UniversalValidator.isStrNumber("3.2928"));
    System.out.println(
        "UniversalValidator.isStrNumber(\"873712\"): " + UniversalValidator.isStrNumber("873712"));
    System.out.println(
        "UniversalValidator.isInteger(\"9932.2\"): " + UniversalValidator.isInteger("9932.2"));
    System.out
        .println("UniversalValidator.isInteger(\"9527\"):" + UniversalValidator.isInteger("9527"));
    System.out.println("UniversalValidator.isIntegerNegative(\"338\"): " + UniversalValidator
        .isIntegerNegative("338"));
    System.out.println("UniversalValidator.isIntegerNegative(\"-123\"): " + UniversalValidator
        .isIntegerNegative("-123"));
    System.out.println("UniversalValidator.isIntegerPositive(\"-848\"): " + UniversalValidator
        .isIntegerPositive("-848"));
    System.out.println("UniversalValidator.isIntegerPositive(\"313\"): " + UniversalValidator
        .isIntegerPositive("313"));
    System.out.println("UniversalValidator.isDateCHNFormat(\"39923-23-82\"): " + UniversalValidator
        .isDateCHNFormat("39923-23-82"));
    System.out.println("UniversalValidator.isDateCHNFormat(\"2019-05-01\"): " + UniversalValidator
        .isDateCHNFormat("2019-05-01"));
    System.out.println("UniversalValidator.isAge(\"121\"): " + UniversalValidator.isAge("121"));
    System.out.println("UniversalValidator.isAge(\"100\"): " + UniversalValidator.isAge("100"));
    System.out.println(
        "UniversalValidator.isLengOut(\"abcde\", 3): " + UniversalValidator.isLengOut("abcde", 3));
    System.out.println(
        "UniversalValidator.isLengOut(\"abcde\", 6): " + UniversalValidator.isLengOut("abcde", 6));
    System.out
        .println("UniversalValidator.isZIP(\"di3320\"): " + UniversalValidator.isZIP("di3320"));
    System.out
        .println("UniversalValidator.isZIP(\"430000\"): " + UniversalValidator.isZIP("430000"));
    System.out.println(
        "UniversalValidator.isLetter(\"9dkk2b\"): " + UniversalValidator.isLetter("9dkk2b"));
    System.out.println(
        "UniversalValidator.isLetter(\"zyxwvu\"): " + UniversalValidator.isLetter("zyxwvu"));
    System.out.println("UniversalValidator.isLetterNumberStr(\"god like\"): " + UniversalValidator
        .isLetterNumberStr("god like"));
    System.out.println("UniversalValidator.isLetterNumberStr(\"51pm\"): " + UniversalValidator
        .isLetterNumberStr("51pm"));
    System.out.println(
        "UniversalValidator.isSafeStr(\"90/45=2\"): " + UniversalValidator.isSafeStr("90/45=2"));
    System.out.println("UniversalValidator.isSafeStr(\"9D94jF9_32\"): " + UniversalValidator
        .isSafeStr("9D94jF9_32"));
    System.out.println(
        "UniversalValidator.isSafeString(12, 15, \"9D94jF9_32\"): " + UniversalValidator
            .isSafeString(12, 15, "9D94jF9_32"));
    System.out.println(
        "UniversalValidator.isSafeString(10, 12, \"9D94jF9_32\"): " + UniversalValidator
            .isSafeString(10, 12, "9D94jF9_32"));
  }

```

### 注意事项:

### 参考链接
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/infrastructure/validate/UniversalValidator.java)