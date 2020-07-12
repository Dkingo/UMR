package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import Chart.MonthProfitLoss;
import Chart.PurchaseSaleChart;
import Chart.StatisticalChart;
import Commodity.GoodsBasedata;
import Commodity.GoodsLoss;
import Managerment.EmployeeInformation;
import Managerment.ModifyPassword;
import PurchaseSale.StockOutput;
import PurchaseSale.Warehousing;
import entity.Manager;
import framework.AllFont;
import framework.ImagePanel;


public class IndexFrame extends JFrame implements MouseListener, ActionListener{
	
	private Manager user;
	
	// 定义辅助变量
	int sign_home = 0;
	int sign_commodity = 0; 
	int sign_purchase_sale_stock = 0;
	int sign_userManager = 0; 
	int sign_chart = 0 ;
	
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	JPanel backgroundPanel;
	JPanel topPanel, topMenu, topPrompt, centerPanel;
	JTabbedPane jTabbedPane;
	JLabel home, commodity, purchaseSale , chart , management, label_exit;
	
	
	
	public IndexFrame(Manager user) throws HeadlessException {  
		this.user = user;
		try {
			UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0));
			initBackpanel();
			
			this.setTitle("超市管理系统");
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
			backgroundPanel = new JPanel();
			backgroundPanel.setLayout(new BorderLayout());
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
			topMenu.setPreferredSize(new Dimension(500, 40));
			topMenu.setOpaque(false);
			home = new JLabel();
			commodity = new JLabel();
			purchaseSale = new JLabel();
			chart = new JLabel();
			management = new JLabel();
			
