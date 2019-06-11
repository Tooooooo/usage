### 目标:
在很多涉及到某种报表功能的Java程序中都需要生成Excel表格。

目前通过Java来操作.xls文件最完整的类库是Apache POI类库，但是当需要创建多种自定义的复杂Excel报表的时候就会出现问题，
这些Excel报表一般都带有多种格式和可扩展功能，在这种情况下，你就不得不写一大堆Java代码来创建报表的规则集（workbook），
规则集一般包含所有要求的格式,公式,其他特定的设置和正确的Java对象集的数据出口。这些代码一般都是难以调试，任务也常常变得容易出错并且耗时。

另外一个问题是有很多Excel组件都没有提供的API。幸运的是POI API读取Excel文件，可以保持它原有的格式，然后根据需要进行修改。很明显，用一些Excel编辑工具来创建所有格式正确的报告模板然后指定真实的数据应该放置的地方，会容易很多。

**JXLS是实现这种方法并且只用几行代码就能创建极其复杂的Excel报表。** 你只需要用特定的标记来创建一个带有所有要求的格式,公式,宏等规则的.xls模板文件来指定数据放置的位置然后再写几行代码来调用JXLS引擎来传递.xls模板和导出的数据作为参数。

除了生成Excel报表功能，JXLS还提供了jxls-reader模块，jxls-reader模块会很有用，如果你需要解析一个预定义格式的Excel文件并在其中插入数据的话。jxls-reader允许你用一个简单的XML文件描述解析规则，读取Excel文件和你的各种JAVA对象（population of yourJava objects）的所有其他工作都会自动完成。


### 核心组件:


### 依赖:

```gradle
// jxls
'org.jxls:jxls:2.4.3',
'org.jxls:jxls-poi:1.0.14',
'org.jxls:jxls-jexcel:1.0.6',
'org.jxls:jxls-reader:2.0.3'
```

### 使用方法:

### 示例:

#### 导入 xlsx 文件

departments.xml 配置文件定义导入规则.

department_data.xls 是需要被导入的数据文件.

执行代码将 excel 数据文件导出到 java 的数据结构

```java
@RequestMapping(value = "export", method = RequestMethod.GET)
  public void zyParticipantStatisticExp1(
      HttpServletResponse response) throws IOException {

    List<Data> result = dao.selectDataList(mp);

    DateFormat ttf = new SimpleDateFormat("HHmm");
    String filename = "xxx 报表[" + ttf.format(new Date()) + "].xlsx";
    response.setContentType(String.format("%s;charset=utf-8", "application/x"));
    response.setHeader("Content-Disposition", "attachment;filename=" +
        new String(filename.getBytes(StandardCharsets.UTF_8), "iso8859-1"));
    try (
        InputStream is = this.getClass().getResourceAsStream("template.xlsx");
        OutputStream os = response.getOutputStream()) {
      Context context = new Context();
      context.putVar("statistics", result);
      JxlsHelper.getInstance().processTemplate(is, os, context);
    }
  }

```

#### 导出到 xlsx 文件

使用 jxls 框架将数据导出为 excel 文件.

需要制作 excel 文件作为导出模板(本例为 template.xlsx), 模板控制格式, 然后由代码注入数据生成最终的 excel 文件

```java
@RequestMapping(value = "excelExport", method = RequestMethod.GET)
  public void excelExport(
      HttpServletResponse response) throws IOException {

    List<Data> result = dao.selectDataList(mp);

    DateFormat ttf = new SimpleDateFormat("HHmm");
    String filename = "xxx 报表[" + ttf.format(new Date()) + "].xlsx";
    response.setContentType(String.format("%s;charset=utf-8", "application/x"));
    response.setHeader("Content-Disposition", "attachment;filename=" +
        new String(filename.getBytes(StandardCharsets.UTF_8), "iso8859-1"));
    try (
        InputStream is = this.getClass().getResourceAsStream("template.xlsx");
        OutputStream os = response.getOutputStream()) {
      Context context = new Context();
      context.putVar("statistics", result);
      JxlsHelper.getInstance().processTemplate(is, os, context);
    }
  }

```

### 注意事项:

### 参考链接:

[JXLS 2.4.0学习](https://my.oschina.net/MeiJianMing/blog/1865468)

[官网](http://jxls.sourceforge.net/)