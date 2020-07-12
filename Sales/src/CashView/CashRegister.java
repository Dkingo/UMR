package CashView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dao.Impl.BaseDaoImpl;
import framework.BaseTableModule;
import framework.Tools;

public class CashRegister extends JPanel implements ActionListener,MouseListener,DocumentListener,Runnable, FocusListener{
	
	public JPanel backgroundPanel;
	JPanel topPanel , centerPanel , topLeftPanel , topRightPanel ,  centerDownPanel , centerLeftPanel,centerRightPanel;
	JPanel centenUpPanel ;
	JPanel cenHomePanel;
	JLabel tool_add , tool_delete , tool_modify ,goods_name , goods_id , goods_quantity , customer_pay , total_pay , return_ , 
	       label1 ,label2 ,label3, change , tpay , vip , original_price , ori_price;
	JTextField name , id , quantity , cpay ,  cash ,vipField;
	JButton scan , settleaccmount , vipButton;
	JTable table ,table1;
	float total = 0 ;
	float original_total = 0;
	float a=0 ;
	int b =0;
	private Thread thread;
	private Vector<Vector> vec;
	private JRadioButton radioButton;
	public LinkedList<String> arrayList = new LinkedList<String>();

	public CashRegister() throws IOException {
		// TODO Auto-generated constructor stub
		initBackground();
	}
	
	public void initBackground() throws IOException {
		backgroundPanel = new JPanel(new BorderLayout());
		initCenterPanel();
		initTopPanel();
		
		backgroundPanel.add(topPanel,"North");
		backgroundPanel.add(centerPanel,"Center");
	}
	
	public void initTopPanel() {
		topPanel = new JPanel(new BorderLayout());
		initTopLeftPanel();
		initTopRightPanel();
		
		topPanel.add(topLeftPanel,"East");
		topPanel.add(topRightPanel,"West");
	}
	
	public void initTopLeftPanel() {
		topLeftPanel = new JPanel();
		
		Icon icon_modify = new ImageIcon("image/modify.png");
		tool_modify = new JLabel(icon_modify);
		tool_modify.setToolTipText("修改信息");
		tool_modify.addMouseListener(this);

		Icon icon_delete = new ImageIcon("image/delete.png");
		tool_delete = new JLabel(icon_delete);
		tool_delete.setToolTipText("删除该商品");
		tool_delete.addMouseListener(this);

		topLeftPanel.add(tool_modify); 
		topLeftPanel.add(tool_delete);
	}
	
	public void initTopRightPanel() {
		topRightPanel = new JPanel();
		
		name = new JTextField(15);
		name.getDocument().addDocumentListener(this);
		
		goods_name = new JLabel("输入商品名字  ");
		label1 = new JLabel("  ");
		scan = new JButton("添加进购物车");
		scan.addMouseListener(this);
		vip = new JLabel("是否为会员");
		radioButton = new JRadioButton();
		radioButton.addMouseListener(this);
		vipField = new JTextField(15);
		vipButton = new JButton("会员查询");
		vipButton.addMouseListener(this);
	
		topRightPanel.add(label1);
		topRightPanel.add(goods_name);
		topRightPanel.add(name);
		topRightPanel.add(label1);
		topRightPanel.add(scan);
		topRightPanel.add(label1);
		topRightPanel.add(vip);
		topRightPanel.add(radioButton);
        topRightPanel.add(vipField);
        topRightPanel.add(vipButton);
		
			
	}
	
	public void initCenterPanel() throws IOException {
		centerPanel = new JPanel(new BorderLayout());
		initCenterDownPanel();
		initCenterUpPanel();
		
		centerPanel.add(centenUpPanel,"North");
		centerPanel.add(centerDownPanel,"Center");
		
		
	}

	public void initCenterUpPanel() throws IOException {
		initCenterLeftPanel();
		initCenterRightPanel();
		
		centenUpPanel = new JPanel(new BorderLayout());		
		centenUpPanel.setPreferredSize(new Dimension(500,600));
	    centenUpPanel.add(centerLeftPanel,"West");
	    centenUpPanel.add(centerRightPanel,"Center");
		
	}
	
