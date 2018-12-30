# JavaCommon
java 基础类收集

**[FileUtils](https://github.com/xuanu/JavaCommon/blob/master/src/cn/zeffect/common/file/FileUtils.java)**;//文件操作相关类
```
File maxPath();//获取剩余空间最大的磁盘
String read(String path);//读文件内容
boolean write(String path,String content);//写文件
boolean write(String filePath, String content, boolean append);//写文件，是否追加
String getFileMD5(File file);//文件MD5
String getFileMD5(InputStream in)//文件MD5
String fmtSpace(long space);//格式化显示磁盘空间
```
**[MD5Utils](https://github.com/xuanu/JavaCommon/blob/master/src/cn/zeffect/common/encrypt/MD5Utils.java)**;//MD5
```
String md5(String plainText);//32位md5
```

**[HttpUtils](https://github.com/xuanu/JavaCommon/blob/master/src/cn/zeffect/common/http/HttpUtils.java)**;//网络相关
```
boolean downFileSync(String url, File saveFile);//下载文件
网络请求可参考：https://github.com/hongyangAndroid/okhttputils
get请求不可使用addParams();
```

**[TextUtils](https://github.com/xuanu/JavaCommon/blob/master/src/cn/zeffect/common/text/TextUtils.java)**;//
```
boolean isEmpty(String msg);
boolean isBlank(String msg);
```

