/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newpackage;

import java.io.*;
public class FileSearch {
  public static void main(String[] args) {
    System.out.println("请输入关键字:");
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    String s = null;
    try {
      s = br.readLine();
    }
    catch (IOException ex) {
      System.out.println("输入有误,请重新输入!");
    }
    System.out.println("请输入盘符:(格式如c: )");
    String Root = null;
    try
    {
      Root=br.readLine();
    }
    catch(IOException iex)
    {
      System.out.println("输入有误,请重新输入!");
    }
    search(s, Root);
  }
  public static void search(String file, String Rootstring) {
    //显示文件和文件夹
    File f = new File(Rootstring);
    String[] fileList = f.list();
    for (int i = 0; i < fileList.length; i++) {
      String str = Rootstring + fileList[i] + " ";
      if (fileList[i].indexOf(file) > 0) {
        System.out.println(Rootstring + fileList[i]);
      }
      if (ChecksDiretory(str)) {
        search(file, str);
      }
    }
  }
  public static boolean ChecksDiretory(String str) {
    File f = new File(str);
    return f.isDirectory();
  }
}