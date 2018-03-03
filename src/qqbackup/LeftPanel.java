package qqbackup;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import static qqbackup.CheckBoxList.m_list;
import static qqbackup.LeftPanel.ReturnPath;

/**
 * 左边按钮部分容器类
 *
 * @author John Kwok
 */
public class LeftPanel extends JPanel implements ActionListener {

    public static Image image = null;
    public static JLabel label;
    private final JButton recover;
    private final JButton backUp;
    private final JButton help;
    private final JButton about;
    private final JButton autoFind;
    public static String ReturnPath = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Tencent Files";

    public LeftPanel() {
        super();
        setSize(800, 300);
//设置布局

        GridLayout gr = new GridLayout(3, 2, 5, 8);
        setLayout(gr);
//声明按钮控件
        autoFind = new JButton("深度搜索聊天记录");
        /*
         此处为写死的头像图标显示  应该实现出动态改变头像图标功能
        
         */
//        try {
//            image = ImageIO.read(new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Tencent\\QQ\\Misc\\178904780"));   //读文件获取图片
//        } catch (IOException ex) {
//        }

        //此处为程序刚加载时显示的头像
        java.net.URL imgURL = LeftPanel.class.getResource("images/178904780.jpg");
        label = new JLabel(new ImageIcon(imgURL));
//label.setBorder(new TitledBorder(new EtchedBorder(),
        //               "QQface"));
        recover = new JButton("恢复聊天记录");
        backUp = new JButton("备份聊天记录");
        help = new JButton("帮助");
        about = new JButton("关于作者");
        //  exit.setPreferredSize(new Dimension(30,30));

        //绑定事件监听器
        autoFind.setActionCommand("Search");
        autoFind.addActionListener(new FileListener("Tencent Files", "c:\\"));
        recover.addActionListener(this);
        backUp.addActionListener(this);
        help.addActionListener(this);
        about.addActionListener(this);
//加入到panel
        add(autoFind);
        add(label);
        add(backUp);
        add(recover);
        add(help);
        add(about);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setVisible(true);
    }

    /**
     * ActionListener 的响应函数
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object ob = e.getSource();
        if (ob == backUp) {
            //备份聊天记录
            ListModel model = m_list.getModel();

            long i;
            int selectNum = 0;
            for (int k = 0; k < model.getSize(); k++) {
                InstallData data = (InstallData) model.getElementAt(k);
                if (data.isSelected()) {
                    selectNum++;

                }
            }
            File destDirFile = new File(System.getProperty("user.dir") + "\\Back Up\\Tencent Files");
            if (!destDirFile.exists()) {
                destDirFile.mkdirs();
            } 
            if (selectNum > 0) {
                i = BackUpProgress.getCurrentNum(new File(System.getProperty("user.dir") + "\\Back Up\\Tencent Files"));
                if (i != 0) {
                    int l = JOptionPane.showConfirmDialog(null, "您已备份过聊天记录，是否覆盖当前备份？", "QQ聊天记录备份", JOptionPane.YES_NO_OPTION);
                    if (l == JOptionPane.OK_OPTION) {
                        CopyFile.deleteFolder(System.getProperty("user.dir") + "\\Back Up\\Tencent Files");

                        new ProgressThread("copy").start();

                        for (int k = 0; k < model.getSize(); k++) {
                            InstallData data = (InstallData) model.getElementAt(k);
                            if (data.isSelected()) {

                                new CopyThread(ReturnPath + "\\" + data.getName()).start();
                                System.out.println(ReturnPath + "\\" + data.getName());
                            }
                        }

                    } else {
                        System.out.println("不备份");
                        return;
                    }

                } else {

                    int j = JOptionPane.showConfirmDialog(null, "您确认要备份记录到当前文件夹么？", "QQ聊天记录备份", JOptionPane.YES_NO_OPTION);
                    if (j == JOptionPane.OK_OPTION) {

                        new ProgressThread("copy").start();

                        for (int k = 0; k < model.getSize(); k++) {
                            InstallData data = (InstallData) model.getElementAt(k);
                            if (data.isSelected()) {

                                new CopyThread(ReturnPath + "\\" + data.getName()).start();
                                System.out.println(ReturnPath + "\\" + data.getName());
                            }
                        }

                    } else {
                        System.out.println("不备份");
                        return;
                    }

                }
            } else {
                JOptionPane.showConfirmDialog(null, "请您先选择要备份的QQ号", "QQ聊天记录备份", JOptionPane.OK_OPTION);
                return;
            }

        } else if (ob == recover) {
            //恢复聊天记录

            long i;
            File destDirFile = new File(ReturnPath);
            if (!destDirFile.exists()) {
                destDirFile.mkdirs();
            }

            i = BackUpProgress.getCurrentNum(new File(System.getProperty("user.dir") + "\\Back Up\\Tencent Files"));
            if (i == 0) {
                int l = JOptionPane.showConfirmDialog(null, "您还没有备份聊天记录，请先备份聊天记录", "QQ聊天记录备份", JOptionPane.OK_OPTION);

                System.out.println("不还原");
                return;

            } else {

                int j = JOptionPane.showConfirmDialog(null, "您确认要还原qq记录么？此操作会覆盖当前QQ记录", "QQ聊天记录备份", JOptionPane.YES_NO_OPTION);
                if (j == JOptionPane.OK_OPTION) {
                    CopyFile.deleteFolder(ReturnPath);

                    new ProgressThread("recover").start();
                    File f = new File(System.getProperty("user.dir") + "\\Back Up\\Tencent Files");
                    File[] ff = f.listFiles();
                    for (File tempF : ff) {
                        new RecoverThread(System.getProperty("user.dir") + "\\Back Up\\Tencent Files" + "\\" + tempF.getName()).start();
                    }
                } else {
                    System.out.println("不备份");
                    return;
                }

            }
        } else if (ob == help) {
            //程序帮助
            new Help();
        } else if (ob == about) {
            //退出
            new About();

        }

    }

}

/**
 * 备份记录进程
 *
 * @author John Kwok
 */
class CopyThread extends Thread {

    private String path;

    CopyThread(String selectFielerPath) {
        path = selectFielerPath;
    }

    @Override
    public void run() {
        try {
            CopyFile.copyFolder(path, System.getProperty("user.dir") + "\\Back Up\\Tencent Files");
        } catch (Exception ex) {
            Logger.getLogger(CopyThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/**
 * 恢复记录进程
 *
 * @author John Kwok
 */
class RecoverThread extends Thread {

    private String path;

    RecoverThread(String selectFielerPath) {
        path = selectFielerPath;
    }

    @Override
    public void run() {
        try {
            CopyFile.copyFolder(path, ReturnPath);                    //还原文件
        } catch (Exception ex) {
            Logger.getLogger(RecoverThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/**
 * 进度进程 用来辅助显示 进度条 实现方法 获取目的文件总数 和 源文件总数
 *
 * @author John Kwok
 */
class ProgressThread extends Thread {

    private String s;

    ProgressThread(String type) {
        s = type;
    }

    @Override
    public void run() {
        if (s.equals("copy")) {
            BackUpProgress bp = new BackUpProgress();
            bp.iterate();
        } else if (s.equals("recover")) {
            RecoverProgress rp = new RecoverProgress();
            rp.iterate();
        }
    }

}
