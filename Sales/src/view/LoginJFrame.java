package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import entity.CashUser;
import entity.Manager;
import framework.AllFont;
import framework.ImagePanel;
import framework.SQLservice;
import framework.WindowOpacity;
import service.Impl.UserServiceImpl;

public class LoginJFrame extends JFrame implements MouseListener, FocusListener{

	//定义全局组件
	JTextField username = new JTextField(20);
	JPasswordField password = new JPasswordField(20);
	ImagePanel backgroundPanel = null;
	JButton button_login, button_reset;
	JRadioButton jr_cashier, jr_manager;
	ButtonGroup group;
	
	public LoginJFrame() {
		
		//窗口淡入淡出
		new WindowOpacity(this);
		
		//logo和登录背景
		Image backgrounImage = null;
		try {
			backgrounImage = ImageIO.read(new File("image/loginbackground.png"));
			Image imgae = ImageIO.read(new File("image/logo.png"));
			this.setIconImage(imgae);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 窗口背景面板
		backgroundPanel = new ImagePanel(backgrounImage);
		backgroundPanel.setLayout(null);

		username.setBounds(378, 202, 173, 30);
		username.setFont(AllFont.Static);
		username.addFocusListener(this);  
		username.setText("用户名/账户");

		password.setBounds(378, 240, 173, 30);
		password.setFont(AllFont.Static);
		password.addFocusListener(this);
		password.setText("密码");
		password.setEchoChar('\0');

		button_login = new JButton("登录");
		button_login.setBounds(380, 320, 70, 27);
		button_login.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_login.setForeground(Color.white);
		button_login.setFont(AllFont.Static);
		button_login.addMouseListener(this);

		button_reset = new JButton("重置");
		button_reset.setBounds(480, 320, 70, 27);
		button_reset.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		button_reset.setForeground(Color.white);
		button_reset.setFont(AllFont.Static);
		button_reset.addMouseListener(this);
		
		jr_cashier = new JRadioButton("收银人员", true);
		jr_cashier.setForeground(Color.black);
		jr_cashier.setBounds(380, 280, 100, 20);
		jr_manager = new JRadioButton("管理人员");
		jr_manager.setForeground(Color.black);
		jr_manager.setBounds(480, 280, 100, 20);
		group = new ButtonGroup();
		group.add(jr_cashier);
		group.add(jr_manager);

		backgroundPanel.add(username);
		backgroundPanel.add(password);
		backgroundPanel.add(jr_cashier);
		backgroundPanel.add(jr_manager);
		backgroundPanel.add(button_login);
		backgroundPanel.add(button_reset);

		this.add(backgroundPanel);
		this.setTitle("  全家超市管理系统");
		this.setSize(830, 530);
		this.setVisible(true);
		this.requestFocus();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == button_login) {
			if ("用户名/账户".equals(username.getText())) {
				JOptionPane.showMessageDialog(null, "用户名不能为空");
			} else if ("密码".equals(String.valueOf(password.getPassword()))) {
				JOptionPane.showMessageDialog(null, "用户密码不能为空");
			} else {
				String params[] = { username.getText(), String.valueOf(password.getPassword())};
				UserServiceImpl userService = new UserServiceImpl();
				try {
					
					Boolean flag = true; 
					CashUser cash_user = null;
					Manager man_user = null;
					
					if (jr_cashier.isSelected()) {
						cash_user = userService.selectOneWithCash(params);
						
					}
					else {
						flag = false;
						man_user = userService.selectOneWithManager(params); 
					}
					
					if (flag) {
						if (cash_user == null) { 
							JOptionPane.showMessageDialog(null, "收银人员或者密码有误");
							
						} else {
							new CashIndexFrame(cash_user);
							this.setVisible(false);
						}
					}
					else {
						if (man_user == null) {
							JOptionPane.showMessageDialog(null, "管理人员账号或者密码有误");
							
						} else {
							this.setVisible(false);
							new IndexFrame(man_user);
						}
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}  
			}
		} else if (e.getSource() == button_reset) {
			username.setText("用户名/账户");
			password.setText("密码");
			password.setEchoChar('\0');
		}

		
	}
	
	//聚焦事件
	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == username) {
			if (username.getText().equals("用户名/账户")) {
				username.setText("");
			}
		} else if (e.getSource() == password) {
			if (String.valueOf(password.getPassword()).equals("密码")) {
				password.setText("");
				password.setEchoChar('*');
			}
		}
		
	}

	//失焦事件
	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == username) {
			if (username.getText().equals("")) {
				username.setText("用户名/账户");
			}
		} else if (e.getSource() == password) {
			if (String.valueOf(password.getPassword()).equals("")) {
				password.setText("密码");
				password.setEchoChar('\0');
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
