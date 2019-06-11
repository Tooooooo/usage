### 目标:
提供操作 String 类型数据常用工具方法.

### 核心组件
> org.apache.commons.lang3.StringUtils

### 依赖:
```gradle
compile group: 'org.apache.commons', name: 'commons-lang3', version: '3.8.1'
```

### 使用方法:

#### 判空函数
1. StringUtils.isEmpty(String str)
2. StringUtils.isNotEmpty(String str)
3. StringUtils.isBlank(String str)
4. StringUtils.isNotBlank(String str)
5. StringUtils.isAnyBlank(CharSequence… css)
6. StringUtils.isAnyEmpty(CharSequence… css)
7. StringUtils.isNoneBlank(CharSequence… css)
8. StringUtils.isNoneEmpty(CharSequence… css)
9. StringUtils.isWhitespace(CharSequence cs)

#### 大小写函数
1. StringUtils.capitalize(String str)
2. StringUtils.uncapitalize(String str)
3. StringUtils.upperCase(String str)
4. StringUtils.upperCase(String str,Locale locale)
5. StringUtils.lowerCase(String str)
6. StringUtils.lowerCase(String str,Locale locale)
7. StringUtils.swapCase(String str)
8. StringUtils.isAllUpperCase(CharSequence cs)
9. StringUtils.isAllLowerCase(CharSequence cs)

#### 删除函数
1. StringUtils.remove(String str, char remove)
2. StringUtils.remove(String str, String remove)
3. StringUtils.removeEnd(String str, String remove)
4. StringUtils.removeEndIgnoreCase(String str, String remove)
5. StringUtils.removePattern(String source, String regex)
6. StringUtils.removeStart(String str, String remove)
7. StringUtils.removeStartIgnoreCase(String str, String remove)
8. StringUtils.deleteWhitespace(String str)

#### 替换函数
1. replace(String text, String searchString, String replacement)
2. replace(String text, String searchString, String replacement, int max)
3. replaceChars(String str, char searchChar, char replaceChar)
4. replaceChars(String str, String searchChars, String replaceChars)
5. replaceEach(String text, String[] searchList, String[] replacementList)
6. replaceEachRepeatedly(String text, String[] searchList, String[] replacementList)
7. replaceOnce(String text, String searchString, String replacement)
8. replacePattern(String source, String regex, String replacement)
9. overlay(String str,String overlay,int start,int end) 

#### 反转函数
1. reverse(String str)
2. reverseDelimited(String str, char separatorChar)

#### 分隔合并函数
1. split(String str)
2. split(String str, char separatorChar)
3. split(String str, String separatorChars)
4. split(String str, String separatorChars, int max)
5. splitByCharacterType(String str)
6. splitByCharacterTypeCamelCase(String str)
7. splitByWholeSeparator(String str, String separator)
8. splitByWholeSeparator(String str, String separator, int max)
9. splitByWholeSeparatorPreserveAllTokens(String str, String separator)
10. splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max)
11. splitPreserveAllTokens(String str)
12. splitPreserveAllTokens(String str, char separatorChar)
13. splitPreserveAllTokens(String str, String separatorChars)
14. splitPreserveAllTokens(String str, String separatorChars, int max)
15. join(byte[] array,char separator)
16. join(Object[] array,char separator)
17. join(Object[] array,char separator,int startIndex,int endIndex)

#### 截取函数
1. substring(String str,int start)
2. substringAfter(String str,String separator)
3. substringBeforeLast(String str,String separator)
4. substringAfterLast(String str,String separator)
5. substringBetween(String str,String tag)

#### 相似度函数
一个字符串可以通过增加一个字符，删除一个字符，替换一个字符得到另外一个字符串，假设，我们把从字符串A转换成字符串B，前面3种操作所执行的最少次数称为AB相似度.

1. getLevenshteinDistance(CharSequence s, CharSequence t)
2. getLevenshteinDistance(CharSequence s, CharSequence t, int threshold)


#### 缩短省略函数
1. abbreviate(String str, int maxWidth)
2. abbreviate(String str, int offset, int maxWidth)
3. abbreviateMiddle(String str, String middle, int length)

#### 删除空白函数
1. rim(String str)
2. trimToEmpty(String str)
3. trimToNull(String str)
4. deleteWhitespace(String str)

#### 其它函数
1. countMatches(CharSequence str,char ch)  // 匹配计数函数
2. difference(String str1,String str2)  // 差异函数

#### 默认字符函数
1. defaultString(String str)
2. defaultString(String str,String defaultStr)

