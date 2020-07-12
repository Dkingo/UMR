package Managerment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import dao.Impl.BaseDaoImpl;
import framework.AllFont;

public class ModifyCashierJFrame extends JFrame implements MouseListener{

	JPanel backgroundPanel, labelPanel, buttonPanel, contentPanel;
	JLabel label_Id, label_name, label_sex, label_age, label_tele, label_address; 
	JTextField id, name, age, tele, address;
	JButton button_save;
	JComboBox sex;
	
	// 获得屏幕的大小
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	// 表格对象
	EmployeeInformation paraentPanel;
	JTable table;
	int selectedRow;
	
	public ModifyCashierJFrame(EmployeeInformation paraentPanel, JTable table, int selectedRow) {
		this.paraentPanel = paraentPanel;
		this.table = table;
		this.selectedRow = selectedRow;
		
		initBackgroundPanel();

		this.add(backgroundPanel);

		this.setTitle("添加收银人员员工信息");
		this.setSize(640, 360);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	private void initBackgroundPanel() {
		backgroundPanel = new JPanel(new BorderLayout());
		
		initcontentPanel();
		initbuttonPanel();
		initLabelPanel();
		
		backgroundPanel.add(labelPanel, "North");
		backgroundPanel.add(buttonPanel, "South");
		backgroundPanel.add(contentPanel, "Center");
		
	}

	//初始化label信息
	private void initLabelPanel() {
		labelPanel = new JPanel();
		JLabel title = new JLabel("员工信息");
		title.setFont(AllFont.Static);
		labelPanel.add(title);
		
	}

	//初始化按钮面板
	private void initbuttonPanel() {
		buttonPanel = new JPanel();
		button_save = new JButton("保存");
		
		button_save.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_save.setForeground(Color.white);
		button_save.setFont(AllFont.Static);
		button_save.addMouseListener(this);
		buttonPanel.add(button_save);
		
	}

	//初始化员工信息面板
	private void initcontentPanel() {
		contentPanel = new JPanel(new GridLayout(7, 2));
		
		label_Id = new JLabel("员工账号", JLabel.CENTER);
		label_name = new JLabel("员工姓名", JLabel.CENTER);
		label_sex = new JLabel("员工性别", JLabel.CENTER);
		label_age = new JLabel("员工年龄", JLabel.CENTER);
		label_tele = new JLabel("电话号码", JLabel.CENTER);
		label_address = new JLabel("家庭住址", JLabel.CENTER);
		
		id = new JTextField(String.valueOf(table.getValueAt(selectedRow, 0)));
		name = new JTextField(String.valueOf(table.getValueAt(selectedRow, 1)));
		age = new JTextField(String.valueOf(table.getValueAt(selectedRow, 3)));
		tele = new JTextField(String.valueOf(table.getValueAt(selectedRow, 4)));
		address = new JTextField(String.valueOf(table.getValueAt(selectedRow, 5)));
		String sex_String = String.valueOf(table.getValueAt(selectedRow, 2));
		
		id.setEditable(false);
		
		sex = new JComboBox();
		sex.addItem("男");
		sex.addItem("女");
		sex.setSelectedItem(sex_String);
		
		contentPanel.add(label_Id);
		contentPanel.add(id);
		contentPanel.add(label_name);
		contentPanel.add(name);
		contentPanel.add(label_sex);
		contentPanel.add(sex);
		contentPanel.add(label_age);
		contentPanel.add(age);
		contentPanel.add(label_tele);
		contentPanel.add(tele);
		contentPanel.add(label_address);
		contentPanel.add(address);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		String id_String = id.getText().trim();
		String name_String = name.getText().trim();
		String age_String = age.getText().trim();
		String tele_String = tele.getText().trim();
		String address_String = address.getText().trim();
		String sex_String = (String) sex.getSelectedItem();
		int age_int = Integer.parseInt(age_String);
		
		if (id_String.isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入员工账号");
		} else if (name_String.isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入员工姓名");
		} else if (age_String.isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入员工年龄");
		} else if (tele_String.isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入电话号码");
		} else if (address_String.isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入家庭地址");
		} else if ("请选择".equals(sex_String)) {
			JOptionPane.showMessageDialog(null, "请选择员工性别");
		} else {
			BaseDaoImpl dao = new BaseDaoImpl();
			String sql = "update cashier set cash_username = ?, cash_sex = ?, cash_age = ?, cash_tele = ?, cash_address = ?"
					+ "where cash_userId = ?";
			Object params[] = {name_String, sex_String, age_int, tele_String, address_String, id_String};
			try {
				int result = dao.update(sql, params);
				if (result > 0) {
					JOptionPane.showMessageDialog(null, "更新员工数据成功!");
					paraentPanel.refreshTablePanel();
					this.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "更新员工数据失败!");
					this.setVisible(false);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
