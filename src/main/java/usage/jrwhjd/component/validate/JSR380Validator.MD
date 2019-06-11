### 目标:
在写程序的时候经常需要进行数据校验，比如服务端对http请求参数校验，数据入库时对字段长度进行校验，接口参数校验，可以说数据校验遍布应用程序代码中.
为了减少代码重复率，JSR380定义了一套bean校验的元数据模型，将数据的约束定义在数据模型中。
hibernate validator则实现了这样一套规范。

### 核心组件

### 依赖:
```gradle
'org.hibernate.validator:hibernate-validator:6.0.16.Final',
'org.glassfish:javax.el:3.0.1-b08'
```

### 使用方法:
在 controller 中对前端传回的值对象进行有效性校验,
在 service 或 dao 中对领域对象进行有效性校验.

#### Bean Validation 中内置的 constraint
- @Null   被注解的元素必须为 null
- @NotNull    被注解的元素必须不为 null
- @AssertTrue     被注解的元素必须为 true
- @AssertFalse    被注解的元素必须为 false
- @Min(value)     被注解的元素必须是一个数字，其值必须大于等于指定的最小值
- @Max(value)     被注解的元素必须是一个数字，其值必须小于等于指定的最大值
- @DecimalMin(value)  被注解的元素必须是一个数字，其值必须大于等于指定的最小值
- @DecimalMax(value)  被注解的元素必须是一个数字，其值必须小于等于指定的最大值
- @Size(max=, min=)   被注解的元素的大小必须在指定的范围内
- @Digits (integer, fraction)     被注解的元素必须是一个数字，其值必须在可接受的范围内
- @Past   被注解的元素必须是一个过去的日期
- @Future     被注解的元素必须是一个将来的日期
- @Pattern(regex=,flag=)  被注解的元素必须符合指定的正则表达式

#### Hibernate Validator 附加的 constraint
- @NotBlank(message =)   验证字符串非null，且长度必须大于0
- @Email  被注解的元素必须是电子邮箱地址
- @Length(min=,max=)  被注解的字符串的大小必须在指定的范围内
- @NotEmpty   被注解的字符串的必须非空
- @Range(min=,max=,message=)  被注解的元素必须在合适的范围内

### 示例:

#### spring mvc 内置对 Hibernate Validator 的支持.

在前端返回的数据结构类中通过注解(这里是 @NotNull)定义数据校验规则
Person.java
```java
@Getter @Setter
public class Person {

    @NotNull( message = "id 必须填写")
    private Integer id;
}

```

在接收前端调用的控制器中设置校验(@Valid), 并接收校验处理结果 BindingResult
Controller.java
```java
@RequestMapping(
		value = "test",
		method = RequestMethod.POST,
		produces = MyMediaType.JSON
	)
public RestResponse someInterface(
	@RequestBody
	@Valid Person p,
	BindingResult errors) {

	// 如果检测到传入请求参数无效, 进行处理
	// 这里可以通过 debug 查看 errors 的数据结构
	if ( errors.hasErrors() ){
		// 处理 errors(记录日志, 返回错误消息给前端)
	}
	
	// 其它业务代码
}
```


#### 对普通域对象有效性校验
```java
public class Car {

  // 类成员变量校验
  @NotNull
  @Size(min = 2, max = 14)
  @Getter
  @Setter
  private String manufacturer;

  public Car(String manufacturer) {
    this.manufacturer = manufacturer;
  }


  public static void main(String[] args) {
    ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
        .configure()
        .buildValidatorFactory();
    Validator validator = factory.getValidator();

    Car car = new Car("c");
    Set<ConstraintViolation<Car>> violations = validator.validate(car);
    System.out.println(violations.size()); // size == 1 , 有违例
    System.out.println(violations);  // 可以查看具体检验错误信息
  }
}
```


### 注意事项:

### 参考链接
[hibernate validator getting started](http://docs.jboss.org/hibernate/validator/4.2/reference/zh-CN/html_single/#validator-gettingstarted)