#### 填充居中函数
1. eftPad/rightPad(String str,int size)
2. leftPad(String str,int size,char padChar)
3. center(String str,int size)
4. center(String str,int size,char padChar)
5. repeat(char ch,int repeat)
6. repeat(String str,String separator,int repeat)
7. appendIfMissing(String str, CharSequence suffix, CharSequence… suffixes)
8. appendIfMissing(String str,CharSequence suffix,CharSequence… suffixes)

#### 是否包含函数
1. ontainsOnly(CharSequence cs,char… valid)
2. containsNone(CharSequence cs,char… searchChars)
3. startsWith(CharSequence str,CharSequence prefix)
4. startsWithIgnoreCase(CharSequence str,CharSequence prefix)
5. startsWithAny(CharSequence string,CharSequence… searchStrings)

#### 索引下标函数
1. ndexOf(CharSequence seq,CharSequence searchSeq)
2. indexOf(CharSequence seq,CharSequence searchSeq,int startPos)
3. indexOfIgnoreCase/lastIndexOfIgnoreCase(CharSequence str,CharSequence searchStr)
4. lastIndexOf(CharSequence seq,int searchChar)

### 示例:
#### 判空函数

> isNotEmpty = !isEmpty, isBlank同理；

容易忽略的:
>  StringUtils.isEmpty("") = true

isBlank和isEmpty区别：
```java
System.out.println(StringUtils.isBlank(" ")); //true
System.out.println(StringUtils.isBlank("   ")); //true
System.out.println(StringUtils.isBlank("\n\t")); //true
//区别
StringUtils.isEmpty(" ") = false
```

isAnyBlank和isAnyEmpty是多维判空，存在一个blank或者empty既true
```java
StringUtils.isAnyBlank("", "bar","foo") = true
//注意这两个区别
StringUtils.isAnyEmpty(" ", "bar") = false
StringUtils.isAnyEmpty(" ", "bar") = true
```

isNoneBlank = !isAnyBlank；isNoneEmpty同理
```java
public static boolean isNoneBlank(CharSequence... css) {
  return !isAnyBlank(css);
}
```

isWhitespace判断空白
```java
StringUtils.isWhitespace(null) = false
StringUtils.isWhitespace("") = true
StringUtils.isWhitespace("  ") = true
```

#### 大小写函数
capitalize首字母大写，upperCase全部转化为大写，swapCase大小写互转；
```java
StringUtils.capitalize(null)  = null
StringUtils.capitalize("")    = ""
//首字母转为大写
StringUtils.capitalize("cat") = "Cat"
//全部转为大写
StringUtils.upperCase("aBc") = "ABC"
//大小写互转
StringUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
```

isAllUpperCase是否全部大写，isAllLowerCase是否全部小写
```java
StringUtils.isAllLowerCase("  ")   = false
StringUtils.isAllLowerCase("abc")  = true
StringUtils.isAllLowerCase("abC")  = false
StringUtils.isAllLowerCase("ab c") = false
StringUtils.isAllLowerCase("ab1c") = false
StringUtils.isAllLowerCase("ab/c") = false
```

#### 删除函数

```java
//删除字符
StringUtils.remove("queued", 'u') = "qeed"
//删除字符串
StringUtils.remove("queued", "ue") = "qd"
//删除结尾匹配的字符串     
StringUtils.removeEnd("www.domain.com", ".com")   = "www.domain"
//删除结尾匹配的字符串,找都不到返回原字符串
StringUtils.removeEnd("www.domain.com", "domain") = "www.domain.com"
//忽略大小写的
StringUtils.removeEndIgnoreCase("www.domain.com", ".COM") = "www.domain")
//删除所有空白（好用）
StringUtils.deleteWhitespace("abc")        = "abc"
StringUtils.deleteWhitespace("   ab  c  ") = "abc"
```

#### 替换函数

```java
StringUtils.replace("aba", "a", "")    = "b"
StringUtils.replace("aba", "a", "z")   = "zbz"    
//数字就是替换个数，0代表不替换，1代表从开始数起第一个，-1代表全部替换
StringUtils.replace("abaa", "a", "", -1)   = "b"
StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
```

replaceEach是replace的增强版，搜索列表和替换长度必须一致，否则报IllegalArgumentException异常：
```java
StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
StringUtils.replaceChars("dabcba", "bcd", "yzx") = "xayzya"
StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
```

replaceOnce只替换一次：
```java
StringUtils.replaceOnce("aba", "a", "")    = "ba"
StringUtils.replaceOnce("aba", "a", "z")   = "zba"
StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}) = "wcte"
//这是一个非常奇怪的函数，本来自己料想结果应该是“dcte”的，可是结果居然是tcte，而dcte再替换一次就是tcte了。
StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}) = "tcte"
```

