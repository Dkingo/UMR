package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import CashView.CashRegister;
import CashView.RegisterJFrame;
import entity.CashUser;
import framework.AllFont;

public class CashIndexFrame extends JFrame implements ActionListener,MouseListener{
	
	int sign_cash = 0;
	int sign_vip = 0;
	
	private CashUser user;
	
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	JPanel backgroundPanel, topPanel, topMenu, topPrompt, centerPanel;
	JTabbedPane jTabbedPane;
	JLabel cash , vip, label_exit;
	
	
	public CashIndexFrame(CashUser user) throws HeadlessException {
		try {
			this.user = user;
			
			UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));
			initBackpanel();
			
			this.setTitle("超市收银系统");
			this.setSize((int) (width * 0.8f), (int) (height * 0.8f));
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("0");
		}
		
		
	}
	
	public void initBackpanel() {
		try {
			backgroundPanel = new JPanel(new BorderLayout());
			initTopPanel();
			initCenterPanel();

			backgroundPanel.add(topPanel, "North");
			backgroundPanel.add(centerPanel, "Center");

			this.add(backgroundPanel);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("1");
		}
		
	}
	
	// 初始化顶部欢迎面板
 	public void initTopPrompt() {

 		Icon icon = new ImageIcon("image/male.png");
 		JLabel label = new JLabel(icon);
 		label_exit = new JLabel();
 		if (user != null) {
 			label.setText("<html><font color='black'>欢迎您，</font><font color='#336699'><b>" + this.user.getCash_username()
 					+ "</b></font></html>");
 		} else {
 			label.setText("<html><font color='black'>欢迎您，</font><font color='#336699'><b></b></font></html>");
 		}
 		label_exit.setText("<html>&nbsp;<font color='red'>退出</font><font color='#336699'><b></b></font></html>");
 		label.setFont(AllFont.Static);
 		label_exit.setFont(AllFont.Static);
 		label_exit.addMouseListener(this);
 		topPrompt = new JPanel();
 		topPrompt.setPreferredSize(new Dimension(250, 40));
 		topPrompt.setOpaque(false);
 		topPrompt.add(label);
 		topPrompt.add(label_exit);

 	}
	
	public void initTopPanel() {
		try {
			initTopMenu();
			initTopPrompt();
	
			topPanel = new JPanel(new BorderLayout());
			topPanel.setPreferredSize(new Dimension(width, 40));
			
			topPanel.add(topMenu, "West");
			topPanel.add(topPrompt, "East");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("2");
		}
		
	}
	
	public void initTopMenu() {
		try {
			topMenu = new JPanel();
			topMenu.setPreferredSize(new Dimension(300, 40));
			topMenu.setOpaque(false);
			cash = new JLabel();
			vip = new JLabel();
			
			String[] nameStrings = { "收银" , "会员管理" };
			cash = createMenuItem(cash, nameStrings[0], "cash", topMenu);
			cash.setName("cash");
			vip = createMenuItem(vip, nameStrings[1], "vip", topMenu);
			vip.setName("vip");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("3");
		}
		
	}
	

    public JLabel createMenuItem(JLabel label , String text , String name , JPanel panel) {
		
		JLabel line = new JLabel("<html>&nbsp;<font color='#D2D2D2'>|</font>&nbsp;</html>");
		//Icon icon = new ImageIcon("image/" + name + ".png");
		//label = new JLabel(icon);
		label.setText("<html><font color='black'>" + text + "</font>&nbsp;</html>");
		label.addMouseListener(this);
		Font font = new Font("微软雅黑", Font.PLAIN, 14);
		label.setFont(font);
		panel.add(label);
		if(!"table".equals(name)) {
			panel.add(line);
		}
		
		return label;
	}
    
public void initCenterPanel() {
		
		try {
			centerPanel = new JPanel(new BorderLayout());
			cash.setText("<html><font color='#336699' style='font-weight:bold'>" + "收银" + "</font>&nbsp;</html>");
			creatCash();
			centerPanel.setOpaque(false);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("41");
		}
		
	}

public void creatCash() throws IOException {

	centerPanel.removeAll();
	// 设置tab标题位置
	jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
	// 设置tab布局
	jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
	jTabbedPane.setFont(AllFont.Static);
	jTabbedPane.addTab("收银", new CashRegister().backgroundPanel);
	centerPanel.add(jTabbedPane, "Center");
}

public void creatVip() {

	centerPanel.removeAll();
	
	jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
	// 设置tab布局
	jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
	jTabbedPane.setFont(AllFont.Static);
	// 设置tab标题位置
	jTabbedPane.addTab("会员管理", new RegisterJFrame().contentPane);
	centerPanel.add(jTabbedPane, "Center");
}

public void initSign() {
	sign_cash = 0;
	sign_vip = 0;
	
}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cash) {
			initSign();
			sign_cash = 1;
			try {
				creatCash();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cash.setText("<html><font color='#336699' style='font-weight:bold'>" + "收银" + "</font>&nbsp;</html>");
			vip.setText("<html><font color='black'>" + "会员管理" + "</font>&nbsp;</html>");
		} else if (e.getSource() == vip) {
			initSign();
			sign_vip = 1;
			creatVip();
            vip.setText("<html><font color='#336699' style='font-weight:bold'>" + "会员管理" + "</font>&nbsp;</html>");
			cash.setText("<html><font color='black'>" + "收银" + "</font>&nbsp;</html>");
		} else if (e.getSource() == label_exit){
			new LoginJFrame();
			this.setVisible(false);
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cash) {
			cash.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			cash.setText("<html><font color='#336699' style='font-weight:bold'>" + "收银" + "</font>&nbsp;</html>");
		} else if (e.getSource() == vip) {
			vip.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			vip.setText("<html><font color='#336699' style='font-weight:bold'>" + "会员管理" + "</font>&nbsp;</html>");
		} else if (e.getSource() == label_exit) {
			label_exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			label_exit.setText("<html>&nbsp;<font color='#336699'>" + "退出" + "</font></html>");
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cash ) {
			if (sign_cash == 0) {
				cash.setText("<html><font color='black'>" + "收银" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == vip) {
			if (sign_vip == 0) {
				vip.setText("<html><font color='black'>" + "会员管理" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == label_exit) {
			label_exit.setText("<html><font color='red'>&nbsp退出</font><font color='#336699'><b></b></font></html>");
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
