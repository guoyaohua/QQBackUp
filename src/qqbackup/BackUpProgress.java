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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static qqbackup.CheckBoxList.m_list;

/**
 *
 * @author John Kwok
 */
public class BackUpProgress extends JFrame {

    JProgressBar current;
    JLabel showProgress = new JLabel("正在为您努力的备份,请稍后…………          ");

    protected static long currentNum = 0;
    private long allNum;

    public BackUpProgress() {
        super("QQ聊天记录备份");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookAndFeel();
        SwingUtilities.updateComponentTreeUI(this);
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
                currentNum = getCurrentNum(new File(System.getProperty("user.dir") + "\\Back Up\\Tencent Files"));
                System.out.println(currentNum);
            } catch (InterruptedException ex) {
                Logger.getLogger(BackUpProgress.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        current.setValue((int) allNum);
        JOptionPane.showConfirmDialog(null, "恭喜您！备份记录完毕。", "QQ聊天记录备份", JOptionPane.OK_OPTION);
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
        ListModel model = m_list.getModel();

        for (int k = 0; k < model.getSize(); k++) {
            InstallData data = (InstallData) model.getElementAt(k);
            if (data.isSelected()) {
                allNum += GetFileSize.getlist(new File(LeftPanel.ReturnPath + "\\" + data.getName()));
            }
        }
        System.out.println("总共文件数" + allNum);
        return allNum;
    }

    static long getCurrentNum(File file) {
        return GetFileSize.getlist(file);
    }
}