overlay
```java
StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
```

#### 反转函数
```java
StringUtils.reverse("bat") = "tab"
StringUtils.reverseDelimited("a.b.c", 'x') = "a.b.c"
StringUtils.reverseDelimited("a.b.c", ".") = "c.b.a"
```

#### 分割合并函数
```java
//用空白符做空格
StringUtils.split("abc def")  = ["abc", "def"]
StringUtils.split("abc  def") = ["abc", "def"]
StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
//用字符分割
StringUtils.split("a:b:c", '.')    = ["a:b:c"]
//0 或者负数代表没有限制
StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
//分割字符串 ,可以设定得到数组的长度，限定为2
StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
//null也可以作为分隔
StringUtils.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
StringUtils.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
StringUtils.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
//带有限定长度的分隔
StringUtils.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
```

join 函数
```java
//只有一个参数的join，简单合并在一起
StringUtils.join(["a", "b", "c"]) = "abc"
StringUtils.join([null, "", "a"]) = "a"    
//null的话，就是把字符合并在一起
StringUtils.join(["a", "b", "c"], null) = "abc"
//从index为0到3合并，注意是排除3的
StringUtils.join([null, "", "a"], ',', 0, 3)   = ",,a"
StringUtils.join(["a", "b", "c"], "--", 0, 3)  = "a--b--c"
//从index为1到3合并，注意是排除3的
StringUtils.join(["a", "b", "c"], "--", 1, 3)  = "b--c"
StringUtils.join(["a", "b", "c"], "--", 2, 3)  = "c"
```

#### 截取函数
```java
StringUtils.substring("abcdefg", 0)  = "abcdefg"
StringUtils.substring("abcdefg", 2)  = "cdefg"
StringUtils.substring("abcdefg", 4)  = "efg"

//start>0表示从左向右, start<0表示从右向左, start=0则从左第一位开始
StringUtils.substring("abcdefg", -2) = "fg"
StringUtils.substring("abcdefg", -4) = "defg"

//从第二个参数字符串开始截取，排除第二个字符串
StringUtils.substringAfter("abc", "a")   = "bc"
StringUtils.substringAfter("abcba", "b") = "cba"
StringUtils.substringAfter("abc", "c")   = ""

//从最后一个字母出现开始截取
StringUtils.substringBeforeLast("abcba", "b") = "abc"
StringUtils.substringBeforeLast("abc", "c")   = "ab"
StringUtils.substringBeforeLast("a", "a")     = ""
StringUtils.substringBeforeLast("a", "z")     = "a"

StringUtils.substringAfterLast("abc", "a")   = "bc"
StringUtils.substringAfterLast("abcba", "b") = "a"
StringUtils.substringAfterLast("abc", "c")   = ""
StringUtils.substringBetween("tagabctag", null)  = null
StringUtils.substringBetween("tagabctag", "")    = ""
StringUtils.substringBetween("tagabctag", "tag") = "abc"
```

#### 差异函数
```java
//在str1中寻找str2中没有的的字符串，并返回     
StringUtils.difference("", "abc") = "abc"
StringUtils.difference("abc", "") = ""
StringUtils.difference("abc", "abc") = ""
StringUtils.difference("abc", "ab") = ""
StringUtils.difference("ab", "abxyz") = "xyz"
StringUtils.difference("abcde", "abxyz") = "xyz"
StringUtils.difference("abcde", "xyz") = "xyz"
```

#### 缩短省略函数

注意：
- 字符串的长度小于或等于最大长度，返回该字符串。
- 运算规律(substring(str, 0, max-3) + “…”)
- 如果最大长度小于4，则抛出异常。

```java
//(substring(str, 0, 6-3) + "...")
StringUtils.abbreviate("abcdefg", 6) = "abc..."
StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
StringUtils.abbreviate("abcdefg", 4) = "a..."
StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
```

#### 匹配计数函数
```java
StringUtils.countMatches("abba", 0)  = 0
StringUtils.countMatches("abba", 'a')   = 2
StringUtils.countMatches("abba", 'b')  = 2
StringUtils.countMatches("abba", 'x') = 0
```

#### 删除空白函数
```java
StringUtils.trim("     ")       = ""
StringUtils.trim("abc")         = "abc"
StringUtils.trim("    abc    ") = "abc"
//空的话，返回null
StringUtils.trimToNull("     ")       = null
StringUtils.trimToNull("abc")         = "abc"
StringUtils.trimToNull("    abc    ") = "abc"
StringUtils.trimToEmpty("     ")       = ""
StringUtils.trimToEmpty("abc")         = "abc"
StringUtils.trimToEmpty("    abc    ") = "abc"
```

