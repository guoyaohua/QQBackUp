package newpackage;

import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 * 文件搜索器
 * @author NetJava 
 *
 */
public class SearchFile extends JFrame {

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		SearchFile sf = new SearchFile();
		sf.init();
	}
	/**
	 * 显示界面的方法
	 */
	public void init(){
		this.setTitle("文件搜索器");
		this.setSize(750, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		this.setLayout(null);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.LIGHT_GRAY);
		panel1.setBounds(0, 0, 150, 600);
		this.add(panel1);
		JLabel lbl1 = new JLabel("搜索路径：");
		panel1.add(lbl1);
		
		
		JComboBox cb = new JComboBox(listDir());
		cb.setEditable(true);
		panel1.add(cb);
		JLabel lbl2 = new JLabel("搜索内容：");
		panel1.add(lbl2);
		JTextField txtContent = new JTextField(12);
		panel1.add(txtContent);
		JButton btnSearch = new JButton("搜索");
		panel1.add(btnSearch);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		panel2.setBounds(150, 0, 600, 600);
		this.add(panel2);
		
		JTextArea textpane = new JTextArea(31,52);
		JScrollPane scroll = new JScrollPane(textpane);
		panel2.add(scroll);
		
		this.setVisible(true);
		

		SearchFileChooser sfc = new SearchFileChooser(txtContent,textpane);
		cb.addActionListener(sfc);
		btnSearch.addActionListener(sfc);
		
	}

	/**
	 * 获取磁盘目录
	 */
	public String [] listDir(){
		//获取磁盘目录
		File [] list = File.listRoots();
		String [] array = new String[list.length+1];
		for(int i=0;i<list.length;i++){
			array[i] = list[i].getAbsolutePath();
		}
		array[list.length] = "选择目录";
		return array;
	}
	
	
	
}
