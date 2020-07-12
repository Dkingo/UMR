package PurchaseSale;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.Impl.BaseDaoImpl;
import framework.BaseTableModule;
import framework.Tools;


public class StockOutput extends JPanel implements ActionListener,MouseListener,DocumentListener{
	
	public JPanel backgroundPanel;
	JPanel topPanel;
	JPanel toolPanel;
	JPanel searchPanel;
	JPanel tablePanel;
	JComboBox select_category, select_warehouse;
	JTextField input_name, input_exchange;
	JLabel label_name, label_category, label_warehouse, tool_add, tool_modify, tool_delete, label_exchange;
	
	BaseTableModule module;
	JScrollPane sroll;
	JTable table;
	
	public StockOutput() {

		backgroundPanel = new JPanel(new BorderLayout());

		initTopPanel();
		
	}

	// 初始化顶部面板
	public void initTopPanel() {

		topPanel = new JPanel(new BorderLayout());

		initSearchPanel();
		initTabelPanel();

		backgroundPanel.add(topPanel, "North");
		backgroundPanel.add(tablePanel, "Center");

	}

	// 初始化搜素条件面板
	public void initSearchPanel() {

		searchPanel = new JPanel();

		// 商品模糊名称输入框
		input_name = new JTextField(10);
		input_exchange = new JTextField(8);
		input_name.getDocument().addDocumentListener(this);
		input_exchange.getDocument().addDocumentListener(this);

		// 标签
		label_name = new JLabel("商品名称");
		label_exchange = new JLabel("交易号");

		searchPanel.add(label_exchange);
		searchPanel.add(input_exchange);
		searchPanel.add(label_name);
		searchPanel.add(input_name);
		topPanel.add(searchPanel, "East");
		
	}
	
	//更新面板
	public void refreshTablePanel(){
		backgroundPanel.remove(tablePanel);
		
		String columnName[] = {"商品编号","商品流水号", "交易号","商品名称","商品价格","出库数量","出库日期"};
		String name = input_name.getText();
		String exchange = input_exchange.getText();
		BaseDaoImpl base = new BaseDaoImpl();
		Vector<Vector> vector = new Vector<Vector>();
		try {
			String table_name = "outofstock";
			vector = base.selectByNaEx(name, exchange, table_name, 7);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		module = new BaseTableModule(columnName, vector);
		table = new JTable(module);
		Tools.setTableStyle(table);
		
		
		sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		tablePanel = new JPanel(new BorderLayout());
		tablePanel.setOpaque(false);

		tablePanel.add(sroll);
		backgroundPanel.add(tablePanel);
		backgroundPanel.validate();
	}
	
	//初始化面板
	public void initTabelPanel() {
		
		String columnName[] = {"商品编号","商品流水号","交易号","商品名称","商品价格","出库数量","出库日期"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			String sql = "select * from outofstock";
			List<Object[]> list = base.select(sql, 7, null);
			if (!list.isEmpty()) {
				for (Object[] object : list) {
					Vector temp = new Vector<String>();
					for (int i = 0; i < object.length; i++) {
						temp.add(object[i]);
					}
					rows.add(temp);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		module = new BaseTableModule(columnName, rows);
		table = new JTable(module);
		Tools.setTableStyle(table);
		
		
		sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		tablePanel = new JPanel(new BorderLayout());
		tablePanel.setOpaque(false);

		tablePanel.add(sroll);
		backgroundPanel.add(tablePanel);
	
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		refreshTablePanel();
		
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		refreshTablePanel();
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
