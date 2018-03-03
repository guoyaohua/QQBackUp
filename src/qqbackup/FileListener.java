package qqbackup;

import java.awt.event.ActionEvent;
import java.io.File;

public class FileListener implements java.awt.event.ActionListener, Runnable {

    private static String content;//确保一变化线程中即可调用
    private String root = "C:\\";
    private static File[] listFile;//确保一变化线程中即可调用
//	private JTextField tf;
//	private JComboBox cb;
//	private JTextArea ta;
    private String fileName;

    private static String item = "";//通过item来判定执行run中的哪一个方法
    private Thread t;//统一设定线
    private static int count = 0;//统计出现的文件个数
    private FileListener fl;//控制线程的启动
    private static String s = "";//标识是否停止寻找
    private int threadNum = 0;
    private File[] rootlist;
    private int success = 0;

    public FileListener() {

    }

    public FileListener(String fileName, String root) {
        this.root = root;
        this.fileName = fileName;
        File file1 = new File("");
        rootlist = file1.listRoots();
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("响应事件");
        QQBackUp.msgLabel.setText("程序正在努力为您搜索聊天记录,请主人稍等片刻…………");
        content = fileName;
        if (e.getActionCommand().equals("Search")) {
            // 指定盘
            if (root == null || root.equals("")) {
                System.out.println("该路径不存在！");
                File file = new File("");
                listFile = file.listRoots();

            } else {
                if (content == null || content.length() == 0) {

                    File file = new File(root);
                    listFile = file.listFiles();
                    //启动线程
                    item = "search";
                    s = "";
                    fl = new FileListener(fileName, root);

                    t = new Thread(fl);
                    t.start();

                } else {

                    //   String[] list;
                    int number = rootlist.length;
		//list = new String[number];

                    //list[i] = rootlist[i].getAbsolutePath();
                    System.out.println("-------------------->" + rootlist[threadNum].getAbsolutePath());
                    File file = new File(rootlist[threadNum].getAbsolutePath());
                    listFile = file.listFiles();
                    //启动线程
                    item = "searchCount";
                    s = "";//每次线程启动前都要将s清空
                    fl = new FileListener(fileName, rootlist[threadNum].getAbsolutePath());
//
                    t = new Thread(fl);
                    t.start();

                }
            }

        }
    }

//		 if (e.getActionCommand().equals("Stop")) {
//			 s = "stop";//判断是否继续搜索
//			 item = "";
//			 if(t.isAlive()){
//				 t.interrupt();
//				 
//			 }
//		 }
    private int search(File[] file) {

        //每次使用之前都将count清0，否则会叠加
        int count = 0;
        if (file == null || file.length == 0) {
            return 0;
        }
        for (int i = 0; i < file.length; i++) {
            File filenew = file[i];
            if (filenew.isDirectory()) {
                File[] tempFile = filenew.listFiles();
                count += search(tempFile);
                this.count = count;
            }
            if (s.equals("stop")) {
                //s = "";清空s
                break;
            } else {
                if (filenew.isFile()) {
                    count++;
                    //ta.append("文件：" + filenew.getAbsolutePath() + "\n");
                    s = "stop";
                    //    success = 1;
                    //    LeftPanel.ReturnPath = analysisPath(filenew.getAbsolutePath());
                }
            }
        }

        return count;
    }

    private int searchContent(File[] file) {
        //System.out.println("new count=======================");
        int count = 0;
        if (file == null || file.length == 0) {
            return 0;
        }
        for (int i = 0; i < file.length; i++) {
            File filenew = file[i];
            // 包含字符
            if (filenew.isDirectory()) {
                File[] tempFile = filenew.listFiles();
                count += searchContent(tempFile);
                this.count = count;
            }

            if (filenew.isFile()) {
                String path = filenew.getAbsolutePath();
                //找到与内容相符的路径

                if (s.equals("stop")) {
                    //s = "";
                    break;
                } else {
                    System.out.println(path);      //没用
                    QQBackUp.msgLabel.setText(path);//没用
                    if (path.indexOf(content) != -1) {
                        count++;
                        //ta.append("文件：" + filenew.getAbsolutePath() + "\n");
                        s = "stop";

                        LeftPanel.ReturnPath = analysisPath(filenew.getAbsolutePath());
                        if (LeftPanel.ReturnPath != null) {
                            break;
                        }
                    }
                }
            }
        }

        return count;
    }

    //重写run方法
    @Override
    public void run() {
//		

        searchContent(listFile);
        System.out.println("searchContent函数结束");
        if (success == 0) {
            if (threadNum++ < rootlist.length) {
                System.out.println("-------------------->" + rootlist[threadNum].getAbsolutePath());
                File file = new File(rootlist[threadNum].getAbsolutePath());
                listFile = file.listFiles();
                //启动线程
                item = "searchCount";
                s = "";//每次线程启动前都要将s清空
                fl = new FileListener(fileName, rootlist[threadNum].getAbsolutePath());
//
                t = new Thread(fl);
                t.start();
            }
        }

    }

    /**
     * 解析搜索到的文件路径 得出目录文件绝对路径
     *
     * @param absolutePath
     * @return
     */
    private String analysisPath(String absolutePath) {
        File f = new File(absolutePath);

        try {
            while (!f.getName().equals("Tencent Files")) {
                f = f.getParentFile();
            }

        } catch (Exception e) {
            QQBackUp.msgLabel.setText("未能成功找到聊天记录");
            s = "";
            return null;
        }
        System.out.println("搜索到记录文件");
        QQBackUp.msgLabel.setText("已成功搜索到聊天记录");
        CheckBoxList.addQQList(f.getAbsolutePath());
        success = 1;
        return f.getAbsolutePath();
    }

}
