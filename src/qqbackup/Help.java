/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qqbackup;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 显示软件帮助信息
 *
 * @author John Kwok
 */
class Help extends JFrame {

    private JTextArea ta = new JTextArea("尊敬的用户：您好！\n"
            + "  首先非常感谢您对“QQ记录备份助手”的支持，希望本程序能\n"
            + "给您带来些便捷。首次编写程序，有许多不足之处，希望大家能\n"
            + "互相交流。您的支持是对我最大的鼓励。\n"
            + "  下面由我为您介绍下本软件的功能：\n"
            + "  本软件启动时会自动导入本地QQ记录数据，并将QQ号码显示在\n"
            + "列表中，并且标明了数据的大小。（注：如果本地聊天记录数据\n"
            + "过于庞大，当首次开启程序时会延迟数秒，均属正常现象）。\n"
            + "  如果当开启程序时没有自动导入QQ记录数据到列表中，那就来\n"
            + "试试“深度搜索聊天记录”吧。当您点击右侧的“深度搜索聊天\n"
            + "记录”时，程序会自动全盘搜索QQ聊天记录文件，当检索到记录\n"
            + "文件时，会自动将检索到的QQ号码导入右侧的列表中。\n"
            + "  当您成功导入QQ信息到右侧列表后，请勾选您要备份记录的QQ\n"
            + "号，当勾选完毕后，点击“备份聊天记录”按钮，程序会自动将\n"
            + "您所选中的QQ记录备份起来（包括您聊天时接受的文件，表情图\n"
            + "片，视频截图，语音文件……）。\n"
            + "  备份成功后，可点击“恢 复 聊 天 记录”进行QQ记录的还原\n"
            + "（注：请确保此时QQ关闭，否则无法成功进行），这时会自动还\n"
            + "原已备份的聊天记录到本地。\n"
            + "\n"
            + "                                                                              By：网G络Y黑H鹰");

    public Help() {
        super("使用帮助");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLookAndFeel();  //设置皮肤
        SwingUtilities.updateComponentTreeUI(this);  //设置swing美化包
        setSize(350, 405);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null); //居中显示 
        ta.setBackground(null);
        add(ta);
        //设置程序图标
        Toolkit tk = Toolkit.getDefaultToolkit();
        java.net.URL imgURL = LeftPanel.class.getResource("images/icon.png");
        Image image = tk.createImage(imgURL); /*image.gif是你的图标*/
   // Image image = tk.createImage("icon.png"); /*image.gif是你的图标*/

        this.setIconImage(image);
        setResizable(false);   //锁定界面大小
        setVisible(true);
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

}
