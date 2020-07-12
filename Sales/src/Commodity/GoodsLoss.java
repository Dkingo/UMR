package Commodity;

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

public class GoodsLoss extends JPanel implements ActionListener,MouseListener,DocumentListener{

	public JPanel backgroundPanel;
	private JPanel topPanel , centerPanel , topRightPanel, topMenuPanel;
	JLabel label_company , label_goodsname, label_delete, label_add, label_modify;
	JTextField goodsname;
	
	JScrollPane sroll ;
	BaseTableModule module;
	JTable table;
	
	public GoodsLoss() {
		// TODO Auto-generated constructor stub
		initBackground();
	}
	
	public void initBackground() {
		backgroundPanel = new JPanel(new BorderLayout());
		initTopPanel();
		initCenterPanel();
		
		backgroundPanel.add(topPanel,"North");
		backgroundPanel.add(centerPanel,"Center");
	}
	
	public void initCenterPanel() {
		String columnName[] = {"商品编号","商品流水号","商品名称","商品损耗数量","商品损耗原因", "损耗时间"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			String sql = "select * from wastage";
			List<Object[]> list = base.select(sql, 6, null);
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
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setOpaque(false);

		centerPanel.add(sroll);
		
	}
	
	//更新面板
	public void refreshTanlePanel() {
		backgroundPanel.remove(centerPanel);
		
		String columnName[] = {"商品编号","商品流水号","商品名称","商品损耗数量","商品损耗原因", "损耗时间"};
		BaseDaoImpl base = new BaseDaoImpl();
		String lossname = goodsname.getText();
		Vector rows = new Vector<Vector>();
		try {
			String table_name = "wastage";
			rows = base.selectByName(lossname, table_name, 6);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		module = new BaseTableModule(columnName, rows);
		table = new JTable(module);
		Tools.setTableStyle(table);
		
		
		sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setOpaque(false);

		centerPanel.add(sroll);
		backgroundPanel.add(centerPanel);
		backgroundPanel.validate();
	}
	
	public void initTopPanel() {
		topPanel = new JPanel(new BorderLayout());
		initRightPanel();
		initTopMenuPanel();
		
		topPanel.add(topRightPanel,"East");
		topPanel.add(topMenuPanel, "West");
	}
	
	//按照名字进行搜索
	public void initRightPanel() {
		topRightPanel = new JPanel();
		goodsname = new JTextField(15);
		label_goodsname = new JLabel("商品名字");
		goodsname.getDocument().addDocumentListener(this);
		
		topRightPanel.add(label_goodsname);
		topRightPanel.add(goodsname);
	}
	
	public void initTopMenuPanel() {
		try {
			topMenuPanel = new JPanel();
			
			//添加损耗商品
			Icon icon_add = new ImageIcon("image/add.png");
			label_add = new JLabel(icon_add);
			label_add.setToolTipText("删去商品");
			label_add.addMouseListener(this);
			
			// 删去商品
			Icon icon_delete = new ImageIcon("image/delete.png");
			label_delete = new JLabel(icon_delete);
			label_delete.setToolTipText("删去商品");
			label_delete.addMouseListener(this);
			
			// 修改商品的信息
			Icon icon_modify = new ImageIcon("image/modify.png");
			label_modify = new JLabel(icon_modify);
			label_modify.setToolTipText("修改商品信息");
			label_modify.addMouseListener(this);
			
			topMenuPanel.add(label_add);
			topMenuPanel.add(label_delete);
			topMenuPanel.add(label_modify);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("topMenuPanel of GoodsBasedata");
		}
		
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == label_add) {
			new AddLossJFrame(this);
		} else if (e.getSource() == label_modify) {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "请选择商品");
			} else {
				new ModifyLossJFrame(this, table, row);
			}
		} else if (e.getSource() == label_delete) {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "请选择商品");
			} else {
				int no_is = (Integer) table.getValueAt(row, 1);
				int result = JOptionPane.showConfirmDialog(null, "是否确定删除？", "用户提示", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					Object[] params = {no_is};
					BaseDaoImpl dao = new BaseDaoImpl();
					String sql = "delete from wastage where no = ?";
					try {
							int result_dele = dao.delete(sql, params);
							if (result_dele > 0) {
								JOptionPane.showMessageDialog(null, "删除商品成功！");
								refreshTanlePanel();
								this.setVisible(false);
							} else {
								JOptionPane.showMessageDialog(null, "删除商品失败!");
							}
						} catch (SQLException e1) {
						e1.printStackTrace();
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
	public void insertUpdate(DocumentEvent e) {
		refreshTanlePanel();
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		refreshTanlePanel();
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
