package CashView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.Impl.BaseDaoImpl;

public class AddGoods extends JFrame implements MouseListener{

	JPanel backgroundPanel ,centerPanel , bottomPanel ;
	JTextField name,saleprice,quanlity,id;
	JLabel l_name,l_saleprice,l_quanlity, l_id;
	CashRegister cashRegister;
	JButton button ;
	JTable table;
	JTable table1;
	int selectRow;
	
	public AddGoods(CashRegister cashRegister , JTable table , JTable table1 , int selectRow) {
		// TODO Auto-generated constructor stub
		this.cashRegister = cashRegister ;
		this.table = table ;
		this.table1 = table1;
		this.selectRow = selectRow;
		initBackgroundPanel();

		this.add(backgroundPanel);

		this.setTitle("添加商品");
		this.setSize(500, 250);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public void initBackgroundPanel() {
	 initCenterPanel();
	 initBottomPanel();
	 
	 backgroundPanel = new JPanel(new BorderLayout());
	 backgroundPanel.add(centerPanel,"Center");
	 backgroundPanel.add(bottomPanel,"South");
	}
	
	public void initCenterPanel() {
		centerPanel = new JPanel(new GridLayout(4,1));
		 
		 l_id = new JLabel("商品编号",JLabel.CENTER);
		l_name = new JLabel("商品名称",JLabel.CENTER);
		l_saleprice = new JLabel("商品价格",JLabel.CENTER);
		l_quanlity = new JLabel("输入商品数量",JLabel.CENTER);
		
		 int i_id = (Integer) table.getValueAt(selectRow, 0);
		 String idString =String.valueOf(i_id);
		 String str = (String) table.getValueAt(selectRow, 1);
		 Float string = (Float) table.getValueAt(selectRow, 2);
		 String  string2 =String.valueOf(string);
		 
		   id = new JTextField(idString);
		   id.setEditable(false);
		   name =new JTextField(str);
		   name.setEditable(false);
		   saleprice = new JTextField(string2);
		   saleprice.setEditable(false);
		   quanlity = new JTextField(15);
		
		centerPanel.add(l_id);
		centerPanel.add(id);
		centerPanel.add(l_name);
		centerPanel.add(name);
		centerPanel.add(l_saleprice);
		centerPanel.add(saleprice);
		centerPanel.add(l_quanlity);
		centerPanel.add(quanlity);
	}
	
	public void initBottomPanel() {
		bottomPanel = new JPanel();
		button = new JButton("  确定   ");
		button.addMouseListener(this);
		bottomPanel.add(button);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == button) {
			
			String string4 = id.getText();
			int id_i = Integer.valueOf(string4);
			String string = name.getText();
			String string2 = saleprice.getText();
			Float saleprice = Float.valueOf(string2);
			String string3 = quanlity.getText();
			int quanlity = Integer.valueOf(string3);
			
			BaseDaoImpl base = new BaseDaoImpl();
			Vector vector = new Vector<Vector>();
			try {
				 
				String sql = "insert into buying(saleid,name,saleprice,quantity) values('"+ id_i+"','"+ string +"','"+ saleprice +"','"+ quanlity +"')";
				int result = base.insert(sql , 0);
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
			String or_price = String.valueOf(cashRegister.original_total);
			(cashRegister.tpay).setText(tpString);
			(cashRegister.ori_price).setText(or_price);
			this.setVisible(false);
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