	public void initCenterLeftPanel() {
		centerLeftPanel = new  JPanel();
		String columnName[] = {"商品编号","商品名称","出售价"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			String sql = "select id,name,saleprice from commodityinformation where measurement>0";
			List<Object[]> list = base.select(sql, 3, null);
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
		
		BaseTableModule module = new BaseTableModule(columnName, rows);
		table = new JTable(module);
		Tools.setTableStyle(table);
		
		
		JScrollPane sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		centerLeftPanel = new JPanel(new BorderLayout());
		centerLeftPanel.setOpaque(false);

		centerLeftPanel.add(sroll);
		
	}
	
	public void refreshCenterLeftPanel() {
		centenUpPanel.remove(centerLeftPanel);
		String nameString = name.getText();
		String columnName[] = {"商品编号","商品名称","出售价"};
		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			rows = baseDaoImpl.selectCash(nameString, "commodityinformation", 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BaseTableModule module = new BaseTableModule(columnName, rows);
		table = new JTable(module);
		Tools.setTableStyle(table);
		
		
		JScrollPane sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		centerLeftPanel = new JPanel(new BorderLayout());
		centerLeftPanel.setOpaque(false);
		centerLeftPanel.add(sroll);
		
		centenUpPanel.add(centerLeftPanel,"West");
		centerLeftPanel.invalidate();
	}
	
	
	public void initCenterRightPanel() {
		
		centerRightPanel = new  JPanel();
		String columnName[] = {"序号","商品编号","商品名称","出售价","数量"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector rows = new Vector<Vector>();
		try {
			String sql = "select * from buying";
			List<Object[]> list = base.select(sql, 5, null);
			if (!list.isEmpty()) {
				for (Object[] object : list) {
					Vector temp = new Vector<String>();
					for (int i = 0; i < object.length; i++) {
						temp.add(object[i]);
					}
					a = (Float) object[3];
					b = (Integer) object[4];
					total+= a*b;
					rows.add(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        vec = rows;
		
		BaseTableModule module = new BaseTableModule(columnName, rows);
		table1 = new JTable(module);
		
		
		Tools.setTableStyle(table1);
		
		
		JScrollPane sroll = new JScrollPane(table1);
		Tools.setJspStyle(sroll);
		centerRightPanel = new JPanel(new BorderLayout());
		centerRightPanel.setOpaque(false);

		centerRightPanel.add(sroll);
		if(rows.isEmpty()) {
			tpay.setText("0.00");
		}else {
		    String tpString = String.valueOf(total);
		    tpay.setText(tpString);
		    String or_price = String.valueOf(original_total);
		    ori_price.setText(or_price);
		}
	}
	
	public void refreshCenterRightPanel() {
		
		//  每次修改商品数量后价格需要重置，不然就会把以前的和现在的叠加
		total=0;
		original_total = 0; 
		tpay.setText("0.00");
		ori_price.setText("0.00");
		
		
		centenUpPanel.remove(centerRightPanel);
		String columnName[] = {"序号","商品编号","商品名称","出售价","数量"};
		BaseDaoImpl base = new BaseDaoImpl();
		Vector<Vector> rows = new Vector<Vector>();
		try {
			String sql = "select * from buying";
			List<Object[]> list = base.select(sql, 5, null);
			if (!list.isEmpty()) {
				for (Object[] object : list) {
					Vector temp = new Vector<String>();
					for (int i = 0; i < object.length; i++) {
						temp.add(object[i]);
					}
					a = (Float) object[3];
					b = (Integer) object[4];
					if( radioButton.isSelected() ) {
					      total+= a*b*0.95;
					      original_total += a*b;
					}else {
						total+= a*b;
						  original_total += a*b;
					}
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
		centerRightPanel = new JPanel(new BorderLayout());
		centerRightPanel.setOpaque(false);

		centerRightPanel.add(sroll1);
		centenUpPanel.add(centerRightPanel,"Center");
	}
	
	public void initCenterDownPanel() {
		centerDownPanel = new JPanel();
		
		original_price = new JLabel("应收金额");
		customer_pay = new JLabel("顾客支付金额  ");
		total_pay = new JLabel("实际应收金额  ");
		return_ = new JLabel("应找回金额   ");
		
		cpay = new JTextField("0.00",15);
		cpay.addFocusListener(this);
		//cpay.addMouseListener(this);
		tpay = new JLabel();
		ori_price = new JLabel("0.00");
		change = new JLabel();
		thread = new Thread(this);
		thread.start();
		
		label1 = new JLabel("    ");
		label2 = new JLabel("    ");
		label3 = new JLabel("    ");
		settleaccmount = new JButton("结账");
		settleaccmount.addMouseListener(this);
		
	
		centerDownPanel.add(customer_pay);
		centerDownPanel.add(cpay);
		centerDownPanel.add(label1);
		centerDownPanel.add(original_price);
		centerDownPanel.add(ori_price);
		centerDownPanel.add(label1);
		centerDownPanel.add(total_pay);
		centerDownPanel.add(tpay);
		centerDownPanel.add(label2);
		centerDownPanel.add(return_);
		centerDownPanel.add(change);
		centerDownPanel.add(label3);
		centerDownPanel.add(settleaccmount);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == scan ) {
			int row = table.getSelectedRow();
			if( row < 0) {
				JOptionPane.showConfirmDialog(null, "请选择需要添加的商品");
			}else {
				new AddGoods(this, table, table1 ,row);
			}
		}else if( e.getSource() == settleaccmount ) {
			
		if(!(cpay.getText().equals("0.00") || cpay.getText().equals(""))) {
			try {
				BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
				String sql = "select * from buying";
				List<Object[]> list = baseDaoImpl.selectBuying(sql, 5, null);
				Object[] object_exchange = {};
				BaseDaoImpl base_exchange = new BaseDaoImpl();
				String sql_exchange = "select max(exchange) from outofstock";
				List<Object[]> exchange_list = base_exchange.select(sql_exchange,1,object_exchange);
				Object[] object_exchange1 = {};
				object_exchange1 = exchange_list.get(0);
				int exchange_1 = Integer.valueOf(String.valueOf(object_exchange1[0]));
				
				System.out.println("aa");
				int i = 0;
				if (!list.isEmpty()) {
					for (Object[] object : list) {
						
						BaseDaoImpl baseDaoImpl2 = new BaseDaoImpl();
						BaseDaoImpl baseDaoImpl3 = new BaseDaoImpl();
						
						int id =(Integer) object[1];
						String name =(String)object[2];
						float saleprice = 0;
						if( radioButton.isSelected() ) {
							saleprice = (float) (((Float)object[3])*0.95);
						}else {
							saleprice = (Float)object[3];
						}
						int sale = (Integer) object[4];
						String dateString = String.valueOf(arrayList.get(i));
						System.out.println(id+"  "+name+"  "+saleprice+"   "+sale+"   "+dateString);
						
						try {
							
							String sqlString = "insert into outofstock(id,name,exchange,saleprice,sale,date) values('"+ id +"','"+ name +"','"+(exchange_1+1) +"','"+ saleprice +"','"+sale +"','"+dateString+"')";
							 System.out.println("sssssss");
							baseDaoImpl2.insert(sqlString, 0);
						}catch (Exception ea) {
							// TODO: handle exception
							JOptionPane.showConfirmDialog(null, "插入出库单出错");
							ea.printStackTrace();
						}
						
						try {
							String sqlString2 = "update commodityinformation set measurement=measurement-'"+ sale +"' where id='"+id+"'";				
							baseDaoImpl3.update(sqlString2);
							
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showConfirmDialog(null, "插入出库单出错");
							e2.printStackTrace();
						}
						
						i++;
					}
				}			
			} catch (Exception e2) {
				// TODO: handle exception
				//JOptionPane.showConfirmDialog(null, "查询buying 单出错");
				e2.printStackTrace();
			}
			
			new Print(this);
			cpay.setText("0.00");
			}else {
				JOptionPane.showConfirmDialog(null, "请输入顾客实付金额再结账");
			}
		}else if( e.getSource() == tool_modify ) {
			int row = table1.getSelectedRow();
			if( row < 0) {
				JOptionPane.showConfirmDialog(null, "请选择需要修改数量的商品");
			}else {
				new Modifyquantity(this, table1, row);
			}
		}else if(e.getSource()==tool_delete) {
			int row = table1.getSelectedRow();
			   if( row < 0) {
			    JOptionPane.showConfirmDialog(null, "请选择需要删除商品");
			   }else {
			    int id_i = Integer.valueOf(String.valueOf(table1.getValueAt(row, 0)));
			    System.out.print(id_i);
			    BaseDaoImpl base = new BaseDaoImpl();
			    try {
			     String sql = "delete from buying where id='"+ id_i+"'";
			     int result = base.insert(sql , 0);
			     refreshCenterRightPanel();
			    }catch (Exception ae) {
			     // TODO: handle exception
			     ae.printStackTrace();
			    }
			   }
		} else if( e.getSource() == vipButton ) {
			String idString = vipField.getText();
			 BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
			 String sql = "select * from vip where vip_id='"+idString+"'";
			 try {
				 List<Object[]> list = baseDaoImpl.select(sql, 2, null);
				if( !list.isEmpty() ) {
					Object[] objects = new Object[2];
					objects = list.get(0);
					JOptionPane.showMessageDialog(null, "此用户会员卡号为"+objects[0]+"，会员名字为"+objects[1]);
				}else {
					JOptionPane.showMessageDialog(null, "没有查询到该会员");
				}
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showConfirmDialog(null, "查询vip出现问题");
				e2.printStackTrace();
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
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
		// TODO Auto-generated method stub
		refreshCenterLeftPanel();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		refreshCenterLeftPanel();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
            //没有输入的时候总价为0.00
            if (change.getText().equals("")) {
                change.setText("0.00");
                this.repaint();
            }
            try {
            	String string = cpay.getText().trim();
    			float f = Float.valueOf(string);
    				change.setText(String.valueOf(f-total));
                this.repaint();
            } catch (Exception e) {
                //System.out.println("不是数字" + e);
            }

        }

    }

	//聚焦事件
	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == cpay) {
			if (cpay.getText().equals("0.00")) {
				cpay.setText("");
			}
		}
		
	}

	//失焦事件
	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == cpay) {
			if (cpay.getText().equals("")) {
				cpay.setText("0.00");
			}
		}
		
	}

}
