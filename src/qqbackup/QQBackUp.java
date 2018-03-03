package qqbackup;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import static qqbackup.LeftPanel.image;

/**
 *
 * @author John Kwok
 */
public class QQBackUp extends JFrame {

    private ImageIcon titleImage;
    private final JLabel titleImageLabel;
    private static LeftPanel leftPanel;
    public static JLabel msgLabel = new JLabel("欢迎使用");

    public QQBackUp() {

        super("QQ记录备份助手  V1.0");

        setSize(460, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //设置程序图标
        Toolkit tk = Toolkit.getDefaultToolkit();
        java.net.URL imgURL = LeftPanel.class.getResource("images/icon.png");
        Image image = tk.createImage(imgURL); /*image.gif是你的图标*/
        this.setIconImage(image);
        setResizable(false);   //锁定界面大小
        //总布局声明    

        BorderLayout bl = new BorderLayout();

        setLayout(bl);
java.net.URL imgURL2 = LeftPanel.class.getResource("images/1_1.jpg");
        titleImage = new ImageIcon(imgURL2);   //此处放置程序logo   上方的图片

        titleImageLabel = new JLabel(titleImage);

        leftPanel = new LeftPanel();
        CheckBoxList list = new CheckBoxList(); ///////////////////

        add(leftPanel, BorderLayout.WEST);
        add(list, BorderLayout.EAST);
        add(titleImageLabel, BorderLayout.NORTH);
        add(msgLabel, BorderLayout.SOUTH);
        setLocationRelativeTo(null); //居中显示
        setVisible(true);
        
        
         //     System.out.println("高："+titleImage.getIconHeight()+"宽:"+titleImage.getIconWidth());  


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);

//设置美化界面
        try {
            /*
             com.jtattoo.plaf.noire.NoireLookAndFeel  柔和黑
             com.jtattoo.plaf.smart.SmartLookAndFeel 木质感+xp风格
             com.jtattoo.plaf.mint.MintLookAndFeel  椭圆按钮+黄色按钮背景  /////////
             com.jtattoo.plaf.mcwin.McWinLookAndFeel 椭圆按钮+绿色按钮背景  // hao 
             com.jtattoo.plaf.luna.LunaLookAndFeel  纯XP风格
             com.jtattoo.plaf.hifi.HiFiLookAndFeel  黑色风格   //////////
             com.jtattoo.plaf.fast.FastLookAndFeel  普通swing风格+蓝色边框
             com.jtattoo.plaf.bernstein.BernsteinLookAndFeel  黄色风格 //非常好
             com.jtattoo.plaf.aluminium.AluminiumLookAndFeel 椭圆按钮+翠绿色按钮背景+金属质感
             com.jtattoo.plaf.aero.AeroLookAndFeel xp清新风格
             com.jtattoo.plafacryl.AcrylLookAndFeel 布质感+swing纯风格
             com.jtattoo.plaf.graphite.GraphiteLookAndFeel
             */
            ////////////////////////---------------------------------- select Look and Feel(下面就是要改变的地方了)
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");   //几COM包就可以换不同风格
            ////////////////////////---------------------------------- start application
            QQBackUp mainFrame = new QQBackUp();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void changeFaceIcon(String path) {
        File f = new File(path);
        if (f.exists()) {
            try {
                LeftPanel.image = ImageIO.read(f);  //读文件获取图片
                LeftPanel.label.setIcon(new ImageIcon(image));// 重新设置faceicon图片
            } catch (IOException ex) {
                Logger.getLogger(QQBackUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("获取头像文件失败");
            java.net.URL imgURL = LeftPanel.class.getResource("images/178904780.jpg");
            LeftPanel.label.setIcon(new ImageIcon(imgURL));  //此处为没有正确加载本地头像时  加载备用头像

        }

    }
}

//        try {
//            File file;
//            try {
//                file = new File(path);
//                    
//
//                
//            }catch (Exception ex) {
//                System.out.println("获取文件失败");
//                LeftPanel.label.setIcon(new ImageIcon("map.png"));  //此处为没有正确加载本地头像时  加载备用头像
//                return;
//            }
//            LeftPanel.image = ImageIO.read(file);
//            LeftPanel.label.setIcon(new ImageIcon(image));// 重新设置faceicon图片
//        } catch (IOException ex) {
//            Logger.getLogger(QQBackUp.class.getName()).log(Level.SEVERE, null, ex);
