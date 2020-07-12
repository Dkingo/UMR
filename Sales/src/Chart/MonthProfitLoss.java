package Chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.Impl.BaseDaoImpl;
import framework.AllFont;
import framework.BaseTableModule;
import framework.Tools;

public class MonthProfitLoss extends JPanel implements ActionListener,MouseListener{
	
	public JPanel backgroundPanel;
	JPanel centerPanel , bottomPanel , topPanel , topRightPanel;
	JTable table ;
	JTextField date_field  = new JTextField("",10);
	JLabel label , label2 ,  date_label;
	JButton b_check;
	DecimalFormat df = new DecimalFormat("0.0");
	float profit = 0 ;
	
	public MonthProfitLoss() {
		// TODO Auto-generated constructor stub
		initBackground();
	}
	
	public void initBackground() {
		backgroundPanel = new JPanel(new BorderLayout());
		initTablePanel();
		initBottomPanel();
		initTopPanel();
		backgroundPanel.add(topPanel,"North");
		backgroundPanel.add(centerPanel,"Center");
		backgroundPanel.add(bottomPanel,"South");
	}
	
	public void initTopPanel() {
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		initTopRightPanel();
		topPanel.add(topRightPanel,"East");
		
	}
	
	public void initTopRightPanel() {
		topRightPanel = new JPanel();
		date_label = new JLabel("年-月份");
		b_check = new JButton("查询");
		b_check.addMouseListener(this);
		topRightPanel.add(date_label);
		topRightPanel.add(date_field);
		topRightPanel.add(b_check);
		
	}
	
	public void initTablePanel() {
        centerPanel = new JPanel();
        String columnName[] = {"商品编号","商品名称","成本价","销售价","销售数量","销售金额","商品盈利"};
        Vector<Vector> vector1 = getData();

				
		BaseTableModule module = new BaseTableModule(columnName, vector1);
		table = new JTable(module) {
			public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {  
		        int modelRow = convertRowIndexToModel(row);   //行
		        int modelColumn = convertColumnIndexToModel(column);  //列
		        java.awt.Component comp = super.prepareRenderer(renderer, row, column);  
		        int count = 0, cnt = -1;
		        comp.setBackground(new Color(230,230,230));
		        for (int i = 0; i < table.getRowCount(); i++) {
		        	float profit = Float.parseFloat(String.valueOf(table.getValueAt(i, 6)));
		        		if (profit < 0) {
		        			cnt = count;
		        		}
		        		count++;
		        		if (modelColumn == 6 && modelRow == cnt)          { //此处加入条件判断
		        			comp.setBackground(java.awt.Color.RED);
		        		}        
		        	}
		        table.setSelectionForeground(new Color(51, 153, 255));
		        return comp;
		    }
		};
		Tools.setTableStyle(table);
		
		JScrollPane sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setOpaque(false);

		centerPanel.add(sroll);
		
	}
	
	public void initBottomPanel() {
		bottomPanel = new JPanel();
		label = new JLabel("商场盈利为："+df.format(profit) + "元",JLabel.CENTER);
		label.setFont(AllFont.cutiFont);
        bottomPanel.add(label);
	}
	
	public void refreshTablePAnel() {
		 centerPanel.removeAll();
		 String columnName[] = {"商品编号","商品名称","成本价","销售价","销售数量","销售金额","商品盈利"};
	     Vector<Vector> vector1 = getData();
	     
	     BaseTableModule module = new BaseTableModule(columnName, vector1);
			table = new JTable(module) {
				public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {  
			        int modelRow = convertRowIndexToModel(row);   //行
			        int modelColumn = convertColumnIndexToModel(column);  //列
			        java.awt.Component comp = super.prepareRenderer(renderer, row, column);  
			        int count = 0, cnt = -1;
			        comp.setBackground(new Color(230,230,230));
			        for (int i = 0; i < table.getRowCount(); i++) {
			        	float profit = Float.parseFloat(String.valueOf(table.getValueAt(i, 6)));
			        		if (profit < 0) {
			        			cnt = count;
			        		}
			        		count++;
			        		if (modelColumn == 6 && modelRow == cnt)          { //此处加入条件判断
			        			comp.setBackground(java.awt.Color.RED);
			        		}        
			        	}
			        return comp;
			    }
			};
			Tools.setTableStyle(table);
			
			JScrollPane sroll = new JScrollPane(table);
			Tools.setJspStyle(sroll);
			centerPanel = new JPanel(new BorderLayout());
			centerPanel.setOpaque(false);

			centerPanel.add(sroll);
			label.setText("商场盈利为："+df.format(profit) + "元");
			backgroundPanel.add(centerPanel);
	}
	
