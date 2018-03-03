package newpackage;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 动作事件处理类对象
 * 
 * @author NetJava
 * 
 */
public class SearchFileChooser implements java.awt.event.ActionListener {

	private String path;
	private JTextField txt;
	private JTextArea area;
	
	public SearchFileChooser(JTextField txt,JTextArea area){
		this.txt = txt;
		this.area = area;
	}
	
	// 事件处理类
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// 判断事件源是否是下拉列表框的实例
		if (e.getSource() instanceof javax.swing.JComboBox) {
			javax.swing.JComboBox cb = (javax.swing.JComboBox) e.getSource();
			path = cb.getSelectedItem().toString();
			if (path.equals("选择目录")) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int flag = jfc.showOpenDialog(null);
				if(flag==JFileChooser.APPROVE_OPTION){
		             //获得该文件    
		            java.io.File f =jfc.getSelectedFile();    
		            path =f.getPath();
		            //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~>"+path);
		         }    

			}
			//System.out.println(value);
		} else {
			String content = txt.getText();
			Search s = new Search();
			s.searchFile(path, content, area);
		}

	}

}
