/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newpackage;

/**
 *
 * @author John Kwok
 */
import java.io.*;

public class FindFile{
 public static final String FILE_DIR = "C:\\"; //要搜索的文件夹
 public static void main(String[] args){
  File dir = new File(FILE_DIR);
  String[] availableDataFiles = dir.list(new FilenameFilterImpl()); //得到所有符合要求的文件   
  for(String f : availableDataFiles) { //循环处理每个文件
   File aFile = new File(FILE_DIR + f);
   System.out.println(aFile.toString()); //做你要的处理，比如打印文件内容等。这里只是列出文件路径。
  }
 }
 
 //这个类用来判断搜索到的文件是不是符合要求
 private static class FilenameFilterImpl implements FilenameFilter {
        public FilenameFilterImpl() {
        }

        public boolean accept(File dir, String name) {            
            return name.matches("findme.txt"); //正则表达式。只有.txt文件符合要求。
        }
    }
 
}