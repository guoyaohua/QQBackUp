package newpackage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JTextArea;

/**
 * 文件查找
 * 
 * @author Administrator
 * 
 */
public class Search {

	public void searchFile(String path, String content, JTextArea area) {
		// 实例化一个文件对象
		File file = new File(path);
		// 获取目录下的文件
		File[] list = file.listFiles();
		if (null == list || list.length == 0) {
			return;
		}
		for (int i = 0; i < list.length; i++) {
			File temp = list[i];
			if (temp.isDirectory()) {
				path = temp.getAbsolutePath();
				for (int j = 0; j < path.length(); j++) {
					if (path.indexOf(content) != -1) {
						area.append(temp.getAbsolutePath() + "\n");
					}
				}
				searchFile(path, content, area);
			}
			if (temp.isFile()) {
				boolean state = false;
				// 获取路径
				String str = temp.getAbsolutePath();
				for (int j = 0; j < str.length(); j++) {
					if (str.indexOf(content) != -1) {
						area.append(temp.getAbsolutePath() + "\n");
						state = true;
						break;
					}
				}
				if (!state) {
					try {
						InputStream is = new FileInputStream(temp);
						BufferedInputStream bis = new BufferedInputStream(is);
						byte[] array = new byte[bis.available()];
						bis.read(array);
						String value = new String(array);
						//System.out.println("=======>"+value);
						for (int j = 0; j < value.length(); j++) {
							if (value.indexOf(content) != -1) {
								area.append(temp.getAbsolutePath() + "\n");
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
