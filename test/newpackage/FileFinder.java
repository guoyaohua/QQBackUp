/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newpackage;
//package IO.file;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
* 文件搜索器
* @author chen
*
*/
public class FileFinder {

/**
* 查找文件
* @param baseDirName 待查找的目录
* @param targetFileName 目标文件名，支持通配符形式
* @param count 期待结果数目，为0表示查找全部
* @return 满足查询条件的文件名列表
*/
public static List findFiles(String baseDirName, String targetFileName, int count) {
   /**
   * 算法简述:
   * 从某个给定的待查找的文件夹出发，搜索该文件夹下的所有文件和子文件夹
   * 如果是文件，则进行匹配，匹配成功则加入结果集，如果是文件夹，则进入队列
   * 队列不空，继续上述操作，队列为空，程序结束，返回结果
   */
  
   List fileList = new ArrayList();
   // 判断目录是否存在
   File baseDir = new File(baseDirName);
   if(!baseDir.exists() || !baseDir.isDirectory()) {
    System.out.println("查找文件失败：" + baseDirName + "不是一个目录!");
    return fileList;
   }
   String tempName = null;
   Queue queue = new LinkedList();
   queue.offer(baseDir);
   File tempFile = null;
   while(!queue.isEmpty()) {
    tempFile = (File) queue.poll();
    if(tempFile.exists() && tempFile.isDirectory()) {
     File[] files = tempFile.listFiles();
     for(int i = 0; i < files.length; i++) {
      // 如果是目录则放进队列
      if(files[i].isDirectory())
       queue.offer(files[i]);
      else {
       // 如果是文件则根据文件名与目标文件名进行匹配
       tempName = files[i].getName();
       if(wildcardMatch(targetFileName,tempName)) 
        fileList.add(files[i].getAbsoluteFile());
       // 如果已经到达指定的数目，则退出循环
       if(count != 0 && fileList.size() > count)
        return fileList;
      }
     }
    }
   
   }
   return fileList;
}

/**
* 通配符匹配
* @param pattern 通配符模式
* @param str 待匹配的字符串
* @return 匹配成功返回true，否则返回false
*/
private static boolean wildcardMatch(String pattern, String str) {
   int patternLength = pattern.length();
   int strLength = str.length();
   int strIndex = 0;
   char ch;
   for(int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
    ch = pattern.charAt(patternIndex);
    if(ch == '*') {
     // 通配符"*"，表示可以匹配任意多个字符
     while(strIndex < strLength) {
      if(wildcardMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
       return true;
      }
      strIndex++;
     }
    } else if(ch == '?') {
     // 通配符"?"表示匹配任意一个字符
     strIndex++;
     if(strIndex > strLength) {
      // 表示str中已经没有字符匹配"?"了
      return false;
     }
    } else {
     if(strIndex >= strLength || ch != str.charAt(strIndex))
      return false;
     strIndex++;
    }
   }
   return (strIndex == strLength);
}

public static void main(String[] args) {

   String baseDir = "C://";
   String fileName = "findme.txt";
   int count = 0;
   List result = FileFinder.findFiles(baseDir, fileName, count);
   for(int i = 0; i < result.size(); i++)
    System.out.println(result.get(i));
}

}