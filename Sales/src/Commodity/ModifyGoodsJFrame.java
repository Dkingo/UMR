package Commodity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import dao.Impl.BaseDaoImpl;
import framework.AllFont;

public class ModifyGoodsJFrame extends JFrame implements MouseListener{

	// 定义全局组件
	JPanel backgroundPanel, labelPanel, contentPanel, buttonPanel;
	JLabel label_id, label_name, label_stock, label_sellprice, label_primecost, label_origin, label_category, label_describe;
	JTextField id, name, stock, sellprice, primecost, origin, describe;
	JButton button_modify;
	JComboBox category;
	
	// 获得屏幕的大小
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	// 表格对象
	JTable table;
	int selectedRow;
	GoodsBasedata parentPanel;
	
	public ModifyGoodsJFrame(GoodsBasedata parentPanel, JTable table, int selectedRow) {
		this.table = table;
		this.selectedRow = selectedRow;
		this.parentPanel = parentPanel;
		
		initBackgroundPanel();

		this.add(backgroundPanel);

		this.setTitle("修改商品");
		this.setSize(640, 360);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
	}

	private void initBackgroundPanel() {
		backgroundPanel = new JPanel(new BorderLayout());
		
		initcontentPanel();
		initbuttonPanel();
		initLabelPanel();
		
		backgroundPanel.add(labelPanel, "North");
		backgroundPanel.add(buttonPanel, "South");
		backgroundPanel.add(contentPanel, "Center");
		
	}

	private void initLabelPanel() {
		labelPanel = new JPanel();
		JLabel title = new JLabel("商品信息");
		title.setFont(AllFont.Static);
		labelPanel.add(title);
		
	}

	private void initbuttonPanel() {
		buttonPanel = new JPanel();
		button_modify = new JButton("保存修改");
		
		button_modify.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_modify.setForeground(Color.white);
		button_modify.setFont(AllFont.Static);
		button_modify.addMouseListener(this);
		buttonPanel.add(button_modify);
		
	}

	private void initcontentPanel() {
		contentPanel = new JPanel(new GridLayout(8, 2));
		
		label_id = new JLabel("商品编号", JLabel.CENTER); 
		label_name = new JLabel("商品名称", JLabel.CENTER);
		label_category = new JLabel("商品类别", JLabel.CENTER);
		label_stock = new JLabel("商品数量", JLabel.CENTER);
		label_origin = new JLabel("生产厂商", JLabel.CENTER);
		label_describe = new JLabel("商品描述", JLabel.CENTER);
		label_primecost = new JLabel("进货价", JLabel.CENTER);
		label_sellprice = new JLabel("销售价", JLabel.CENTER);
			
		id = new JTextField(String.valueOf(table.getValueAt(selectedRow, 0)));
		name = new JTextField(String.valueOf(table.getValueAt(selectedRow, 1)));
		stock = new JTextField(String.valueOf(table.getValueAt(selectedRow, 2)));
		origin = new JTextField(String.valueOf(table.getValueAt(selectedRow, 5)));
		describe = new JTextField(String.valueOf(table.getValueAt(selectedRow, 4)));
		sellprice = new JTextField(String.valueOf(table.getValueAt(selectedRow, 7)));
		primecost = new JTextField(String.valueOf(table.getValueAt(selectedRow, 6)));
		String category_default = String.valueOf(table.getValueAt(selectedRow, 3));
		
		//设置商品编号不可修改
		id.setEditable(false);
		
		//商品种类下拉框 
		category = new JComboBox();
		category.addItem("零食");
		category.addItem("饮品");
		category.addItem("日用品");
		category.addItem("文具");
		category.setSelectedItem(category_default);
		
		contentPanel.add(label_id);
		contentPanel.add(id);
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
		if (e.getSource() == button_modify) {
			String id_String = id.getText().trim();
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
				int result = 0;
				double primecost_double = Double.valueOf(primecost_String);
				double sellprice_double = Double.valueOf(sellprice_String);
				double stock_double = Double.valueOf(stock_String);
				int id_int = Integer.valueOf(id_String);
				String category_String = (String) category.getSelectedItem();
				Object[] params = {name_String, stock_double, category_String, describe_String,
						origin_String, primecost_double, sellprice_double, id_int};
				
				try {
					BaseDaoImpl dao = new BaseDaoImpl();
					String sql = "UPDATE commodityinformation set name=?, measurement=?, category=?, "
							+ "sdescribe=?, manufacturer=?, buyingprice=?, saleprice=? where id = ?";
					result = dao.update(sql, params);
					if (result > 0) {
						JOptionPane.showMessageDialog(null, "修改商品成功！");
						parentPanel.refreshTablePanel();
						this.setVisible(false);
					}
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "修改商品失败！");
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
