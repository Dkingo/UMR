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
	
	//初始化面板
	public void initCenterPanel() {
		
		String columnName[] = {"商品编号","商品名称","商品库存","商品种类","商品描述","生产厂商","进货价","出售价"};
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
		        int modelRow = convertRowIndexToModel(row);   //行
		        int modelColumn = convertColumnIndexToModel(column);  //列
		        java.awt.Component comp = super.prepareRenderer(renderer, row, column);  
		        int count = 0, cnt = -1;
		        comp.setBackground(new Color(230,230,230));
		        for (int i = 0; i < table.getRowCount(); i++) {
		        	float num = Float.parseFloat(String.valueOf(table.getValueAt(i, 2)));
	        		if (num <= 5) {
	        			cnt = count;
	        		}
	        		count++;
	        		if (modelColumn == 2 && modelRow == cnt)          { //此处加入条件判断
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
	
	//更新数据面板
	public void refreshTablePanel() {
		
		backgroundPanel.remove(centerPanel);
		String columnName[] = {"商品编号","商品名称","商品库存","商品种类","商品描述","生产厂商","进货价","出售价"};
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
		        int modelRow = convertRowIndexToModel(row);   //行
		        int modelColumn = convertColumnIndexToModel(column);  //列
		        java.awt.Component comp = super.prepareRenderer(renderer, row, column);  
		        int count = 0, cnt = -1;
		        comp.setBackground(new Color(230,230,230));
		        for (int i = 0; i < table.getRowCount(); i++) {
		        	float num = Float.parseFloat(String.valueOf(table.getValueAt(i, 2)));
	        		if (num <= 5) {
	        			cnt = count;
	        		}
	        		count++;
	        		if (modelColumn == 2 && modelRow == cnt)          { //此处加入条件判断
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
			
			// 删去已下架的商品
			Icon icon_delete = new ImageIcon("image/delete.png");
			label_delete = new JLabel(icon_delete);
			label_delete.setToolTipText("删去商品");
			label_delete.addMouseListener(this);
			
			// 修改商品的信息
			Icon icon_modify = new ImageIcon("image/modify.png");
			label_modify = new JLabel(icon_modify);
			label_modify.setToolTipText("修改商品信息");
			label_modify.addMouseListener(this);
			
			topMenuPanel.add(label_delete);
			topMenuPanel.add(label_modify);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("topMenuPanel of GoodsBasedata");
		}
		
	}
	
	
	//根据商品id,名称, 生产厂商，商品类别搜索
	public void initFieldPanel() {
		
		topFieldPanel = new JPanel();
		
		//商品名称和id的模糊搜索
		input_name = new JTextField(10);
		input_id = new JTextField(5);
		input_name.getDocument().addDocumentListener(this);
		input_id.getDocument().addDocumentListener(this);
		
		//商品种类下拉框
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
		select_category.addItem("全部");
		if (list_category != null) {
			for (Object[] object : list_category) {
				select_category.addItem(object[0]);
			}
		}
		select_category.addActionListener(this);
		
		//生产厂商下拉框
		select_origin = new JComboBox();
		List<Object[]> list_origin = null;
		try {
			BaseDaoImpl dao_ori = new BaseDaoImpl();
			String sql_ori = "SELECT DISTINCT manufacturer FROM commodityinformation";
			list_origin = dao_ori.select(sql_ori, 1, null);
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		select_origin.addItem("全部");
		if (list_origin != null) {
			for (Object[] object : list_origin) {
				select_origin.addItem(object[0]);
			}
		}
		select_origin.addActionListener(this);
		
		label_id = new JLabel("商品编号");
		label_name = new JLabel("商品名称");
		label_category = new JLabel("商品种类");
		label_origin = new JLabel("生产厂商");
		
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
				JOptionPane.showMessageDialog(null, "请选择商品");
			} else {
				new ModifyGoodsJFrame(this, table, row);
			}
		} else {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "请选择商品");
			} else {
				int id_is = (Integer) table.getValueAt(row, 0);
				int result = JOptionPane.showConfirmDialog(null, "是否确定删除？", "用户提示", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Object[] params = {id_is};
					BaseDaoImpl dao = new BaseDaoImpl();
					BaseDaoImpl dao_select = new BaseDaoImpl();
					String sql = "delete from commodityinformation where id = ?";
					try {
						int result_stock = dao_select.selectById(id_is);
						if (result_stock != 0) {
							JOptionPane.showMessageDialog(null, "所选商品库存不为零");
						} else {
							int result_dele = dao.delete(sql, params);
							if (result_dele > 0) {
								JOptionPane.showMessageDialog(null, "删除商品成功！");
								refreshTablePanel();
							} else {
								JOptionPane.showMessageDialog(null, "删除商品失败!");
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