	public Vector<Vector> getData() {
		int ma = 0 ;
		int pa = 0 ;
		profit = 0;
		String yearMonth="";
		Vector<Vector> vector = new Vector<Vector>();
		Vector<Vector> vector1 = new Vector<Vector>();
	    BaseDaoImpl base = new BaseDaoImpl();
		try {
			String sql = "select * from commodityinformation";
			List<Object[]> list = base.select(sql, 8, null);
			if (!list.isEmpty()) {
				for (Object[] object : list) {
					Vector v = new Vector();
					int id = (Integer)object[0];
					String str = (String)object[1];

					float buying = (Float)object[6];
					float saleprice = (Float)object[7];
					
					v.add(id);
					v.add(str);
					v.add(buying);
					v.add(saleprice);
					vector.add(v);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		

		 String date = date_field.getText();
		 System.out.print(" date"+"  "+date);
		 
		for( Vector vect: vector ) {
			int count = 0 ;
			float total = 0 ;
			int id = (Integer)vect.get(0);
			float buying = (Float)vect.get(2);
			Vector vec = new Vector();
			BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
			try {
				String sql = "select * from outofstock where id='"+ id+"'";
				List<Object[]> list = baseDaoImpl.select(sql, 7, null);
			if( date.equals("") ) {
				if (!list.isEmpty()) {
					for (Object[] object : list) {
						float price = (Float)object[4];
						int q = (Integer) object[5];
						count+=q;
						total +=price*q; 
					}
					profit+=(total-buying*count);
					float p = total - buying*count;
				   vec.add(vect.get(0));
				   vec.add(vect.get(1));
				   vec.add(vect.get(2));
				   vec.add(vect.get(3));
				   vec.add(count);
				   vec.add(total);
				   vec.add(p);
				   vector1.add(vec);
								
			 	}
				
			 }else {
				 if (!list.isEmpty()) {
						for (Object[] object : list) {
							float price = (Float)object[4];
							int q = (Integer) object[5];
							yearMonth = String.valueOf(object[6]);
							 if( yearMonth.contains(date)  ) {
								 count+=q;
								 total +=price*q; 								 
							 }
						}
						profit+=(total-buying*count);
						float p = total - buying*count;
					   vec.add(vect.get(0));
					   vec.add(vect.get(1));
					   vec.add(vect.get(2));
					   vec.add(vect.get(3));
					   vec.add(count);
					   vec.add(total);
					   vec.add(p);
					   vector1.add(vec);
			   }
			 }

			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} try {
				
				int qu = 0 ;
				BaseDaoImpl baseDaoImpl1 = new BaseDaoImpl();
				String sqlString = "select * from wastage where id='"+id+"'";
				List<Object[]> list1 = baseDaoImpl1.select(sqlString, 6, null);
				if( date.equals("") ) {
				if (!list1.isEmpty()) {
					for (Object[] object : list1) {						
						qu+=(Integer)object[3];
						
					}
					profit = profit - buying*qu;
				}
			  }else {
				  if (!list1.isEmpty()) {
						for (Object[] object : list1) {	
							yearMonth = String.valueOf(object[5]);
							if(yearMonth.contains(date)) {
							qu+=(Integer)object[3];
							}
						}
						profit = profit - buying*qu;
					}
			  }
			} catch (Exception e) {
				// TODO: handle exception
				
				e.printStackTrace();
			}
		}
		return vector1;
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == b_check ) {
			refreshTablePAnel();
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
