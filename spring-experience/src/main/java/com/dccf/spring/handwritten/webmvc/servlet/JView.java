package com.dccf.spring.handwritten.webmvc.servlet;

import cn.hutool.core.io.IoUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JView {
  // 目标文件
  private File targetFile;

  private String makeStringForRegExp(String str) {
    return str.replace("\\", "\\\\")
        .replace("*", "\\*")
        .replace("+", "\\+")
        .replace("|", "\\|")
        .replace("{", "\\}")
        .replace("}", "\\}")
        .replace("(", "\\(")
        .replace(")", "\\)")
        .replace("^", "\\^")
        .replace("$", "\\$")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("?", "\\?")
        .replace(",", "\\,")
        .replace(".", "\\.")
        .replace("&", "\\&");
  }

  public JView(String targetFile) {
    this(new File(targetFile));
  }

  public JView(File targetFile) {
    this.targetFile = targetFile;
  }

  public JView() {}

  public File getTargetFile() {
    return targetFile;
  }

  public void setTargetFile(String targetFile) {
    setTargetFile(new File(targetFile));
  }

  public void setTargetFile(File targetFile) {
    this.targetFile = targetFile;
  }

  public void render(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) {
    StringBuffer buff = new StringBuffer();
    RandomAccessFile raf = null;
    try {
      /*
      RandomAccessFile是一个可以随机访问文件的类，也就是说，你可以在文件中任意位置进行读写操作，而不仅仅是只能从头开始读取
      r表示只读模式
      所以，这段代码的作用是以只读模式打开由getTargetFile()方法返回的文件。
      注意：使用RandomAccessFile进行写操作时，需要将文件位置指针移动到你想要开始写入的偏移量。如果只是以只读模式打开文件，那么文件指针默认位于文件的开头。
       */
      raf = new RandomAccessFile(getTargetFile(), "r");
      String line = null;
      while (null != (line = raf.readLine())) {
        line = new String(line.getBytes("iso-8859-1"), "utf-8");

        /*
        这段代码是Java中的正则表达式模式编译。让我们逐步解析这段代码：

        Pattern.compile(...): 这是Java的正则表达式模式编译方法，用于创建一个正则表达式模式对象。

        "!\\{[^\\}]+\\}": 这是要编译的正则表达式字符串。

        !：匹配一个感叹号字符。
        \\{ 和 \\}：分别匹配左大括号“{”和右大括号“}”。在正则表达式中，大括号是特殊字符，所以前面需要使用反斜杠进行转义。
        [^\\}]+：这是一个字符集（character set）。它匹配一个或多个不是右大括号“}”的字符。[^...]表示匹配不在括号内的任何字符。
        Pattern.CASE_INSENSITIVE: 这是一个标志，表示在匹配时忽略大小写。

        所以，这段代码的意思是：创建一个正则表达式模式，该模式可以匹配以感叹号开头，后跟一个左大括号“{”，然后是一个或多个不是右大括号“}”的字符，并以右大括号“}”结尾的字符串，且在匹配时忽略大小写。
          */
        Pattern p = Pattern.compile("!\\{[^\\}]+\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(line); // 该对象可用于在给定的输入字符串中查找与指定正则表达式模式匹配的子字符串。并且对这个子字符串进行如查找、替换、分割等操作。
        while (matcher.find()) { // 它用于在输入的字符串中查找与正则表达式模式匹配的子字符串。这个方法会从模式匹配器的状态开始进行搜索。并返回找到的第一个匹配项。如果没有找到匹配项，则返回false。

          /*
          在正则表达式中，!\\{|\\} 的作用是匹配特定的字符串模式。

          具体来说：

          !：匹配一个感叹号。
          \\{ 和 \\}：分别匹配左大括号“{”和右大括号“}”。在正则表达式中，大括号是特殊字符，所以前面需要使用反斜杠进行转义。
          这个正则表达式可以用来匹配以感叹号开头，后跟一个左大括号“{”，然后是一个或多个不是右大括号“}”的字符，并以右大括号“}”结尾的字符串。

          这样的字符串可能用于表示命令、标记或者特定的语法结构等。在具体的应用场景中，它可以帮助你提取、识别或者处理特定的字符串模式。
             */
          String paramName = matcher.group().replaceAll("!\\{|\\}", ""); // group()这个方法用于获取匹配的子字符串。
          Object paramVal = model.get(paramName);
          if (paramVal != null) {
            line = matcher.replaceFirst(makeStringForRegExp(paramVal.toString()));
            matcher = p.matcher(line); // 对目标字符串line进行匹配
          }
        }
        buff.append(line);
      }
      resp.getWriter().write(buff.toString());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IoUtil.close(raf);
    }
  }
}
