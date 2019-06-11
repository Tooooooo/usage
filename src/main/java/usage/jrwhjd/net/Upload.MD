### 目标:
统一文件上传所使用的组件以及流程.

### 核心组件:
- org.apache.commons.fileupload.servlet.ServletFileUpload
- org.apache.commons.fileupload.FileItemStream
- org.apache.commons.io.IOUtils

### 依赖:
```gradle
"org.springframework:spring-webmvc:$springVersion",
"org.springframework:spring-tx:$springVersion",
"org.springframework:spring-jdbc:$springVersion",
'org.projectlombok:lombok:1.18.4',

// commons
'org.apache.commons:commons-lang3:3.5',
'commons-io:commons-io:2.5',
'commons-fileupload:commons-fileupload:1.3.3',
'commons-configuration:commons-configuration:1.10',
'commons-collections:commons-collections:3.2.2',

'com.fasterxml.jackson.core:jackson-databind:2.8.6',
'com.fasterxml.jackson.core:jackson-core:2.8.6',
'com.fasterxml.jackson.core:jackson-annotations:2.8.6',

// log
'org.slf4j:slf4j-api:1.7.22',
'org.apache.logging.log4j:log4j-core:2.8',
'org.apache.logging.log4j:log4j-api:2.8',
'org.apache.logging.log4j:log4j-slf4j-impl:2.8',
```
### 使用方法:
无需进行额外配置, 直接套用示例代码并加上自己的业务逻辑

### 示例:

示例代码有三个方法: 接收客户端上传的文件并保存到磁盘, 删除已上传的文件以及已获取上传文件的列表

```java
/**
 * @author Created by tuding on 2018/5/17.
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileUploadController {

  private static final char SPT = File.separatorChar;
  private static String PATH;

  /**
   * 上传的文件能否覆盖已存在的同名文件.
   */
  @Getter
  @Setter
  private boolean overwritable = false;

  /* constructor */
  public FileUploadController() {
    // 实例化时就要确定文件的上传路径: 从配置文件中读取
    PATH = Preference.current().getString("file.upload.path", "e:\\tmp");
    log.debug("upload path was set to {}", PATH);
  }


  /**
   * 从前端接收包含上传文件数据请求并处理
   * @param request 由 spring mvc 注入的 HttpServletRequest 实例
   
   * @return org.springframework.http.ResponseEntity
   */
  @RequestMapping(value = "upload", method = RequestMethod.POST)
  public ResponseEntity receive(HttpServletRequest request) {
    // 开始转换 request 对象
    FileItemIterator iter = null;
    try {
      iter = new ServletFileUpload().getItemIterator(request);
      while (iter.hasNext()) {
        FileItemStream item = iter.next();
        // 处理非文件的表单项
        if (item.isFormField()) {
          String fName = item.getFieldName();  // 表单项名称
          String fValue = Streams.asString(item.openStream());  // 表单项的值
          // 可以在此处理上述 fName 和 fValue 的业务逻辑
          continue;
        }
        @NotNull
        String filename = PATH + SPT + item.getName();
        log.debug("上传文件的完全路径:{}", filename);
        if (Files.exists(Paths.get(filename)) && !isOverwritable()) {
          log.warn("文件已存在, 无法覆盖上传: {}", filename);
          return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        try (
            InputStream fin = item.openStream();
            // 本示例直接将文件保存到磁盘, 可以将文件流的目的地指向其它地方, 比如数据库
            OutputStream fout = IOUtils.buffer(new FileOutputStream(filename))) {
          IOUtils.copy(fin, fout);
          log.debug("文件 {} 上传完成", filename);
        }
      }
    } catch (FileUploadException | IOException e) {
      e.printStackTrace();
    }
    return ResponseEntity.ok().build();
  }


  /**
   * 依前端传回的文件名要求, 从磁盘删除指定的文件
   * @param request 由 spring mvc 注入的 HttpServletRequest 实例
   * @param f 请求删除的文件名(不包含路径)
   * @return org.springframework.http.ResponseEntity
   */
  @RequestMapping(value = "remove", method = RequestMethod.GET)
  public ResponseEntity delete(
      HttpServletRequest request,
      @RequestParam(value = "f", required = true) String f) {
    String filename = PATH + SPT + f;
    Path path = Paths.get(filename);
    if (!Files.exists(path)) {
      log.warn("准备删除的文件并不存在: {}", f);
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    try {
      Files.delete(path);
    } catch (IOException e) {
      log.error("文件删除失败:{}", path.getFileName());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    log.info("已删除文件: {}", path);
    return ResponseEntity.ok().build();
  }


  /**
   * 获得已上传的文件的列表
   * @param request 由 spring mvc 注入的 HttpServletRequest 实例
   * @return 包含所有已上传文件信息(FileInfo)的 List
   */
  @RequestMapping(value = "list", method = RequestMethod.GET, produces = MyMediaType.JSON)
  public List<FileInfo> list(HttpServletRequest request) {
    List<FileInfo> result = new ArrayList<>();
    try (DirectoryStream<Path> dir = Files.newDirectoryStream(Paths.get(PATH))) {
      dir.forEach(path -> result.add(new FileInfo(path)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

}
```

### 注意事项:
支持同时上传多个文件

### 参考链接: