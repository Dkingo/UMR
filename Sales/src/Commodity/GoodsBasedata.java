package Commodity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;

import dao.Impl.BaseDaoImpl;
import framework.BaseTableModule;
import framework.Tools;


public class GoodsBasedata extends JPanel implements ActionListener,MouseListener, DocumentListener{
	
	public JPanel backgroundPanel;
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel topMenuPanel;
	private JPanel topFieldPanel;
	private JLabel label_add , label_delete , label_modify , label_category, label_origin, label_name, label_id ;
	private JTextField input_name, input_id;
	JComboBox select_category, select_origin;
	JScrollPane sroll ;
	BaseTableModule module;
	JTable table;
	
	public GoodsBasedata() {
		
		initBackground();
		
	}
	
	public void initBackground() {
		try {
			backgroundPanel = new JPanel(new BorderLayout());
			initTopPanel();
			initCenterPanel();
			
			backgroundPanel.add(topPanel,"North");
			backgroundPanel.add(centerPanel,"Center");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("background of GoodsBasedata");
		}
	} 
	 
	public void initTopPanel() {
		
		try {
			topPanel = new JPanel(new BorderLayout());
			initTopMenuPanel();
			initFieldPanel();
			
			topPanel.add(topMenuPanel,"West");
			topPanel.add(topFieldPanel,"East");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("topPanel of GoodsBasedata");
		}
		
	}
	
	//��ʼ�����
	public void initCenterPanel() {
		
		String columnName[] = {"��Ʒ���","��Ʒ����","��Ʒ���","��Ʒ����","��Ʒ����","��������","������","���ۼ�"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			String sql = "select * from commodityinformation";
			List<Object[]> list = base.select(sql, 8, null);
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
		
		table = new JTable(module)  {
			public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {  
		        int modelRow = convertRowIndexToModel(row);   //��
		        int modelColumn = convertColumnIndexToModel(column);  //��
		        java.awt.Component comp = super.prepareRenderer(renderer, row, column);  
		        int count = 0, cnt = -1;
		        comp.setBackground(new Color(230,230,230));
		        for (int i = 0; i < table.getRowCount(); i++) {
		        	float num = Float.parseFloat(String.valueOf(table.getValueAt(i, 2)));
	        		if (num <= 5) {
	        			cnt = count;
	        		}
	        		count++;
	        		if (modelColumn == 2 && modelRow == cnt)          { //�˴����������ж�
	        			comp.setBackground(java.awt.Color.RED);
	        		}        
		        }
		        table.setSelectionForeground(new Color(51, 153, 255));
		        return comp;
		    }
		};
		Tools.setTableStyle(table);
		
		sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setOpaque(false);

		centerPanel.add(sroll);
	}
	
	//�����������
	public void refreshTablePanel() {
		
		backgroundPanel.remove(centerPanel);
		String columnName[] = {"��Ʒ���","��Ʒ����","��Ʒ���","��Ʒ����","��Ʒ����","��������","������","���ۼ�"};
		String name = input_name.getText();
		String id = input_id.getText();
		String category_String = (String) select_category.getSelectedItem();
		String origin_String = (String) select_origin.getSelectedItem();
		Object conditionParams[] = {category_String, origin_String, name, id };
		BaseDaoImpl base = new BaseDaoImpl();
		Vector<Vector> vector = new Vector<Vector>();
		try {
			vector = base.selectByCondition(conditionParams);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		module = new BaseTableModule(columnName, vector);
		table = new JTable(module) {
			public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {  
		        int modelRow = convertRowIndexToModel(row);   //��
		        int modelColumn = convertColumnIndexToModel(column);  //��
		        java.awt.Component comp = super.prepareRenderer(renderer, row, column);  
		        int count = 0, cnt = -1;
		        comp.setBackground(new Color(230,230,230));
		        for (int i = 0; i < table.getRowCount(); i++) {
		        	float num = Float.parseFloat(String.valueOf(table.getValueAt(i, 2)));
	        		if (num <= 5) {
	        			cnt = count;
	        		}
	        		count++;
	        		if (modelColumn == 2 && modelRow == cnt)          { //�˴����������ж�
	        			comp.setBackground(java.awt.Color.RED);
	        		}        
		        }
		        table.setSelectionForeground(new Color(51, 153, 255));
		        return comp;
		    }
		};
		Tools.setTableStyle(table);
		
		
		sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.LIGHT_GRAY);
		centerPanel.setOpaque(false);

		centerPanel.add(sroll);
		backgroundPanel.add(centerPanel);
		backgroundPanel.validate();
	}
	
	public void initTopMenuPanel() {
		try {
			topMenuPanel = new JPanel();
			
			// ɾȥ���¼ܵ���Ʒ
			Icon icon_delete = new ImageIcon("image/delete.png");
			label_delete = new JLabel(icon_delete);
			label_delete.setToolTipText("ɾȥ��Ʒ");
			label_delete.addMouseListener(this);
			
			// �޸���Ʒ����Ϣ
			Icon icon_modify = new ImageIcon("image/modify.png");
			label_modify = new JLabel(icon_modify);
			label_modify.setToolTipText("�޸���Ʒ��Ϣ");
			label_modify.addMouseListener(this);
			
			topMenuPanel.add(label_delete);
			topMenuPanel.add(label_modify);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("topMenuPanel of GoodsBasedata");
		}
		
	}
	
	
	//������Ʒid,����, �������̣���Ʒ�������
	public void initFieldPanel() {
		
		topFieldPanel = new JPanel();
		
		//��Ʒ���ƺ�id��ģ������
		input_name = new JTextField(10);
		input_id = new JTextField(5);
		input_name.getDocument().addDocumentListener(this);
		input_id.getDocument().addDocumentListener(this);
		
		//��Ʒ����������
		select_category = new JComboBox();
		List<Object[]> list_category = null;
		String sql_name = "select distinct category from commodityinformation";
		try {
			BaseDaoImpl dao = new BaseDaoImpl();
			list_category = dao.select(sql_name, 1, null);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("topFieldPanel of GoodsBasedata");
		}
		select_category.addItem("ȫ��");
		if (list_category != null) {
			for (Object[] object : list_category) {
				select_category.addItem(object[0]);
			}
		}
		select_category.addActionListener(this);
		
		//��������������
		select_origin = new JComboBox();
		List<Object[]> list_origin = null;
		try {
			BaseDaoImpl dao_ori = new BaseDaoImpl();
			String sql_ori = "SELECT DISTINCT manufacturer FROM commodityinformation";
			list_origin = dao_ori.select(sql_ori, 1, null);
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		select_origin.addItem("ȫ��");
		if (list_origin != null) {
			for (Object[] object : list_origin) {
				select_origin.addItem(object[0]);
			}
		}
		select_origin.addActionListener(this);
		
		label_id = new JLabel("��Ʒ���");
		label_name = new JLabel("��Ʒ����");
		label_category = new JLabel("��Ʒ����");
		label_origin = new JLabel("��������");
		
		topFieldPanel.add(label_id);
		topFieldPanel.add(input_id);
		topFieldPanel.add(label_name);
		topFieldPanel.add(input_name);
		topFieldPanel.add(label_category);
		topFieldPanel.add(select_category);
		topFieldPanel.add(label_origin);
		topFieldPanel.add(select_origin);
		
		topPanel.add(topFieldPanel, "East");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == label_modify) {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ����Ʒ");
			} else {
				new ModifyGoodsJFrame(this, table, row);
			}
		} else {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ����Ʒ");
			} else {
				int id_is = (Integer) table.getValueAt(row, 0);
				int result = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����", "�û���ʾ", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Object[] params = {id_is};
					BaseDaoImpl dao = new BaseDaoImpl();
					BaseDaoImpl dao_select = new BaseDaoImpl();
					String sql = "delete from commodityinformation where id = ?";
					try {
						int result_stock = dao_select.selectById(id_is);
						if (result_stock != 0) {
							JOptionPane.showMessageDialog(null, "��ѡ��Ʒ��治Ϊ��");
						} else {
							int result_dele = dao.delete(sql, params);
							if (result_dele > 0) {
								JOptionPane.showMessageDialog(null, "ɾ����Ʒ�ɹ���");
								refreshTablePanel();
							} else {
								JOptionPane.showMessageDialog(null, "ɾ����Ʒʧ��!");
							}
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == select_category) {
			refreshTablePanel();
		} else if (e.getSource() == select_origin) {
			refreshTablePanel();
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
	public void insertUpdate(DocumentEvent e) {
		refreshTablePanel();
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		refreshTablePanel();
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
