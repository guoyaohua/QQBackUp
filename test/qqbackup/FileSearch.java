package qqbackup;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FileSearch {
    public static String returnPath = "";
    public static void setReturnPath(String returnPath){
        FileSearch.returnPath=returnPath;
    }

	public static void main(String[] args) {
		FileSearch fs = new FileSearch();
		fs.listRoots();
		fs.init();
	}

	public void init() {
		JFrame jf = new JFrame("文件搜索器");
		jf.setSize(600, 400);
		jf.setDefaultCloseOperation(3);
		//jf.setResizable(false);
		jf.setLocationRelativeTo(null);
		jf.setLayout(null);

		// 面板
		JPanel jp = new JPanel();
		jp.setBounds(0, 0, 120, 400);
		jp.setBackground(Color.white);
		jp.setLayout(new GridLayout(12, 1));
		jf.add(jp);

		//JLabel lb = new JLabel("选择搜索范围");
		//jp.add(lb);
		//JComboBox cb = new JComboBox(list);
		//cb.addItem("选择目录");
		//cb.setSelectedItem(cb);
		//cb.setEditable(true);
		//jp.add(cb);
		//JLabel lb1 = new JLabel("按内容搜索");
		//jp.add(lb1);
		//JTextField tf = new JTextField(20);
		//jp.add(tf);
//
		JButton btn = new JButton("搜索");
		btn.setActionCommand("Search");
		jp.add(btn);
		JButton btn1 = new JButton("停止");
		btn1.setActionCommand("Stop");
		jp.add(btn1);

		//JTextArea ta = new JTextArea();
		//ta.setVisible(true);
		//ta.setEditable(false);
		//ta.setBackground(Color.white);
		//ta.setLineWrap(true);
		//JScrollPane pane = new JScrollPane(ta);
		//pane.setBounds(120, 0, 480, 400);
		//jf.add(pane);
String fileName = new String("178904780");
String root = new String("C:\\");
		FileListener fl = new FileListener(fileName,root);

		//cb.addActionListener(fl);
		btn.addActionListener(fl);
		btn1.addActionListener(fl);

		jf.setVisible(true);

	}

	public void listRoots() {
		File file = new File("");
		File[] rootlist = file.listRoots();
		int number = rootlist.length;
		list = new String[number];
		for (int i = 0; i < rootlist.length; i++) {
			this.list[i] = rootlist[i].getAbsolutePath();
		}
		System.out.println("总共有" + number + "个盘");
	}

	private String[] list;
        

}
