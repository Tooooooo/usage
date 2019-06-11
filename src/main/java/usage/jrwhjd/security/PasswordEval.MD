### 目标:
检查用户提交的密码, 从不同方面检查密码强度

### 核心组件:
> com.jrwhjd.tool.security.password.PasswordStrengthCheck

### 依赖:
> toolbox.jar 及其相关依赖配置

### 使用方法:
PasswordStrengthCheck 提供四个密码强度评估方法:

- 宽松评估(looseCheck) -- 检查密码长度和是否存在重复或连续字符
- 一般评估(normalCheck) -- 检查密码长度, 是否存在重复或连续字符, 是否仅由单一的数字或字母组成
- 和严格评估(strictCheck) -- 检查密码长度, 是否存在重复或连续字符, 是否仅由单一的数字或字母组成, 是否仅由单一的大写字母或小写字母组成, 是否包含特殊符号
- 以及自定义评估(check) -- 参数传入可变数量的 PasswordEvalAspect 枚举来自定义要对密码进行的评估面

PasswordStrengthCheck 可以配置两个对象属性:

- requiredLength -- 默认值是6, 表示密码长度的最低要求
- maxRepeatOrContinuousLengthAllowed -- 默认值是3, 表示密码中相邻的字符可重复或连续的次数, 超过该数则密码被判定为存在重复或连续字符

密码的强度级别是:
- weak(20)
- normal(40)
- good(60)
- great(80)
- perfect(100);


### 示例:
```xml
<bean name="passwordEvaluate" class="com.jrwhjd.tool.security.password.PasswordStrengthCheck">
  <property name="requiredLength" value="8"/>
  <property name="maxRepeatOrContinuousLengthAllowed" value="3"/>
</bean>
```

```java
  public static void main(String[] args) {
    String badpwd = "123456";
    String weakpwd = "helloworld";
    String commonpwd = "helloword111";
    String strongpwd = "heLLo*worD97";
    PasswordStrengthCheck checker = ContextService.getBean("passwordEvaluate", PasswordStrengthCheck.class);
    System.out.println("bad password: " + badpwd);
    System.out.println("loose: \t -> " + checker.looseCheck(badpwd));
    System.out.println("normal: \t -> " + checker.normalCheck(badpwd));
    System.out.println("strict: \t -> " + checker.strictCheck(badpwd));
    System.out.println("weak password: " + weakpwd);
    System.out.println("loose: \t -> " + checker.looseCheck(weakpwd));
    System.out.println("normal: \t -> " + checker.normalCheck(weakpwd));
    System.out.println("strict: \t -> " + checker.strictCheck(weakpwd));
    System.out.println("common password: " + commonpwd);
    System.out.println("loose: \t -> " + checker.looseCheck(commonpwd));
    System.out.println("normal: \t -> " + checker.normalCheck(commonpwd));
    System.out.println("strict: \t -> " + checker.strictCheck(commonpwd));
    System.out.println("strong password: " + strongpwd);
    System.out.println("loose: \t -> " + checker.looseCheck(strongpwd));
    System.out.println("normal: \t -> " + checker.normalCheck(strongpwd));
    System.out.println("strict: \t -> " + checker.strictCheck(strongpwd));
    System.out.println("is common password accpeted: "
        + StrengthLevel.perfect.accept(checker.strictCheck(commonpwd)));
    /*
        bad password: 123456
        loose: 	 -> bad
        normal: 	 -> bad
        strict: 	 -> bad
        weak password: helloworld
        loose: 	 -> perfect
        normal: 	 -> normal
        strict: 	 -> weak
        common password: helloword111
        loose: 	 -> normal
        normal: 	 -> normal
        strict: 	 -> weak
        strong password: heLLo*worD97
        loose: 	 -> perfect
        normal: 	 -> perfect
        strict: 	 -> perfect
        is strong password accpeted: false
    */
  }
```

### 注意事项:

### 参考链接:
[源码地址](https://gitlab.ctbiyi.com/jrjd/toolbox/tree/master/src/main/java/com/jrwhjd/tool/security/password)