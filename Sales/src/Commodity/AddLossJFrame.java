package Commodity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import dao.Impl.BaseDaoImpl;
import framework.AllFont;
import jdk.nashorn.internal.scripts.JO;

public class AddLossJFrame extends JFrame implements MouseListener, ActionListener{

	JPanel backgroundPanel, labelPanel, buttonPanel, contentPanel;
	JLabel label_name, label_id, label_num, label_describe;
	JTextField name, num, describe;
	JButton button_save;
	JComboBox select_id;
	
	// 获得屏幕的大小
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	// 父面板对象
	GoodsLoss paraentPanel;
	
	public AddLossJFrame(GoodsLoss paraentPanel) {
		this.paraentPanel = paraentPanel;
		initBackgroundPanel();

		this.add(backgroundPanel);

		this.setTitle("添加商品");
		this.setSize(480, 270);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
	}
	
	//初始化背景模板
	private void initBackgroundPanel() {
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
		contentPanel = new JPanel(new GridLayout(4, 2));
		
		label_id = new JLabel("商品编号", JLabel.CENTER);
		label_name = new JLabel("商品名称", JLabel.CENTER);
		label_num = new JLabel("损耗数量", JLabel.CENTER);
		label_describe = new JLabel("损耗原因", JLabel.CENTER);
		
		name = new JTextField("");
		num = new JTextField("");
		describe = new JTextField("");
		name.setEnabled(false);
		
		//商品id下拉框
		select_id = new JComboBox();
		List<Object[]> list_category = null;
		String sql_id = "select id from commodityinformation";
		try {
			BaseDaoImpl dao = new BaseDaoImpl();
			list_category = dao.select(sql_id, 1, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		select_id.addItem("请选择");
		if (list_category != null) {
			for (Object[] object : list_category) {
				select_id.addItem(object[0]);
			}
		}
		select_id.addActionListener(this);
		
		contentPanel.add(label_id);
		contentPanel.add(select_id);
		contentPanel.add(label_name);
		contentPanel.add(name);
		contentPanel.add(label_num);
		contentPanel.add(num);
		contentPanel.add(label_describe);
		contentPanel.add(describe);
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == button_save) {
			String id_String = String.valueOf(select_id.getSelectedItem());
			String name_String = name.getText().trim();
			String num_String = num.getText().trim();
			String describe_String = describe.getText().trim();
			
			if ("请选择".equals(id_String)) {
				JOptionPane.showMessageDialog(null, "请输入商品编号");
			} else if (num_String.isEmpty()){
				JOptionPane.showMessageDialog(null, "请输入损耗数量");
			} else if (describe_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入损耗原因");
			} else {
				int id_int = Integer.parseInt(id_String);
				int num_int = Integer.parseInt(num_String);
				
				Date date=new Date(); 
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
				String time = df.format(date);
				
				BaseDaoImpl dao_insert = new BaseDaoImpl();
				BaseDaoImpl dao_update = new BaseDaoImpl();
				String sql_insert = "insert into wastage (id, name, loss, loss_describe, date) values (?,?,?,?,?)";
				String sql_update = "update commodityinformation set measurement = measurement - 1 where id = ?";
				Object[] params_insert = {id_int, name_String, num_int, describe_String, time};
				Object[] params_update = {id_int};
				try {
					int result_insert = dao_insert.insert(sql_insert, params_insert);
					int result_update = dao_update.update(sql_update, params_update);
					if (result_insert > 0 && result_update > 0) {
						JOptionPane.showMessageDialog(null, "添加商品损耗成功！");
						paraentPanel.refreshTanlePanel();
						this.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null, "添加商品损耗失败！");
					}
				}catch (Exception e2) {
					e2.printStackTrace();
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

	//下拉框改变事件
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == select_id) {
			String id_String = String.valueOf(select_id.getSelectedItem());
			
			if ("请选择".equals(id_String)) {
				JOptionPane.showMessageDialog(null, "请输入商品编号");
				name.setText("");
			} else {
				int id_int = Integer.parseInt(id_String);
				BaseDaoImpl dao_select = new BaseDaoImpl();
				String sql_select = "select name from commodityinformation where id = ?";
				Object[] params_id = {id_int};
				try {
					List<Object[]> list_name = dao_select.select(sql_select, 1, params_id);
					Object[] obj = list_name.get(0);
					name.setText((String) obj[0]);
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
	}

}