			String[] nameStrings = { "首页", "商品", "进销存管理", "统计报表","人员管理" };
			home = createMenuItem(home, nameStrings[0], "home", topMenu);
			home.setName("home");
			commodity = createMenuItem(commodity, nameStrings[1], "commodity", topMenu);
			commodity.setName("commodity");
			purchaseSale = createMenuItem(purchaseSale, nameStrings[2], "purchaseSale", topMenu);
			purchaseSale.setName("purchaseSale");
			chart = createMenuItem(chart, nameStrings[3], "chart", topMenu);
			chart.setName("chart");;
			management = createMenuItem(management, nameStrings[4], "management", topMenu);
			management.setName("management");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("3");
		}
		
	}
	
     public JLabel createMenuItem(JLabel label , String text , String name , JPanel panel) {
		
		JLabel line = new JLabel("<html>&nbsp;<font color='#D2D2D2'>|</font>&nbsp;</html>");
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
	
  // 初始化顶部欢迎面板
 	public void initTopPrompt() {

 		Icon icon = new ImageIcon("image/male.png");
 		JLabel label = new JLabel(icon);
 		label_exit = new JLabel();
 		if (user != null) {
 			label.setText("<html><font color='black'>欢迎您，</font><font color='#336699'><b>" + this.user.getMag_username()
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
	
	public void initCenterPanel() {
		
		try {
			centerPanel = new JPanel(new BorderLayout());
			home.setText("<html><font color='#336699' style='font-weight:bold'>" + "首页" + "</font>&nbsp;</html>");
			creatHome();
			centerPanel.setOpaque(false);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("4");
		}
		
	}
	
	public void creatHome() { 

		centerPanel.removeAll();
		try {
			Image bgimg = ImageIO.read(new File("image/indexbackground.png"));
			ImagePanel centerBackground = new ImagePanel(bgimg);
			centerPanel.add(centerBackground, "Center");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("5");
		}

	}
	
	public void creatCommodity() {

		centerPanel.removeAll();
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(AllFont.Static);

		jTabbedPane.addTab("商品基本数据", new GoodsBasedata().backgroundPanel);
		jTabbedPane.addTab("商品损耗", new GoodsLoss().backgroundPanel);
		centerPanel.add(jTabbedPane, "Center");
	}
	
	public void creatPurchaseSale() {

		centerPanel.removeAll();
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(AllFont.Static);

		jTabbedPane.addTab("入库表", new Warehousing().backgroundPanel);
		jTabbedPane.addTab("出库表", new StockOutput().backgroundPanel);
//		jTabbedPane.addTab("商品库存", new WarehouseManagerJPanel().backgroundPanel);

		centerPanel.add(jTabbedPane, "Center");
	}
	
	public void refreshPurchaseSale() {

		centerPanel.removeAll();
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(AllFont.Static);

		jTabbedPane.addTab("入库表", new Warehousing().backgroundPanel);
		jTabbedPane.addTab("出库表", new StockOutput().backgroundPanel);
//		jTabbedPane.addTab("商品库存", new WarehouseManagerJPanel().backgroundPanel);

		centerPanel.add(jTabbedPane, "Center");
	}

	public void creatManagment() {

		centerPanel.removeAll();
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(AllFont.Static);

		jTabbedPane.addTab("人员信息管理", new EmployeeInformation().backgroundPanel);
		jTabbedPane.addTab("修改密码", new ModifyPassword().backgroundPanel);
		centerPanel.add(jTabbedPane, "Center");
	}

	public void creatChart() {

		centerPanel.removeAll();
		// 设置tab标题位置
		jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		// 设置tab布局
		jTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		jTabbedPane.setFont(AllFont.Static);

		jTabbedPane.addTab("季度年度统计报表", new StatisticalChart().backgroundPanel);
		jTabbedPane.addTab("商场月度盈亏报表", new MonthProfitLoss().backgroundPanel);
		jTabbedPane.addTab("商场进销存平衡表", new PurchaseSaleChart().backgroundPanel);


		centerPanel.add(jTabbedPane, "Center");
	}
	
	// 初始化辅助变量
		public void initSign() {
			sign_home = 0;
			sign_commodity = 0;
			sign_purchase_sale_stock = 0;
			sign_userManager = 0;
			sign_chart = 0 ;
		}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				if (e.getSource() == home) {
					initSign();
					sign_home = 1;
					creatHome();
					home.setText("<html><font color='#336699' style='font-weight:bold'>" + "首页" + "</font>&nbsp;</html>");
					commodity.setText("<html><font color='black'>" + "商品" + "</font>&nbsp;</html>");
					purchaseSale.setText("<html><font color='black'>" + "进销存管理" + "</font>&nbsp;</html>");
					management.setText("<html><font color='black'>" + "人员管理" + "</font>&nbsp;</html>");
					chart.setText("<html><font color='black'>" + "统计表" + "</font>&nbsp;</html>");
				} else if (e.getSource() == commodity) {
					initSign();
					sign_commodity = 1;
					creatCommodity();
		            commodity.setText("<html><font color='#336699' style='font-weight:bold'>" + "商品" + "</font>&nbsp;</html>");
					home.setText("<html><font color='black'>" + "首页" + "</font>&nbsp;</html>");
					purchaseSale.setText("<html><font color='black'>" + "进销存管理" + "</font>&nbsp;</html>");
					management.setText("<html><font color='black'>" + "人员管理" + "</font>&nbsp;</html>");
					chart.setText("<html><font color='black'>" + "统计表" + "</font>&nbsp;</html>");
				} else if (e.getSource() == purchaseSale) {
					initSign();
					sign_purchase_sale_stock = 1;
					creatPurchaseSale();
					purchaseSale.setText(  
							"<html><font color='#336699' style='font-weight:bold'>" + "进销存管理" + "</font>&nbsp;</html>");
					home.setText("<html><font color='black'>" + "首页" + "</font>&nbsp;</html>");
					commodity.setText("<html><font color='black'>" + "商品" + "</font>&nbsp;</html>");
					management.setText("<html><font color='black'>" + "人员管理" + "</font>&nbsp;</html>");
					chart.setText("<html><font color='black'>" + "统计表" + "</font>&nbsp;</html>");
				} else if (e.getSource() == chart) {
					initSign();
					sign_chart = 1;
					creatChart();
					chart.setText("<html><font color='#336699' style='font-weight:bold'>" + "统计表" + "</font>&nbsp;</html>");
					home.setText("<html><font color='black'>" + "首页" + "</font>&nbsp;</html>");
					commodity.setText("<html><font color='black'>" + "商品" + "</font>&nbsp;</html>");
					management.setText("<html><font color='black'>" + "人员管理" + "</font>&nbsp;</html>");
					purchaseSale.setText("<html><font color='black'>" + "进销存管理" + "</font>&nbsp;</html>");
				} 
				else if(e.getSource() == management){
					initSign();
					sign_userManager = 1 ;
					creatManagment();
					management.setText("<html><font color='#336699' style='font-weight:bold'>" + "人员管理" + "</font>&nbsp;</html>");
					home.setText("<html><font color='black'>" + "首页" + "</font>&nbsp;</html>");
					commodity.setText("<html><font color='black'>" + "商品" + "</font>&nbsp;</html>");
					purchaseSale.setText("<html><font color='black'>" + "进销存管理" + "</font>&nbsp;</html>");
					chart.setText("<html><font color='black'>" + "统计表" + "</font>&nbsp;</html>");
				}else if (e.getSource() == label_exit){
					new LoginJFrame();
					this.setVisible(false);
					
				}else {
					System.out.println("ok");
				}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == home) {
			home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			home.setText("<html><font color='#336699' style='font-weight:bold'>" + "首页" + "</font>&nbsp;</html>");
		} else if (e.getSource() == commodity) {
			commodity.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			commodity.setText("<html><font color='#336699' style='font-weight:bold'>" + "商品" + "</font>&nbsp;</html>");
		} else if (e.getSource() == purchaseSale) {
			purchaseSale.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			purchaseSale.setText(
					"<html><font color='#336699' style='font-weight:bold'>" + "进销存管理" + "</font>&nbsp;</html>");
		} else if (e.getSource() == chart) {
			chart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			chart.setText("<html><font color='#336699' style='font-weight:bold'>" + "统计表" + "</font>&nbsp;</html>");
		}else if (e.getSource() == management) {
			management.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			management.setText("<html><font color='#336699' style='font-weight:bold'>" + "人员管理" + "</font>&nbsp;</html>");
		} else if (e.getSource() == label_exit) {
			label_exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			label_exit.setText("<html>&nbsp;<font color='#336699'>" + "退出" + "</font></html>");
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == home) {
			if (sign_home == 0) {
				home.setText("<html><font color='black'>" + "首页" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == commodity) {
			if (sign_commodity == 0) {
				commodity.setText("<html><font color='black'>" + "商品" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == purchaseSale) {
			if (sign_purchase_sale_stock == 0) {
				purchaseSale.setText("<html><font color='black'>" + "进销存管理" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == chart) {
			if (sign_chart == 0) {
				chart.setText("<html><font color='black'>" + "统计表" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == management) {
			if (sign_userManager == 0) {
				management.setText("<html><font color='black'>" + "人员管理" + "</font>&nbsp;</html>");
			}
		} else if (e.getSource() == label_exit) {
			label_exit.setText("<html><font color='red'>&nbsp退出</font><font color='#336699'><b></b></font></html>");
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
