package Managerment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dao.Impl.BaseDaoImpl;
import framework.BaseTableModule;
import framework.Tools;

public class EmployeeInformation extends JPanel implements ActionListener,MouseListener{

	public JPanel backgroundPanel;
	JPanel topPanel;
	JPanel toolPanel;
	JPanel tablePanel;
	JLabel  tool_add, tool_modify, tool_delete;
	
	BaseTableModule module;
	JScrollPane sroll;
	JTable table;
	
	public EmployeeInformation() {
		// TODO Auto-generated constructor stub
		backgroundPanel = new JPanel(new BorderLayout());

		initTopPanel();
		
	}

	// 初始化顶部面板
	public void initTopPanel() {

		topPanel = new JPanel(new BorderLayout());

		initToolPanel();
		initTablePanel();
		backgroundPanel.add(topPanel, "North");
		backgroundPanel.add(tablePanel, "Center");

	}

	// 初始化工具面板
	public void initToolPanel() {

		toolPanel = new JPanel();
		// 工具图标
		Icon icon_add = new ImageIcon("image/add.png");
		tool_add = new JLabel(icon_add);
		tool_add.setToolTipText("添加新员工");
		tool_add.addMouseListener(this);

		Icon icon_modify = new ImageIcon("image/modify.png");
		tool_modify = new JLabel(icon_modify);
		tool_modify.setToolTipText("修改员工信息");
		tool_modify.addMouseListener(this);

		Icon icon_delete = new ImageIcon("image/delete.png");
		tool_delete = new JLabel(icon_delete);
		tool_delete.setToolTipText("删除离职员工信息");
		tool_delete.addMouseListener(this);

		toolPanel.add(tool_add);
		toolPanel.add(tool_modify); 
		toolPanel.add(tool_delete);

		topPanel.add(toolPanel, "West");

	}
	
	//初始化面板
	public void initTablePanel() {
		String columnName[] = {"账号", "姓名", "性别", "年龄", "手机号码", "家庭住址"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			String sql = "select cash_userId, cash_username, cash_sex, cash_age, cash_tele, cash_address from cashier";
			BaseDaoImpl dao = new BaseDaoImpl();
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
		}catch (Exception e) {
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
	}
	
	//更新Table面板
	public void refreshTablePanel() {
		backgroundPanel.remove(tablePanel);
		
		String columnName[] = {"账号", "姓名", "性别", "年龄", "手机号码", "家庭住址"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			String sql = "select cash_userId, cash_username, cash_sex, cash_age, cash_tele, cash_address from cashier";
			BaseDaoImpl dao = new BaseDaoImpl();
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
		}catch (Exception e) {
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
		backgroundPanel.validate();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == tool_add) {
			new AddCashierJFrame(this);
		} else if (e.getSource() == tool_modify) {
			
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "请选择员工");
			} else {
				new ModifyCashierJFrame(this, table, row);
				refreshTablePanel();
			}
		} else if (e.getSource() == tool_delete){
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "请选择员工");
			} else {
				String id_is = (String) table.getValueAt(row, 0);
				int result = JOptionPane.showConfirmDialog(null, "是否确定删除？", "用户提示", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					String sql = "delete from cashier where cash_userId = ?";
					Object[] params = {id_is};
					System.out.println(id_is);
					BaseDaoImpl dao = new BaseDaoImpl();
					try {
						int result_delete = dao.delete(sql, params);
						if (result_delete > 0) {
							JOptionPane.showMessageDialog(null, "员工信息删除成功!");
							refreshTablePanel();
						} else {
							JOptionPane.showMessageDialog(null, "员工信息删除失败!");
						}
					}catch (Exception e1) {
						
					}
				}
				
				
				refreshTablePanel();
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

}
