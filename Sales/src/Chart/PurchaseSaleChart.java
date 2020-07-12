package Chart;

import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dao.Impl.BaseDaoImpl;
import framework.AllFont;
import framework.BaseTableModule;
import framework.Tools;

public class PurchaseSaleChart {

	public JPanel backgroundPanel;
	JPanel centerPanel , bottomPanel;
	JTable table;
	JLabel label;
	float total = 0;
	public PurchaseSaleChart() {
		// TODO Auto-generated constructor stub
		initBackground();
	}
	
	public void initBackground() {
		backgroundPanel = new JPanel(new BorderLayout());
		initTablePanel();
		initBottomPanel();
		backgroundPanel.add(centerPanel,"Center");
		backgroundPanel.add(bottomPanel,"South");
	}
	
	public void initTablePanel() {
		centerPanel = new JPanel();
		String columnName[] = {"商品编号","商品名称","成本价","销售价","进货数量","总的成本价"};
		Vector<Vector> vec = getData();		
		BaseTableModule module = new BaseTableModule(columnName, vec);
		table = new JTable(module);
		Tools.setTableStyle(table);
		
		JScrollPane sroll = new JScrollPane(table);
		Tools.setJspStyle(sroll);
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setOpaque(false);

		centerPanel.add(sroll);
	}
	
	public void initBottomPanel() {
		bottomPanel = new JPanel();
		label = new JLabel("商品进货总金额为："+total,JLabel.CENTER);
		label.setFont(AllFont.cutiFont);
		bottomPanel.add(label);
	}
	
	public Vector<Vector> getData() {
		int ma = 0 ;
		int pa = 0 ;
		total = 0;
		Vector<Vector> vector = new Vector<Vector>();
		Vector<Vector> vector1 = new Vector<Vector>();
	    BaseDaoImpl base = new BaseDaoImpl();
		try {
			String sql = "select * from warehousing";
			List<Object[]> list = base.select(sql, 7, null);
			if (!list.isEmpty()) {
				for (Object[] object : list) {
					Vector v = new Vector();
					int id = (Integer)object[1];
					String str = (String)object[2];
					int quantity = (Integer)object[3];
					float buying = (Float)object[4];
					float saleprice = (Float)object[5];
					
					total += buying*quantity;
					v.add(id);
					v.add(str);
					v.add(buying);
					v.add(saleprice);
					v.add(quantity);
					v.add(buying*quantity);
					vector1.add(v);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		return vector1;
	}
}
