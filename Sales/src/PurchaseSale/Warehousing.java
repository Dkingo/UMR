package PurchaseSale;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.Impl.BaseDaoImpl;
import framework.BaseTableModule;
import framework.Tools;


public class Warehousing extends JPanel implements ActionListener,MouseListener,DocumentListener{
	
	public JPanel backgroundPanel; 
	private JPanel topPanel, toolPanel, searchPanel, tablePanel;
	JComboBox select_category;
	JTextField input_name;
	JLabel label_name, label_category, tool_add , tool_delete;
	BaseTableModule module;
	JScrollPane sroll;
	JTable table;
	
	public Warehousing(){
	

		backgroundPanel = new JPanel(new BorderLayout());

		initTopPanel();
	}
	
	public void initTopPanel() {

		topPanel = new JPanel(new BorderLayout());

		initToolPanel();
		initTablePanel();
		initSearchPanel();

		backgroundPanel.add(topPanel, "North");
		backgroundPanel.add(tablePanel, "Center");
	}

	// ��ʼ���������
	public void initToolPanel() {

		toolPanel = new JPanel();
		// ����ͼ��
		Icon icon_add = new ImageIcon("image/add.png");
		tool_add = new JLabel(icon_add);
		tool_add.setToolTipText("�½���ⵥ");
		tool_add.addMouseListener(this);
		
		Icon icon_delete = new ImageIcon("image/delete.png");
		tool_delete = new JLabel(icon_delete);
		tool_delete.setToolTipText("ɾ����ⵥ");
		tool_delete.addMouseListener(this);
		
		toolPanel.add(tool_add);
		toolPanel.add(tool_delete);

		topPanel.add(toolPanel, "West");

	}

	public void initSearchPanel() {

		searchPanel = new JPanel();

		// ��Ʒģ�����������
		input_name = new JTextField(10);
		input_name.getDocument().addDocumentListener(this);

		// ��ǩ
		label_name = new JLabel("��Ʒ����");

		searchPanel.add(label_name);
		searchPanel.add(input_name);
		topPanel.add(searchPanel, "East");   
		
	}
	
	//��ʼ�����
	public void initTablePanel() {
		String columnName[] = {"��Ʒ���","��Ʒ��ˮ��","��Ʒ����","��Ʒ�������","������","���ۼ�","�������"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			String sql = "select * from warehousing";
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
			System.out.println("center");
		}
		module = new BaseTableModule(columnName, rows);
		table = new JTable(module);
		Tools.setTableStyle(table);
		
		
		sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		tablePanel = new JPanel(new BorderLayout());
		tablePanel.setOpaque(false);

		tablePanel.add(sroll);
	}
	
	//�������
	public void refreshTablePanel() {
		
		backgroundPanel.remove(tablePanel);
		String columnName[] = {"��Ʒ���","��Ʒ��ˮ��","��Ʒ����","��Ʒ�������","������","���ۼ�","�������"};
		String name = input_name.getText();
		BaseDaoImpl base = new BaseDaoImpl();
		Vector<Vector> vector = new Vector<Vector>();
		try {
			String table_name = "warehousing";
			vector = base.selectByName(name, table_name, 7);
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == tool_add) {
			new AddGoodsJFrame(this);
		} else if (e.getSource() == tool_delete){
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ����Ʒ");
			} else {
				int id_is = (Integer) table.getValueAt(row, 0);
				int result = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����", "�û���ʾ", JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					Object[] params = {id_is};
					BaseDaoImpl dao = new BaseDaoImpl();
					BaseDaoImpl dao_comm = new BaseDaoImpl();
					String sql_comm = "delete from commodityinformation where id = ?";
					String sql_delete = "delete from warehousing where id = ?";
					int result_dele = 0, result_comm = 0;
					try {
						result_dele = dao.delete(sql_delete, params);
						result_comm = dao_comm.delete(sql_comm, params);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (result_dele > 0 && result_comm > 0) {
						JOptionPane.showMessageDialog(null, "ɾ����Ʒ�ɹ���");
						refreshTablePanel();
					} else {
						JOptionPane.showMessageDialog(null, "ɾ����Ʒʧ��!");
					}
				}
			}
		}
		
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

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		refreshTablePanel();
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		refreshTablePanel();
		
	}
}
