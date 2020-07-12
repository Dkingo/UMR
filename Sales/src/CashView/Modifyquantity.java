package CashView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.Impl.BaseDaoImpl;

public class Modifyquantity extends JFrame implements MouseListener{

	
	JPanel backgroundPanel ,centerPanel , bottomPanel ;
	JTextField name,saleprice,quanlity,id;
	JLabel l_name,l_saleprice,l_quanlity, l_id;
	CashRegister cashRegister;
	JButton button , button1 ;
	JTable table;
	int selectRow;
	
	public Modifyquantity(CashRegister cashRegister , JTable table  , int selectRow) {
		// TODO Auto-generated constructor stub
		this.cashRegister = cashRegister ;
		this.table = table ;
		this.selectRow = selectRow;
		initBackgroundPanel();

		this.add(backgroundPanel);

		this.setTitle("修改商品信息");
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
		
		
		 int i_id = (Integer) table.getValueAt(selectRow, 1);
		 String idString =String.valueOf(i_id);
		 String str = (String) table.getValueAt(selectRow, 2);
		 Float string = (Float) table.getValueAt(selectRow, 3);
		 String  string2 =String.valueOf(string);
		 String string3 = String.valueOf(table.getValueAt(selectRow, 4));
		 
		   id = new JTextField(idString);
		   name =new JTextField(str);
		   saleprice = new JTextField(string2);
		   quanlity = new JTextField(string3);
		   
		   id.setEditable(false);
		   name.setEditable(false);
		   saleprice.setEditable(false);
		
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
		button1 = new JButton("  取消   " );
		button.addMouseListener(this);
		button1.addMouseListener(this);
		bottomPanel.add(button);
		bottomPanel.add(button1);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == button) {
			
		
			int i_id = (Integer) table.getValueAt(selectRow, 0);
			
			String string3 = quanlity.getText();
			int quanlity = Integer.valueOf(string3);
			
			BaseDaoImpl base = new BaseDaoImpl();
			Vector vector = new Vector<Vector>();
			try {
				String sql = "update buying set quantity='"+ quanlity +"' where id='"+i_id+"'";
				int result = base.update(sql);
				
			} catch (Exception ae) {
				JOptionPane.showConfirmDialog(null, "更新buying库出错");
				ae.printStackTrace();
			}
			try {
			cashRegister.refreshCenterRightPanel();
			}catch (Exception ep) {
				// TODO: handle exception
				ep.printStackTrace();
			}
			this.setVisible(false);
		}else if( e.getSource() == button1 ) {
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
