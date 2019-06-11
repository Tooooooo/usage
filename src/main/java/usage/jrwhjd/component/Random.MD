### 目标:
快速的生成各种要求的伪随机数, 以及衍生出的各类随机应用.

### 核心组件
> com.jrwhjd.infrastructure.util.RandomUtils

### 依赖:
> JRJDBase.jar

### 使用方法:
在代码中调用 com.jrwhjd.infrastructure.util.RandomUtils 的 static 方法.

```java
nonRepeatRandomsInt
public static int[] nonRepeatRandomsInt(int minimum,
                                        int maximum,
                                        int amount)
生成指定区间内, 指定数量的不重复的伪随机整数.
生成的伪随机整数 N 满足条件: minimum <= N < maximum
参数:
minimum - 随机数的最小值
maximum - 随机数的最大值
amount - 生成随机数的数量
返回:
伪随机数组成的整型数组, 元素个数取决于 amount 参数的值
------

randomInt
public static int randomInt(int minimum,
                            int maximum)
生成指定区间内的伪随机整数. 生成的随机数 N 满足条件: minimum <= N < maximum.
参数:
minimum - 随机数的最小值
maximum - 随机数的最大值
返回:
指定区间的伪随机数
------

noneRepeatRandom
public static java.lang.Object[] noneRepeatRandom(java.lang.Object[] stuff,
                                                  int size)
从备选的对象数组中随机抽取不重复对象, 保存在长度为 size 的数组中返回. 其中 stuff.length > size > 2
参数:
stuff - 备选对象的数组, 不能有重复
size - 指定返回的随机不重复对象数组的大小
返回:
保存不重复随机对象的数组
抛出:
java.lang.IllegalArgumentException - 当 size <= 2 || stuff.length <= size 时抛出
------

randomDate
public static java.util.Date randomDate(java.lang.String beginDate,
                                        java.lang.String endDate)
获取随机日期.
参数:
beginDate - 起始日期，格式为：yyyy-MM-dd
endDate - 结束日期，格式为：yyyy-MM-dd
返回:
获取指定范围内的某个随机日期
------

randomPassWord
public static java.lang.String randomPassWord(int len)
获得随机密码.
参数:
len - 生成密码长度
返回:
随机密码
```

### 示例:
```java
    // 随机生成一个 10~100 之间的伪随机数
    System.out.println(RandomUtils.randomInt(10, 100));
    
    // 以数组的形式产生4个 1~100 的伪随机数
    System.out.println(Arrays.toString(RandomUtils.nonRepeatRandomsInt(1, 100, 4)));
    
    // 从 data 中选取不重复的3个元素组成新的数组
    String[] dat = new String[]{"foo", "bar", "baz", "qux", "quux", "corge"};
    System.out.println(Arrays.toString(RandomUtils.noneRepeatRandom(dat, 3)));
    
    // 随机选取 "2010-11-21", "2019-05-01" 之间的一个日期
    System.out.println(df.format(RandomUtils.randomDate("2010-11-21", "2019-05-01")));
    
    // 随机生成一个6位的密码, 密码由字母和数字以及下划线组成
    System.out.println(RandomUtils.randomPassWord(6));
```

### 注意事项:

### 参考链接
[源码地址](https://gitlab.ctbiyi.com/jrjd/jrjdbase/blob/master/src/main/java/com/jrwhjd/infrastructure/util/RandomUtils.java)