注意这两者的区别
```java
StringUtils.deleteWhitespace("")           = ""
StringUtils.deleteWhitespace("abc")        = "abc"
StringUtils.deleteWhitespace("   ab  c  ") = "abc"
```

#### 判断是否相等函数
```java
StringUtils.equals("abc", null)  = false
StringUtils.equals("abc", "abc") = true
StringUtils.equals("abc", "ABC") = false
//忽略大小写
StringUtils.equalsIgnoreCase("abc", null)  = false
StringUtils.equalsIgnoreCase("abc", "abc") = true
StringUtils.equalsIgnoreCase("abc", "ABC") = true
```

#### 填充居中函数
```java
StringUtils.leftPad("bat", 3)  = "bat"
//左填充，默认填充空         
StringUtils.leftPad("bat", 5)  = "  bat"
StringUtils.leftPad("bat", 1)  = "bat"
StringUtils.leftPad("bat", -1) = "bat"
//左填充，填充字符为z
StringUtils.leftPad("bat", 3, 'z')  = "bat"
StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
StringUtils.leftPad("bat", 1, 'z')  = "bat"
StringUtils.leftPad("bat", -1, 'z') = "bat"
//居中
StringUtils.center("ab", -1)  = "ab"
StringUtils.center("ab", 4)   = " ab "
StringUtils.center("abcd", 2) = "abcd"
StringUtils.center("a", 4)    = " a  
//居中，最后一个参数是填充字符或字符串
StringUtils.center("abcd", 2, ' ') = "abcd"
StringUtils.center("a", 4, ' ')    = " a  "
StringUtils.center("a", 4, 'y')    = "yayy"
//重复字符串，第二个参数是重复次数
StringUtils.repeat("a", 3)  = "aaa"
StringUtils.repeat("ab", 2) = "abab"
StringUtils.repeat("a", -2) = ""
//重复字符串，第二个参数是分割符，第三个参数是重复次数
StringUtils.repeat("", "x", 3)    = "xxx"
StringUtils.repeat("?", ", ", 3)  = "?, ?, ?"

StringUtils.appendIfMissing("abcxyz", "xyz", "mno") = "abcxyz"
StringUtils.appendIfMissing("abcmno", "xyz", "mno") = "abcmno"
StringUtils.appendIfMissing("abcXYZ", "xyz", "mno") = "abcXYZxyz"
StringUtils.appendIfMissing("abcMNO", "xyz", "mno") = "abcMNOxyz"
```

#### 是否包含函数
```java
//判断字符串中所有字符，是否都是出自参数2中
StringUtils.containsOnly("ab", "")      = false
StringUtils.containsOnly("abab", "abc") = true
StringUtils.containsOnly("ab1", "abc")  = false
StringUtils.containsOnly("abz", "abc")  = false

//判断字符串中所有字符，都不在参数2中。
StringUtils.containsNone("abab", 'xyz') = true
StringUtils.containsNone("ab1", 'xyz')  = true
StringUtils.containsNone("abz", 'xyz')  = false

//判断字符串是否以第二个参数开始
StringUtils.startsWith("abcdef", "abc") = true
StringUtils.startsWith("ABCDEF", "abc") = false
```

#### 索引下标函数
```java
//返回第二个参数开始出现的索引值
StringUtils.indexOf("aabaabaa", "a")  = 0
StringUtils.indexOf("aabaabaa", "b")  = 2
StringUtils.indexOf("aabaabaa", "ab") = 1
 
//从第三个参数索引开始找起，返回第二个参数开始出现的索引值
StringUtils.indexOf("aabaabaa", "a", 0)  = 0
StringUtils.indexOf("aabaabaa", "b", 0)  = 2
StringUtils.indexOf("aabaabaa", "ab", 0) = 1
StringUtils.indexOf("aabaabaa", "b", 3)  = 5
StringUtils.indexOf("aabaabaa", "b", 9)  = -1  
//返回第二个参数出现的最后一个索引值        
StringUtils.lastIndexOf("aabaabaa", 'a') = 7
StringUtils.lastIndexOf("aabaabaa", 'b') = 5
 
StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
```

### 注意事项:
StringUtils 工具类极其强大, 基本覆盖了日常业务的需求, 因此在代码编写中需要操作String对象, 应优先考虑使用 StringUtils 而不是自己写.

### 参考链接:
[Javadoc](http://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/StringUtils.html)