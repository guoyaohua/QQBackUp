/*
* Copyright 2005 MH-Software-Entwicklung. All rights reserved.
* Use is subject to license terms.
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TT extends JFrame {

    public TT() {
        super("Minimal-Frame-Application");
        
        // setup menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        JMenuItem menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        // setup widgets
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        JScrollPane westPanel = new JScrollPane(new JTree());
        JEditorPane editor = new JEditorPane("text/plain", "Hello World");
        JScrollPane eastPanel = new JScrollPane(editor);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, westPanel,eastPanel);
        splitPane.setDividerLocation(148);
        contentPanel.add(splitPane, BorderLayout.CENTER);
        setContentPane(contentPanel);
        
        // add listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // show application
        setLocation(32, 32);
        setSize(400, 300);
        show();
    } // end CTor MinFrame
    /*
    com.jtattoo.plaf.noire.NoireLookAndFeel  柔和黑
com.jtattoo.plaf.smart.SmartLookAndFeel 木质感+xp风格
com.jtattoo.plaf.mint.MintLookAndFeel  椭圆按钮+黄色按钮背景
com.jtattoo.plaf.mcwin.McWinLookAndFeel 椭圆按钮+绿色按钮背景
com.jtattoo.plaf.luna.LunaLookAndFeel  纯XP风格
com.jtattoo.plaf.hifi.HiFiLookAndFeel  黑色风格
com.jtattoo.plaf.fast.FastLookAndFeel  普通swing风格+蓝色边框
com.jtattoo.plaf.bernstein.BernsteinLookAndFeel  黄色风格
com.jtattoo.plaf.aluminium.AluminiumLookAndFeel 椭圆按钮+翠绿色按钮背景+金属质感
com.jtattoo.plaf.aero.AeroLookAndFeel xp清新风格
com.jtattoo.plafacryl.AcrylLookAndFeel 布质感+swing纯风格
com.jtattoo.plaf.graphite.GraphiteLookAndFeel
    */
    public static void main(String[] args) {
        try {
            ////////////////////////---------------------------------- select Look and Feel(下面就是要改变的地方了)
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");   //几COM包就可以换不同风格
            ////////////////////////---------------------------------- start application
            new TT();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    } // end main
    
} // end class MinFrame