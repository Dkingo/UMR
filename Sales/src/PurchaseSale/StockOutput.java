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

	// ��ʼ���������
	public void initTopPanel() {

		topPanel = new JPanel(new BorderLayout());

		initSearchPanel();
		initTabelPanel();

		backgroundPanel.add(topPanel, "North");
		backgroundPanel.add(tablePanel, "Center");

	}

	// ��ʼ�������������
	public void initSearchPanel() {

		searchPanel = new JPanel();

		// ��Ʒģ�����������
		input_name = new JTextField(10);
		input_exchange = new JTextField(8);
		input_name.getDocument().addDocumentListener(this);
		input_exchange.getDocument().addDocumentListener(this);

		// ��ǩ
		label_name = new JLabel("��Ʒ����");
		label_exchange = new JLabel("���׺�");

		searchPanel.add(label_exchange);
		searchPanel.add(input_exchange);
		searchPanel.add(label_name);
		searchPanel.add(input_name);
		topPanel.add(searchPanel, "East");
		
	}
	
	//�������
	public void refreshTablePanel(){
		backgroundPanel.remove(tablePanel);
		
		String columnName[] = {"��Ʒ���","��Ʒ��ˮ��", "���׺�","��Ʒ����","��Ʒ�۸�","��������","��������"};
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
	
	//��ʼ�����
	public void initTabelPanel() {
		
		String columnName[] = {"��Ʒ���","��Ʒ��ˮ��","���׺�","��Ʒ����","��Ʒ�۸�","��������","��������"};
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
