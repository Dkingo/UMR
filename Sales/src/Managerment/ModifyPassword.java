package Managerment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dao.Impl.BaseDaoImpl;
import framework.AllFont;

public class ModifyPassword extends JPanel implements ActionListener,MouseListener{
	
	public JPanel backgroundPanel;
	JPanel topPanel,centerPanel;
	JLabel label_name , label_password , label_password2 ,label1 , label2 ,label3, label4;
	JTextField name ;
	JPasswordField password , password2 ;
	JButton button_1;
	ButtonGroup group;
	JRadioButton jr_cashier, jr_manager;
	
	public ModifyPassword() {
		// TODO Auto-generated constructor stub
		initBackground();
	}
	
	public void initBackground() {
		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(new Color(235,235,235));
		initCenterPanel();
		
		backgroundPanel.add(centerPanel);
	}

	public void initCenterPanel() {
		
		label_name = new JLabel("用户账号   ");
		label_name.setFont(AllFont.cutiFont);
		label_password = new JLabel("新密码      ");
		label_password.setFont(AllFont.cutiFont);
		label_password2 = new JLabel("确认密码   ");
		label_password2.setFont(AllFont.cutiFont);
		
		name = new JTextField(15);
		name.setFont(AllFont.tatic);
		name.setPreferredSize(new Dimension(300 , 30));
		password = new JPasswordField(15);
		password.setFont(AllFont.tatic);
		password.setPreferredSize(new Dimension(300 , 30));
		password2 = new JPasswordField(15);
		password2.setFont(AllFont.tatic);
		password2.setPreferredSize(new Dimension(300 , 30));
		
		label1 = new JLabel();
		label1.setPreferredSize(new Dimension(300,10));
		label2 = new JLabel();
		label2.setPreferredSize(new Dimension(300,10));
		label3 = new JLabel();
		label3.setPreferredSize(new Dimension(400,20));
		label4 = new JLabel();
		label4.setPreferredSize(new Dimension(400,20));
		
		button_1 = new JButton("  确定  ");
		button_1.setFont(AllFont.jiemianFont);
		button_1.setBounds(750,500,40,30);
		button_1.addMouseListener(this);
		
		jr_cashier = new JRadioButton("收银人员", true);
		jr_cashier.setForeground(Color.black);
		jr_cashier.setBounds(380, 280, 100, 20);
		jr_manager = new JRadioButton("管理人员");
		jr_manager.setForeground(Color.black);
		jr_manager.setBounds(480, 280, 100, 20);
		group = new ButtonGroup();
		jr_cashier.setBackground(new Color(235,235,235));
		jr_manager.setBackground(new Color(235,235,235));
		group.add(jr_cashier);
		group.add(jr_manager);
		
		centerPanel = new JPanel();
		centerPanel.setBackground(new Color(235,235,235));
		centerPanel.setBounds(600, 200, 400, 500);
		centerPanel.add(label_name);
		centerPanel.add(name);
		centerPanel.add(label1);
		centerPanel.add(label_password);
		centerPanel.add(password);
		centerPanel.add(label2);
	    centerPanel.add(label_password2);
	    centerPanel.add(password2);
	    centerPanel.add(label3);
	    centerPanel.add(jr_cashier);
	    centerPanel.add(jr_manager);
	    centerPanel.add(label4);
	    centerPanel.add(button_1);
	    
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == button_1) {
			String name_String = name.getText().trim();
			String password_String = String.valueOf(password.getPassword());
			String password2_String = String.valueOf(password2.getPassword());
			
			if (name_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "用户账号不能为空");
			} else if (password_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "新密码不能为空");
			} else if (password2_String.isEmpty()) {
				JOptionPane.showMessageDialog(null,  "确认密码不能为空");
			} else if (!password_String.equals(password2_String)){
				JOptionPane.showMessageDialog(null,  "新密码与确认密码不同");
			} else {
				String sql = null;
				
				if (jr_cashier.isSelected()) {
					sql = "update cashier set cash_password = ? where cash_userId = ?";
				} else if (jr_manager.isSelected()) {
					sql = "update management set mag_password = ? where mag_username = ?";
				}
				
				Object[] params = {password_String, name_String};
				BaseDaoImpl dao = new BaseDaoImpl();
				try {
					int result = dao.update(sql, params);
					if (result > 0) {
						JOptionPane.showMessageDialog(null, "修改密码成功!");
						name.setText("");
						password.setText("");
						password2.setText("");
						password.setEchoChar('*');
						password2.setEchoChar('*');
					} else {
						JOptionPane.showMessageDialog(null, "修改密码失败!");
					}
				}catch (Exception e1) {
					e1.printStackTrace();
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

}
