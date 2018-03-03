
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * 加载显示图象,需要JDK1.5或以上 显示头像图片
 */
public class showtu extends JFrame {

    public showtu(String bmpFile) {
        Image image = null;
        try {
            image = ImageIO.read(new File(bmpFile));   //读文件获取图片
        } catch (IOException ex) {
        }
        JLabel label = new JLabel(new ImageIcon(image));
        add(label);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        final String fileName = "C:\\Users\\John Kwok\\AppData\\Roaming\\Tencent\\QQ\\Misc\\178904780"; //换成你要显示的图片 
        new showtu(fileName);
//        SwingUtilities.invokeLater(new Runnable(){ 
//            public void run(){ 
//                new showtu(fileName).setVisible(true); 
//            } 
//        }); 
    }
}
