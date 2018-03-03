package qqbackup;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import static java.lang.System.out;
import java.util.*;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.*;

public class CheckBoxList extends JPanel {

    public static long total = 0;
/**
 * 把置顶路径中的文件、文件夹显示在列表里   并显示其大小
 * @param absolutePath 要导入的目录绝对路径
 */
    static void addQQList(String absolutePath) {
        options.clear();
        File f = new File(absolutePath);
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {                //检测这个路径是否为一个目录 （检测是否为文件夹）
                out.println(fs.getName() + " [目录]<br />");
                if (isNumeric(fs.getName())) {
                    GetFileSize g = new GetFileSize();
                    String facePath = absolutePath + "\\" + fs.getName();
                    options.add(new InstallData(fs.getName(), g.getSize(facePath)));
                }
            } else {
                out.println(fs.getName() + "<br />");
            }
        }

        m_list.setListData(options);
     m_list.repaint();
    }

    protected static JList m_list;
    protected JLabel m_total;
    protected static Vector options;

    public CheckBoxList() {
        super();
        setSize(360, 340);
        //  getContentPane().setLayout(new FlowLayout());
        options = new Vector();
//        InstallData[] options = {
//            new InstallData("Program   executable ", 118),
//            new InstallData("Help   files ", 52),
//            new InstallData("Tools   and   converters ", 83),
//            new InstallData("Source   code ", 133)
//        };
        //////////////////////////////////获取文件夹中所有文件名称
        String path = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Tencent Files"; //路径
        File f = new File(path);
        if (!f.exists()) {
            out.println(path + " not exists");  //若未找到文件  响应事件
            options.add(new InstallData("未发现QQ记录", 0));
            options.add(new InstallData("请点深度搜索", 0));
            ///////////////////////////////////////////////////////////////////////////////        
            m_list = new JList(options);
            m_list.setVisibleRowCount(10);
            m_list.setFixedCellWidth(180);
            CheckListCellRenderer renderer = new CheckListCellRenderer();
            m_list.setCellRenderer(renderer);
            m_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            CheckListener lst = new CheckListener(this);
            m_list.addMouseListener(lst);
            m_list.addKeyListener(lst);

            JScrollPane ps = new JScrollPane();
            ps.getViewport().add(m_list);

            m_total = new JLabel("Space   required:   0K ");

            setLayout(new BorderLayout());
            add(ps, BorderLayout.CENTER);
            add(m_total, BorderLayout.SOUTH);
            setBorder(new TitledBorder(new EtchedBorder(),
                    "请选择您要备份的QQ: "));

            setVisible(true);

            ///////////////////////////////////////////////////////      
            return;
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {                //检测这个路径是否为一个目录 （检测是否为文件夹）
                out.println(fs.getName() + " [目录]<br />");
                if (isNumeric(fs.getName())) {
                    GetFileSize g = new GetFileSize();
                    String facePath = path + "\\" + fs.getName();
                    options.add(new InstallData(fs.getName(), g.getSize(facePath)));
                }
            } else {
                out.println(fs.getName() + "<br />");
            }
        }

        m_list = new JList(options);
        m_list.setVisibleRowCount(10);
        CheckListCellRenderer renderer = new CheckListCellRenderer();
        m_list.setCellRenderer(renderer);
        m_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        CheckListener lst = new CheckListener(this);
        m_list.addMouseListener(lst);
        m_list.addKeyListener(lst);

        JScrollPane ps = new JScrollPane();
        ps.getViewport().add(m_list);

        m_total = new JLabel("Space   required:   0K ");

        //    JPanel p = new JPanel();
        setLayout(new BorderLayout());
        add(ps, BorderLayout.CENTER);
        add(m_total, BorderLayout.SOUTH);
        setBorder(new TitledBorder(new EtchedBorder(),
                "请选择您要备份的QQ: "));
        //  getContentPane().add(p);

//        WindowListener wndCloser = new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        };
//        addWindowListener(wndCloser);
        setVisible(true);

        recalcTotal();
    }

    public void recalcTotal() {
        ListModel model = m_list.getModel();
        total = 0;
        for (int k = 0; k < model.getSize(); k++) {
            InstallData data = (InstallData) model.getElementAt(k);
            if (data.isSelected()) {
                total += data.getSize();
            }
        }
        m_total.setText("Space   required:   " + GetFileSize.FormetFileSize(total));
    }

//    public static void main(String argv[]) {
//        new CheckBoxList();
//
//    }
    /**
     * *正则表达式 判断字符串是否为数字
     *
     *
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}

class CheckListCellRenderer extends JCheckBox implements ListCellRenderer {

    protected static EmptyBorder m_noFocusBorder
            = new EmptyBorder(1, 1, 1, 1);

    public CheckListCellRenderer() {
        super();
        setOpaque(true);
        setBorder(m_noFocusBorder);
    }

    @Override
    public Component getListCellRendererComponent(JList list,
            Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());

        setBackground(isSelected ? list.getSelectionBackground()
                : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground()
                : list.getForeground());

        InstallData data = (InstallData) value;
        setSelected(data.isSelected());

        setFont(list.getFont());
        setBorder((cellHasFocus)
                ? UIManager.getBorder("List.focusCellHighlightBorder ")
                : m_noFocusBorder);

        return this;
    }
}

class CheckListener
        implements MouseListener, KeyListener {

    protected CheckBoxList m_parent;
    protected JList m_list;

    public CheckListener(CheckBoxList parent) {
        m_parent = parent;
        m_list = parent.m_list;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getX() < 200) {   //此处设置的为点击的敏感区间
            doCheck();
            /////////////////////////////////////   
            int index = m_list.getSelectedIndex();
            if (index < 0) {
                return;
            }
            InstallData data = (InstallData) m_list.getModel().
                    getElementAt(index);
            String name = data.getName();
            //////////////////////////////////////  
            QQBackUp.changeFaceIcon("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Tencent\\QQ\\Misc\\" + name);
//此处还需获取计算机用户名  否则无法正常运行
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == ' ') {         //按空格也可以选择

            doCheck();
            /////////////////////////////////////   
            int index = m_list.getSelectedIndex();
            if (index < 0) {
                return;
            }
            InstallData data = (InstallData) m_list.getModel().
                    getElementAt(index);
            String name = data.getName();
            //////////////////////////////////////  
            QQBackUp.changeFaceIcon("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Tencent\\QQ\\Misc\\" + name);
//此处还需获取计算机用户名  否则无法正常运行
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    protected void doCheck() {
        int index = m_list.getSelectedIndex();
        if (index < 0) {
            return;
        }
        InstallData data = (InstallData) m_list.getModel().
                getElementAt(index);
        data.invertSelected();
        m_list.repaint();
        m_parent.recalcTotal();
    }
}

class InstallData {

    protected String m_name;
    protected long m_size;
    protected boolean m_selected;

    public InstallData(String name, long size) {
        m_name = name;
        m_size = size;
        m_selected = false;
    }

    public String getName() {
        return m_name;
    }

    public long getSize() {
        return m_size;
    }

    public void setSelected(boolean selected) {
        m_selected = selected;
    }

    public void invertSelected() {
        m_selected = !m_selected;
    }

    public boolean isSelected() {
        return m_selected;
    }

    public String toString() {
        return m_name + "   ( " + GetFileSize.FormetFileSize(m_size) + "    ) ";
    }
}
