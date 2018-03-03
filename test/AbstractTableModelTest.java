//package com;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.Container;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class AbstractTableModelTest extends JFrame {

	private JTable table = null;
	private JScrollPane jsp = null;
	private JComboBox cob = null;
	private JCheckBox ckb = null;
	private JTextField txt = null;

	public AbstractTableModelTest() {
		this.setTitle("JTable的ListSelectionModeld的监听事件");
		this.setSize(400, 360);

		// 获取窗体的内容面板
		Container container = this.getContentPane();
		MyAbstractTableModel1 myModel = new MyAbstractTableModel1();

		// JTable
		table = new JTable(myModel);
		// 获得表格的表格列类
		TableColumn tc1 = table.getColumnModel().getColumn(2);
		TableColumn tc2 = table.getColumnModel().getColumn(4);
		TableColumn tc3 = table.getColumnModel().getColumn(5);

		// 实例化JCheckBox
		ckb = new JCheckBox();
		tc1.setCellEditor(new DefaultCellEditor(ckb));

		// 实例化JComboBox
		cob = new JComboBox();
		cob.addItem("HN");
		cob.addItem("HB");
		cob.addItem("BJ");
		tc2.setCellEditor(new DefaultCellEditor(cob));

		// 实例化JTextField
		txt = new JTextField("");
		txt.setSize(100, 26);
		tc3.setCellEditor(new DefaultCellEditor(txt));

		// JScrollPane
		jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		container.add(jsp);

		// 获得自定义的抽象表格模型

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		new AbstractTableModelTest();
	}
}

@SuppressWarnings("serial")
class MyAbstractTableModel1 extends AbstractTableModel {
	// 定义表头数据
	String[] head = { "ID", "NAME", "SEX(boy)", "AGE", "ADDRESS", "DEMO" };
	// 创建类型数组
	// Class[]
	// typeArray={Object.class,Object.class,Boolean.class,int.class,Object.class,Object.class};

	// 定义表的内容数据
	Object[] data1 = { "200913420125", "SUMMER", new Boolean(true),
			new Integer(20), "1", "NULL" };
	Object[] data2 = { "200913420124", "WULEI", new Boolean(true),
			new Integer(20), "2", "NULL" };
	Object[] data3 = { "200913420125", "BOOK", new Boolean(false),
			new Integer(20), "3", "NULL" };
	Object[] data4 = { "200913420125", "CUP", new Boolean(true),
			new Integer(20), "4", "NULL" };
	Object[] data5 = { "200913420125", "MOUSE", new Boolean(true),
			new Integer(20), "5", "NULL" };
	// 定义表格每一列的数据类型

	Class[] typeArray = { Object.class, Object.class, Boolean.class,
			Integer.class, JComboBox.class, Object.class };

	Object[][] data = { data1, data2, data3, data4, data5 };

	// 获得表格的列数
	public int getColumnCount() {
		return head.length;
	}

	// 获得表格的行数
	public int getRowCount() {
		return data.length;
	}

	// 获得表格的列名称
	@Override
	public String getColumnName(int column) {
		return head[column];
	}

	// 获得表格的单元格的数据
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	// 使表格具有可编辑性
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	// 替换单元格的值
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = aValue;
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	// 实现了如果是boolean自动转成JCheckbox
	/*
	 * 需要自己的celleditor这么麻烦吧。jtable自动支持Jcheckbox，
	 * 只要覆盖tablemodel的getColumnClass返回一个boolean的class， jtable会自动画一个Jcheckbox给你，
	 * 你的value是true还是false直接读table里那个cell的值就可以
	 */
	public Class getColumnClass(int columnIndex) {
		return typeArray[columnIndex];// 返回每一列的数据类型
	}
}
