//package Test1;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.IconView;

public class JListCheckBoxDemo extends JFrame implements ActionListener {

    JButton jb1, jb2, jb3;
    JPanel jp1;
    JList list;
    CheckListItem[] cli;
    DefaultListModel model;
    private int first;
    private int sec;

    public static void main(String args[]) {
        new JListCheckBoxDemo();
    }

    public JListCheckBoxDemo() {
        // Create a list containing CheckListItem's
        cli = new CheckListItem[]{
            new CheckListItem("1"), new CheckListItem("2"),
            new CheckListItem("3"), new CheckListItem("4"),
            new CheckListItem("5"), new CheckListItem("6"),
            new CheckListItem("7"), new CheckListItem("8"),
            new CheckListItem("9")};

        model = new DefaultListModel();
        //循环把数组的中的项添加到model中
        for (int i = 0; i < cli.length; i++) {
            model.add(i, cli[i]);
        }

        list = new JList(model);
        // Use a CheckListRenderer (see below)
        // to renderer list cells
        list.setCellRenderer(new CheckListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectionBackground(new Color(177, 232, 58));//186,212,239,177,232,58
        list.setSelectionForeground(Color.black);
        // Add a mouse listener to handle changing selection
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                // Get index of item clicked
                //获得用户点击项的索引
                int index = list.locationToIndex(event.getPoint());
                CheckListItem item = (CheckListItem) list.getModel()
                        .getElementAt(index);
                // 设置列表中项的选择状态
                item.setSelected(!item.isSelected());
                // 重新绘制列表中项
                list.repaint(list.getCellBounds(index, index));
            }

            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub                 
                first = list.locationToIndex(e.getPoint());
            }

            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub                 
                sec = list.locationToIndex(e.getPoint());
                if (sec != -1) {
                    if (first != sec) {
                        String s1 = model.getElementAt(first).toString();
                        String s2 = model.getElementAt(sec).toString();
                        model.setElementAt(cli[first], sec);
                        model.setElementAt(cli[sec], first);
                        model.copyInto(cli);
                        list.setModel(model);
                    }
                }
            }
        });
        this.add(new JScrollPane(list), "Center");
        jb1 = new JButton("上移");
        jb2 = new JButton("下移");
        jb3 = new JButton("取值");
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jp1 = new JPanel();
        jp1.setLayout(new BoxLayout(jp1, BoxLayout.Y_AXIS));
        jp1.add(jb1);
        jp1.add(jb2);
        jp1.add(jb3);
        this.add(jp1, "East");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);
        this.setBounds(400, 300, 150, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == jb1) {
			//上移
            //先获得选择项的索引
            int index = list.getSelectedIndex();
            if (index != -1) {
                if (index > 0) {
                    System.out.println(model.getSize() + " index=" + index);
                    //再把当前选择项的值赋给上一个索引处的值
                    model.set(index - 1, cli[index]);
                    //然后把上一个索引处的值赋个当前索引处
                    model.set(index, cli[index - 1]);
                    //重新构造列表模型
                    list.setModel(model);
                    //把改变后的模型赋给数组cli,该步必不可少
                    model.copyInto(cli);
                    //最后设置选择项为选中状态
                    list.setSelectedIndex(index - 1);
                } else {
                    JOptionPane.showMessageDialog(null, "已移动到最顶部了", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "请先选择要移动的项", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (e.getSource() == jb2) {
			//下移
            //先获得选择项的索引
            int index = list.getSelectedIndex();
            if (index != -1) {
                if (index < model.getSize() - 1) {
                    System.out.println(model.getSize() + " index=" + index);
                    //再把当前选择项的值赋给下一个索引处的值
                    model.set(index + 1, cli[index]);
                    //然后把下一个索引处的值赋个当前索引处
                    model.set(index, cli[index + 1]);
                    //重新构造列表模型
                    list.setModel(model);
                    //把改变后的模型赋给数组cli,该步必不可少
                    model.copyInto(cli);
                    //最后设置选择项为选中状态
                    list.setSelectedIndex(index + 1);
                } else {
                    JOptionPane.showMessageDialog(null, "已移动到最底部了", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "请先选择要移动的项", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (e.getSource() == jb3) {
            //获取选中的值
            CheckListItem item;
            for (int i = 0; i < model.getSize(); i++) {
                item = (CheckListItem) list.getModel().getElementAt(i);
                if (item.isSelected()) {
                    System.out.println(item);
                }
            }// for end
        }//if end 
    }// action end

}

// Represents items in the list that can be selected
class CheckListItem {

    private String label;
    private boolean isSelected = false;
    private ImageIcon icon;

    public CheckListItem(String label) {
        this.label = label;
        icon=new ImageIcon("map.png");
        
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String toString() {
        return label;
    }
}
// Handles rendering cells in the list using a check box

class CheckListRenderer extends JCheckBox implements ListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean hasFocus) {

        setEnabled(list.isEnabled());
        setSelected(((CheckListItem) value).isSelected());
        setFont(list.getFont());
        if (isSelected) {
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());
        } else {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }
        setText(value.toString());
        return this;

    }

}
