package CashView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.Impl.BaseDaoImpl;
import framework.AllFont;
import framework.BaseTableModule;
import framework.Tools;

public class Print extends JFrame implements MouseListener{

	JPanel backgroundPanel ,topPanel, bottomUpPanel,bottomDownPanel, bottomCenPanel,centerPanel , bottomPanel ;
	JTextField name,saleprice,quanlity,id;
	JLabel  title , cpayJLabel ,tpayJLabel ,cashJLabel , cpay,tpay,cash ,tip,time;
	CashRegister cashRegister;
	JButton button ;
	JTable table;
	JTable table1;
	int selectRow;
	
	public Print(CashRegister cashRegister ) {
		// TODO Auto-generated constructor stub
		this.cashRegister = cashRegister ;
		initBackgroundPanel();

		this.add(backgroundPanel);

		this.setTitle("添加商品");
		this.setSize(500, 800);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public void initBackgroundPanel() {
	 initTopPanel();
	 initCenterPanel();
	 initBottomPanel();
	 
	 backgroundPanel = new JPanel(new BorderLayout());
	 backgroundPanel.add(topPanel,"North");
	 backgroundPanel.add(centerPanel,"Center");
	 backgroundPanel.add(bottomPanel,"South");
	}
	
	public void initTopPanel() {
		topPanel = new JPanel();
		title = new JLabel("全家购物清单");	
		title.setFont(AllFont.cutiFont);
		topPanel.add(title);
	}
	
	
	public void initCenterPanel() {
		
		String columnName[] = {"商品名称","出售价","数量","金额"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector<Vector> rows = new Vector<Vector>();
		float a = 0 ;
		int b = 0 ;
		try {
			String sql = "select * from buying";
			List<Object[]> list = base.select(sql, 5, null);
			if (!list.isEmpty()) {
				for (Object[] object : list) {
					Vector temp = new Vector<String>();
					for (int i = 0; i < object.length; i++) {
						if( i==2 || i==3 || i==4 ){
							temp.add(object[i]);
							}
						 a = (Float)object[3];
						 b =(Integer)object[4];
					}
					float total = a*b;
					temp.add(total);
					rows.add(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		BaseTableModule module = new BaseTableModule(columnName, rows);
		table1 = new JTable(module);
		
		Tools.setTableStyle(table1);
		
		
		JScrollPane sroll1 = new JScrollPane(table1);
		Tools.setJspStyle(sroll1);
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setOpaque(false);

		centerPanel.add(sroll1);
	}
	
	public void initBottomPanel() {
		bottomPanel = new JPanel(new BorderLayout());
		initButtomUpPanel();
		initBottomCenPanel();
		initButtomDownPanel();
		bottomPanel.add(bottomUpPanel,"North");
		bottomPanel.add(bottomCenPanel,"Center");
		bottomPanel.add(bottomDownPanel,"South");
	}
	
	public void initButtomUpPanel() {
		bottomUpPanel = new JPanel(new GridLayout(3,2));
		String cpayString = (cashRegister.cpay).getText();
		String tpString = (cashRegister.tpay).getText();
		String cashString = (cashRegister.change).getText();
		
		cpayJLabel = new JLabel("顾客支付金额 : ",JLabel.CENTER);
		tpayJLabel = new JLabel("实际应收金额  :",JLabel.CENTER);
		cashJLabel = new JLabel("应找回金额  :  ",JLabel.CENTER);
		
		cpay = new JLabel();
		tpay = new JLabel();
		cash = new JLabel();
		
		cpay.setText("￥"+cpayString);
		tpay.setText("￥"+tpString);
		cash.setText("￥"+cashString);
		bottomUpPanel.add(cpayJLabel);
		bottomUpPanel.add(cpay);
		bottomUpPanel.add(tpayJLabel);
		bottomUpPanel.add(tpay);
		bottomUpPanel.add(cashJLabel);
		bottomUpPanel.add(cash);
	}
	
	public void initBottomCenPanel() {
		bottomCenPanel = new JPanel(new GridLayout(2,1));
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time1 = df.format(date);
		tip = new JLabel("欢迎您再次光顾本店，祝您生活愉快，本小票为在本店的购物凭证");
		time = new JLabel(time1,JLabel.CENTER);
		bottomCenPanel.add(tip);
		bottomCenPanel.add(time);
	}
	
	public void initButtomDownPanel() {
		bottomDownPanel = new JPanel();
		button = new JButton("  确定   ");
		button.addMouseListener(this);
		bottomDownPanel.add(button);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == button) {
			
			try {
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time = df.format(date);
				(cashRegister.arrayList).add(time);
			} catch (Exception ae) {
				ae.printStackTrace();
			}
			try {
			cashRegister.refreshCenterRightPanel();
			}catch (Exception ep) {
				// TODO: handle exception
				System.out.println("错了错了");
				ep.printStackTrace();
			}
			String tpString = String.valueOf(cashRegister.total);
			(cashRegister.tpay).setText(tpString);
			
			BaseDaoImpl base = new BaseDaoImpl();
			base.refreshCar();
			(cashRegister.arrayList).removeAll(cashRegister.arrayList);
			this.setVisible(false);
			JOptionPane.showMessageDialog(null, "结账成功 ^_^ ,请下一位顾客结账");
			cashRegister.refreshCenterRightPanel();
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
