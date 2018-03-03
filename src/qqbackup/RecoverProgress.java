/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qqbackup;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static qqbackup.CheckBoxList.m_list;
import static qqbackup.LeftPanel.ReturnPath;

/**
 *
 * @author John Kwok
 */
public class RecoverProgress extends JFrame {

    JProgressBar current;
    JLabel showProgress = new JLabel("正在为您努力的还原,请稍后…………          ");

    protected static long currentNum = 0;
    private long allNum;

    public RecoverProgress() {
        super("QQ聊天记录备份");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookAndFeel();  //设置皮肤
        SwingUtilities.updateComponentTreeUI(this);  //设置swing美化包
        setSize(230, 130);
        allNum = getAllNum();
        setLayout(new FlowLayout());
        current = new JProgressBar(0, (int) allNum);
        current.setValue(0);
        current.setStringPainted(true);
        //设置程序图标
        Toolkit tk = Toolkit.getDefaultToolkit();
        java.net.URL imgURL = LeftPanel.class.getResource("images/icon.png");
        Image image = tk.createImage(imgURL); /*image.gif是你的图标*/
        this.setIconImage(image);
        //pack();
        add(current);
        add(showProgress);
        setLocationRelativeTo(null); //居中显示
        setVisible(true);

    }

    public void iterate() {
        currentNum = 0;

        while (currentNum < allNum) {
            try {
                current.setValue((int) currentNum);
                Thread.sleep(1000);
                currentNum = getCurrentNum(new File(ReturnPath));
                System.out.println(currentNum);
            } catch (InterruptedException ex) {
                Logger.getLogger(BackUpProgress.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        current.setValue((int) allNum);
        JOptionPane.showConfirmDialog(null, "恭喜您！QQ记录还原完毕。", "QQ聊天记录备份", JOptionPane.OK_OPTION);
        setVisible(false);
    }

    private void setLookAndFeel() {
        try {
            ////////////////////////---------------------------------- select Look and Feel(下面就是要改变的地方了)
            setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");   //几COM包就可以换不同风格
            ////////////////////////---------------------------------- start application
            // new TT();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private long getAllNum() {

        allNum = GetFileSize.getlist(new File(System.getProperty("user.dir") + "\\Back Up\\Tencent Files"));

        System.out.println("总共文件数" + allNum);
        return allNum;
    }

    static long getCurrentNum(File file) {
        return GetFileSize.getlist(file);
    }
}
