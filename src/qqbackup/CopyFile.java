/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qqbackup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static qqbackup.BackUpProgress.currentNum;
//import static qqbackup.BackUpProgress.currentSize;

class CopyFile {

    /**
     * 复制文件到目录 <功能详细描述>
     *
     * @param srcPath 文件绝对路径
     * @param destDirPath 目标文件夹绝对路径
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static void copyFile(String srcPath, String destDirPath) throws Exception {
        File srcfile = new File(srcPath);
        File destDir = new File(destDirPath);

        InputStream is = null;
        OutputStream os = null;
        int ret = 0;

        // 源文件存在
        if (srcfile.exists() && destDir.exists() && destDir.isDirectory()) {
            try {
                is = new FileInputStream(srcfile);

                String destFile = destDirPath + File.separator + srcfile.getName();

                os = new FileOutputStream(new File(destFile));

                byte[] buffer = new byte[1024];

                while ((ret = is.read(buffer)) != -1) {
                    os.write(buffer, 0, ret); // 此处不能用os.write(buffer),当读取最后的字节小于1024时，会多写;
                    // ret是读取的字节数
                }
                os.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new Exception("");
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception("");
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                }
            }
        } else {
            throw new Exception("源文件不存在或目标路径不存在");
        }

    }

    /**
     * 列出文件夹下的所有文件，使用递归。 <功能详细描述>
     *
     * @param dirPath 文件夹绝对路径
     * @see [类、类#方法、类#成员]
     */
    public static void getFileList(String dirPath) {
        File rootDir = new File(dirPath);
        if (rootDir.exists()) {
            File[] files = rootDir.listFiles();

            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("目录" + file.getName());
                    // 递归调用
                    getFileList(file.getPath());
                } else {
                    System.out.println("文件" + file.getName());
                }
            }
        }
    }

    /**
     * <一句话功能简述>复制文件夹 <功能详细描述>
     *
     * @param srcDir 文件夹的绝对路径
     * @param destDir 目标绝对路径
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public static void copyFolder(String srcDir, String destDir) throws Exception {
        File srcFile = new File(srcDir);

        // 在目标路径建立文件夹
        String name = srcFile.getName();
        File destDirFile = new File(destDir);
            if (!destDirFile.exists()) {
                destDirFile.mkdirs();
            }
        File destFile = new File(destDir + File.separator + name);  //目的文件夹内的元文件夹名称路径
        if (!destFile.exists()) {
            destFile.mkdir();
        }
        if (srcFile.exists() && destFile.isDirectory()) {
            File[] files = srcFile.listFiles();

            String src = srcDir;
            String dest = destFile.getAbsolutePath();

            for (File temp : files) {
                // 复制目录
                if (temp.isDirectory()) {
                    String tempSrc = src + File.separator + temp.getName();
                    String tempDest = dest + File.separator + temp.getName();
                    File tempFile = new File(tempDest);
                    if(!tempFile.exists()){
                    tempFile.mkdir();
                    }
                    // 当子目录不为空时
                    if (temp.listFiles().length != 0) {
                        // 递归调用
                        copyFolder(tempSrc, dest);
                    }
                } else // 复制文件
                {
                    String tempPath = src + File.separator + temp.getName();
                    copyFile(tempPath, dest);
                }
            }
        }
        //  System.out.println("拷贝完成");
    }

    /**
     * 删除文件夹 <功能详细描述>
     * 要先删除子内容，再删除父内容
     *
     * @param dirPath 要删除的文件夹
     * @see [类、类#方法、类#成员]
     */
    public static void deleteFolder(String dirPath) {
        File folder = new File(dirPath);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                String tempFilePath = dirPath + File.separator + file.getName();
                deleteFolder(tempFilePath);
            } else {
                file.delete();
            }

        }

        folder.delete();
    }

}
