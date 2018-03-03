/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qqbackup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * 显示程序及作者信息
 *
 * @author John Kwok
 */
class About extends JFrame {

    public About() {
        super("关于作者");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLookAndFeel();  //设置皮肤
        SwingUtilities.updateComponentTreeUI(this);  //设置swing美化包
        setSize(580, 450);
        BorderLayout bl = new BorderLayout();

        setLayout(bl);
        //设置程序图标
        Toolkit tk = Toolkit.getDefaultToolkit();
        java.net.URL imgURL = LeftPanel.class.getResource("images/icon.png");
        Image imageIcon = tk.createImage(imgURL); /*image.gif是你的图标*/

        this.setIconImage(imageIcon);
        setLocationRelativeTo(null); //居中显示
        java.net.URL imgURL2 = LeftPanel.class.getResource("images/me.jpg");
       
        ImageIcon image = new ImageIcon(imgURL2);   //照片
        JLabel imageLabel = new JLabel(image);
        JPanel jp = new JPanel();
        JTextField name = new JTextField("作者：网G络Y黑H鹰");
        JTextField bolg = new JTextField("个人博客：http://blog.csdn.net/gyh111111");
        JTextField qq = new JTextField("QQ:178904780   欢迎互相交流");
        JTextField motto = new JTextField("Struggle For The Ideal !");
        GridLayout gr = new GridLayout(4, 1, 5, 8);
        name.setOpaque(false);
        bolg.setOpaque(false);
        qq.setOpaque(false);
        motto.setOpaque(false);
        jp.setLayout(gr);
        jp.add(name);
        jp.add(bolg);
        jp.add(qq);
        jp.add(motto);
        imageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        jp.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(imageLabel, BorderLayout.WEST);
        add(jp, BorderLayout.EAST);
        setResizable(false);   //锁定界面大小
        addComponentListener(new MCA());
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

    public static void main(String[] arg) {
        About about = new About();
    }

    class MCA extends ComponentAdapter {

        public void componentResized(ComponentEvent e) {
            Dimension aa = getSize();
            System.out.println("长：" + aa.width + "    " + "宽：" + aa.height);
        }
    }
}
