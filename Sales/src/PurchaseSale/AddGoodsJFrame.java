package PurchaseSale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import Commodity.GoodsBasedata;
import dao.Impl.BaseDaoImpl;
import framework.AllFont;
import framework.SQLservice;

public class AddGoodsJFrame extends JFrame implements MouseListener{

	JPanel backgroundPanel, labelPanel, buttonPanel, contentPanel;
	JLabel label_name, label_stock, label_sellprice, label_primecost, label_origin, label_category, label_describe;
	JTextField name, stock, sellprice, primecost, origin, describe;
	JButton button_save;
	JComboBox category;
	
	// 获得屏幕的大小
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	// 父面板对象
	Warehousing paraentPanel;
	
	public AddGoodsJFrame(Warehousing paraentPanel) {

		this.paraentPanel = paraentPanel;
		initBackgroundPanel();

		this.add(backgroundPanel);

		this.setTitle("添加商品");
		this.setSize(640, 360);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	//初始化背景模板
	public void initBackgroundPanel() {
		backgroundPanel = new JPanel(new BorderLayout());
		
		initcontentPanel();
		initbuttonPanel();
		initLabelPanel();
		
		backgroundPanel.add(labelPanel, "North");
		backgroundPanel.add(buttonPanel, "South");
		backgroundPanel.add(contentPanel, "Center");
	}
	
	//初始化label面板
	private void initLabelPanel() {
		
		labelPanel = new JPanel();
		JLabel title = new JLabel("商品信息");
		title.setFont(AllFont.Static);
		labelPanel.add(title);
		
	}

	//设置按钮面板
	private void initbuttonPanel() {
		buttonPanel = new JPanel();
		button_save = new JButton("保存");
		
		button_save.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_save.setForeground(Color.white);
		button_save.setFont(AllFont.Static);
		button_save.addMouseListener(this);
		buttonPanel.add(button_save);
	}
	
	//商品信息面板
	private void initcontentPanel() {
		contentPanel = new JPanel(new GridLayout(7, 2));
		
		label_name = new JLabel("商品名称", JLabel.CENTER);
		label_category = new JLabel("商品类别", JLabel.CENTER);
		label_stock = new JLabel("商品数量", JLabel.CENTER);
		label_origin = new JLabel("生产厂商", JLabel.CENTER);
		label_describe = new JLabel("商品描述", JLabel.CENTER);
		label_primecost = new JLabel("进货价", JLabel.CENTER);
		label_sellprice = new JLabel("销售价", JLabel.CENTER);
		
		name = new JTextField("");
		stock = new JTextField("");
		origin = new JTextField("");
		describe = new JTextField("");
		sellprice = new JTextField("");
		primecost = new JTextField("");
		
		//商品种类下拉框 
		category = new JComboBox();
		category.addItem("零食");
		category.addItem("饮品");
		category.addItem("日用品");
		category.addItem("文具");
		
		contentPanel.add(label_name);
		contentPanel.add(name);
		contentPanel.add(label_category); 
		contentPanel.add(category);
		contentPanel.add(label_stock);
		contentPanel.add(stock);
		contentPanel.add(label_origin);
		contentPanel.add(origin);
		contentPanel.add(label_describe);
		contentPanel.add(describe);
		contentPanel.add(label_primecost);
		contentPanel.add(primecost);
		contentPanel.add(label_sellprice);
		contentPanel.add(sellprice);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == button_save) {
			String name_String = name.getText().trim();
			String stock_String = stock.getText().trim();
			String origin_String = origin.getText().trim();
			String describe_String = describe.getText();
			String primecost_String = primecost.getText().trim();
			String sellprice_String = sellprice.getText().trim();
			
			if (name_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入商品名称");
			} else if (stock_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入商品数量");
			} else if(origin_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入生产厂商");
			} else if (primecost_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入成本价");
			} else if (sellprice_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入销售价");
			} else {
				
				double primecost_double = Double.valueOf(primecost_String);
				double sellprice_double = Double.valueOf(sellprice_String);
				double stock_double = Double.valueOf(stock_String);
				String category_String = (String) category.getSelectedItem();
				
				Date date=new Date(); 
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
				String time = df.format(date);
				int result_comm = 0, result_in = 0;
				
				try {
					BaseDaoImpl dao_comm = new BaseDaoImpl();
					BaseDaoImpl dao_in = new BaseDaoImpl();
					SQLservice dao_contain = new SQLservice();
					SQLservice dao_contain1 = new SQLservice();
					BaseDaoImpl dao_updatecomm = new BaseDaoImpl();
					BaseDaoImpl dao_insertware = new BaseDaoImpl();
					
					//通过名称，进货价， 生产厂商查询商品信息表该商品
					Vector<Object[]> ve = dao_contain.selectOne(name_String,primecost_double, origin_String, 8);
					
					if (ve.size() != 0) { //商品信息有该商品
						int resultcomm = 0;
						
						//获取商品id
						Object[] object = ve.get(0);
						int id_int = (int) object[0];
						
						//更新该商品在商品信息表的数据
						Object[] params_update = {stock_double, id_int};
						String sql_updatecomm = "update commodityinformation set measurement = measurement + ?"
								+ "where id = ?";
						resultcomm = dao_updatecomm.update(sql_updatecomm, params_update);
						
						//商品插入入库表
						Object[] params_ware = {id_int, name_String, stock_double, primecost_double, sellprice_double, time};
						String sql_warehouse = "insert into warehousing (id, name, input, buyingprice, sellprice, date) "
								+ "values (?,?,?,?,?,?)";
						int result_ware = dao_insertware.insert(sql_warehouse, params_ware);
						
						if (resultcomm > 0 && result_ware > 0) {
							JOptionPane.showMessageDialog(null, "添加商品成功！");
							paraentPanel.refreshTablePanel();
							this.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(null, "添加商品入库或更新商品信息失败");
						}
					} else {  //商品信息表没有该商品
						
						//添加商品到商品信息表
						Object[] params_comm = {name_String, stock_double, category_String, describe_String,
								origin_String, primecost_double, sellprice_double};
						String sql_comm = "INSERT INTO commodityinformation (name, measurement, category,sdescribe,manufacturer,"
								+ "buyingprice, saleprice)VALUES (?,?,?,?,?,?,?)";
						result_comm = dao_in.insert(sql_comm, params_comm);

						//通过名称，进货价， 生产厂商查询商品信息表该商品
						Vector<Object[]> ve1 = dao_contain1.selectOne(name_String,primecost_double, origin_String, 8);
						
						//获取商品id
						Object[] object1 = ve1.get(0);
 					    int id_int1 = (int) object1[0];
						
						//添加商品入库
						Object[] params_in = {id_int1,name_String, stock_double, primecost_double, sellprice_double, time};
						String sql_in = "INSERT INTO warehousing (id, name, input, buyingprice, sellprice, date) VALUES (?,?,?,?,?,?)";
						result_in = dao_comm.insert(sql_in, params_in);
						
						if ((result_comm > 0) && (result_in > 0)) {
							JOptionPane.showMessageDialog(null, "添加商品成功！");
							paraentPanel.refreshTablePanel();
							this.setVisible(false);
							
						}		
					}
					
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "添加商品入库或更新商品信息失败");
					this.setVisible(false);
					e1.printStackTrace();
				}
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
