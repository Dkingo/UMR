package Chart;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.Impl.BaseDaoImpl;
import framework.BaseTableModule;
import framework.Tools;

public class StatisticalChart extends JPanel implements ActionListener,MouseListener, FocusListener{

	public JPanel backgroundPanel;
	JPanel topPanel , centerPanel , topRightPanel ,centerLeftPanel , centerRightPanel;
	JButton button_1 , button_2 ;
	JTextField textField = new JTextField(15);
	JTextField sale_text = new JTextField("0",10);
	JTable table , table1;
	JLabel label_time, label_tm;
	Vector<Vector> cvector = new Vector<Vector>();
	Vector<Vector> cvector2 = new Vector<Vector>();
	
	public StatisticalChart() {
		// TODO Auto-generated constructor stub
		initBackground();
	}
	
	public void initBackground() {
		backgroundPanel = new JPanel(new BorderLayout());
		initCenterPanel();
		initTopPanel();
		
		backgroundPanel.add(topPanel,"North");
		backgroundPanel.add(centerPanel,"Center");
	}
	
	public void initTopPanel() {
		topPanel = new JPanel(new BorderLayout());
		initTopRightPanel();
		
		topPanel.add(topRightPanel,"East");
	}
	
	public void initCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		initCenterLeftPanel();
		initCenterRightPanel();
		centerPanel.add(centerLeftPanel,"Center");
		centerPanel.add(centerRightPanel,"East");
	}
	
	public void initCenterLeftPanel() {
		centerLeftPanel = new JPanel();
		String columnName[] = {"商品名称","销售数量","销售金额"};
		Vector<Vector> rows =getData();
				
		BaseTableModule module = new BaseTableModule(columnName, rows);
		table = new JTable(module);
		Tools.setTableStyle(table);
		
		JScrollPane sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		centerLeftPanel = new JPanel(new BorderLayout());
		centerLeftPanel.setOpaque(false);

		centerLeftPanel.add(sroll);
	}
	
	public void initCenterRightPanel() {
		centerRightPanel = new JPanel();
		String columnName[] = {"商品名称","销售数量","销售金额"};
		Vector<Vector> vec = new Vector<Vector>();		
		BaseTableModule module = new BaseTableModule(columnName, vec);
		table1 = new JTable(module);
		Tools.setTableStyle(table1);
		
		JScrollPane sroll = new JScrollPane(table1);
		Tools.setJspStyle(sroll);
		centerRightPanel = new JPanel(new BorderLayout());
		centerRightPanel.setOpaque(false);

		centerRightPanel.add(sroll);
	}
	
	public void refreshCenterRightPanel() {
		centerPanel.remove(centerRightPanel);
		centerRightPanel = new JPanel();
		String columnName[] = {"商品名称","销售数量","销售金额"};
		cvector.removeAll(cvector);
		getData();
				
		BaseTableModule module = new BaseTableModule(columnName, cvector);
		table1 = new JTable(module);
		Tools.setTableStyle(table1);
		
		JScrollPane sroll = new JScrollPane(table1);
		Tools.setJspStyle(sroll);
		centerRightPanel = new JPanel(new BorderLayout());
		centerRightPanel.setOpaque(false);

		centerRightPanel.add(sroll);
		centerPanel.add(centerRightPanel,"East");
		centerPanel.invalidate();
	}
	
	public void refreshCenterRightPanel0() {
		centerPanel.remove(centerRightPanel);
		centerRightPanel = new JPanel();
		String columnName[] = {"商品名称","销售数量","销售金额"};
		cvector2.removeAll(cvector2);
		getData();
				
		BaseTableModule module = new BaseTableModule(columnName, cvector2);
		table1 = new JTable(module);
		Tools.setTableStyle(table1);
		
		JScrollPane sroll = new JScrollPane(table1);
		Tools.setJspStyle(sroll);
		centerRightPanel = new JPanel(new BorderLayout());
		centerRightPanel.setOpaque(false);

		centerRightPanel.add(sroll);
		centerPanel.add(centerRightPanel,"East");
		centerPanel.invalidate();
	}
	
	public void initTopRightPanel() {
		textField.setText("年-月-日");
		textField.addFocusListener(this);
		sale_text.setText("0");
		sale_text.addFocusListener(this);
		label_time = new JLabel("时间");
		label_tm = new JLabel("数量或金额");
		button_1 = new JButton("热门销售数量");
		button_1.addMouseListener(this);
		button_2 = new JButton("销售金额");
		button_2.addMouseListener(this);
		//textField = new JTextField(10);
		topRightPanel = new JPanel();
		
		topRightPanel.add(label_tm);
		topRightPanel.add(sale_text);
		topRightPanel.add(label_time);
		topRightPanel.add(textField);
		topRightPanel.add(button_1);
		topRightPanel.add(button_2);
	}
	
	public Vector<Vector> getData() {
		int ma = 0 ;
		int pa = 0 ;
		String yearMonth="";
		Set<String> hashSet;
		Vector<Vector> vector = new Vector<Vector>();
	    hashSet = new HashSet<String>();
	 
	    BaseDaoImpl base = new BaseDaoImpl();
		try {
			String sql = "select * from commodityinformation";
			List<Object[]> list = base.select(sql, 8, null);
			if (!list.isEmpty()) {
				for (Object[] object : list) {
					String str = (String)object[1];
					hashSet.add(str);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		 String date = textField.getText();
		 System.out.print(" date"+"  "+date);
		for( String string: hashSet ) {
			int count = 0 ;
			int datecount = 0 ;
			float datetotal = 0 ;
			float total = 0 ;
			
			Vector vec = new Vector();
			Vector rightVector = new Vector(); 
			Vector rightVector2 = new Vector(); 
			BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
			try {
				String sql = "select * from outofstock where name='"+ string+"'";
				List<Object[]> list = baseDaoImpl.select(sql, 7, null);
			if( date.equals("年-月-日") ) {
				if (!list.isEmpty()) {
					for (Object[] object : list) {
						float price = (Float)object[4];
						int q = (Integer) object[5];
						count+=q;
						total +=price*q; 
						
					}
		       vec.add(string);
		       vec.add(count);
		       vec.add(total);
		       vector.add(vec);
		     
		       
		       if( count >=  Integer.valueOf(sale_text.getText()) ) {
		        rightVector.add(string);
		        rightVector.add(count);
		        rightVector.add(total);
		        cvector.add(rightVector);
		       }
		     
		       if( total >  Integer.valueOf(sale_text.getText()) ) {
		        rightVector2.add(string);
		        rightVector2.add(count);
		        rightVector2.add(total);
		        cvector2.add(rightVector2);
		       }
		       
		    }
		   }else {
			   if (!list.isEmpty()) {
					for (Object[] object : list) {
						float price = (Float)object[4];
						int q = (Integer) object[5];
						yearMonth = String.valueOf(object[6]);
						count+=q;
						total +=price*q;
						 if( yearMonth.contains(date)  ) {
							 datecount += q ;
							 datetotal += q*price ;
							 
						 }
					}

				       vec.add(string);
				       vec.add(count);
				       vec.add(total);
				       vector.add(vec);		     
				  
				 System.out.print(" date"+yearMonth+"  "+date);
				 
				       if( datecount >=  Integer.parseInt(sale_text.getText()) ) {
				        rightVector.add(string);
				        rightVector.add(datecount);
				        rightVector.add(datetotal);
				        cvector.add(rightVector);
				       }
				     
				       if( datetotal > Float.valueOf(sale_text.getText()) ) {
				        rightVector2.add(string);
				        rightVector2.add(datecount);
				        rightVector2.add(datetotal);
				        cvector2.add(rightVector2);
				       }
			    }
		   }
		  }catch (Exception e) {
		    // TODO: handle exception
		    e.printStackTrace();
		   }
		  }
		  return vector;
		 }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == button_1 ) {
			for( Object object: cvector ) {
				System.out.println(object);
			}
			sale_text.getText();
			refreshCenterRightPanel();

		}else if( e.getSource() == button_2 ) {
			for( Object object: cvector2 ) {
				System.out.println(object);
			}
			
			refreshCenterRightPanel0();
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

	//聚焦事件
	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == textField) {
			if (textField.getText().equals("年-月-日")) {
				textField.setText("");
			}
		} else if (e.getSource() == sale_text) {
			if (sale_text.getText().equals("0"))
			sale_text.setText("");
		}
		
	}

	//失焦事件
	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == textField) {
			if (textField.getText().equals("")) {
				textField.setText("年-月-日");
			}
		} else if (e.getSource() == sale_text) {
			if (sale_text.getText().equals("")) {
				sale_text.setText("0");
			}
		}
		
	}

}
