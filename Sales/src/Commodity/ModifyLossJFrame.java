package Commodity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import dao.Impl.BaseDaoImpl;
import framework.AllFont;

public class ModifyLossJFrame extends JFrame implements MouseListener {

	JPanel backgroundPanel, labelPanel, buttonPanel, contentPanel;
	JLabel label_name, label_id, label_num, label_describe;
	JTextField name, num, describe, id;
	JButton button_save;
	
	// 获得屏幕的大小
	final static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	// 父面板对象
	GoodsLoss paraentPanel;
	
	// 表格对象
	JTable table;
	int selectedRow;
	GoodsBasedata parentPanel;
	
	int num_pre;
	
	public ModifyLossJFrame(GoodsLoss paraentPanel, JTable table, int selectedRow) {
		this.paraentPanel = paraentPanel;
		this.table = table;
		this.selectedRow = selectedRow;
		initBackgroundPanel();

		this.add(backgroundPanel);

		this.setTitle("修改商品损耗数量");
		this.setSize(480, 270);
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
		button_save = new JButton("保存修改");
		
		button_save.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_save.setForeground(Color.white);
		button_save.setFont(AllFont.Static);
		button_save.addMouseListener(this);
		buttonPanel.add(button_save);
		
	}



	private void initcontentPanel() {
		contentPanel = new JPanel(new GridLayout(4, 2));
		
		label_id = new JLabel("商品编号", JLabel.CENTER);
		label_name = new JLabel("商品名称", JLabel.CENTER);
		label_num = new JLabel("损耗数量", JLabel.CENTER);
		label_describe = new JLabel("损耗原因", JLabel.CENTER);
		
		id = new JTextField(String.valueOf(table.getValueAt(selectedRow, 0)));
		name = new JTextField(String.valueOf(table.getValueAt(selectedRow, 2)));
		num = new JTextField(String.valueOf(table.getValueAt(selectedRow, 3)));
		describe = new JTextField(String.valueOf(table.getValueAt(selectedRow, 4)));
		String num_pre_String = String.valueOf(table.getValueAt(selectedRow, 3));
		num_pre = Integer.parseInt(num_pre_String);
		
		id.setEditable(false);
		name.setEditable(false);
		
		contentPanel.add(label_id);
		contentPanel.add(id);
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
			String id_String = id.getText().trim();
			String name_String = name.getText().trim();
			String num_String = num.getText().trim();
			String describe_String = describe.getText().trim();
			
			int id_int = Integer.parseInt(id_String);
			int num_int = Integer.parseInt(num_String);
			
			if (num_String.isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入损耗数量");
			} else if (describe_String.isEmpty()){
				JOptionPane.showMessageDialog(null, "请输入损耗原因");
			} else {
				BaseDaoImpl dao1_update = new BaseDaoImpl();
				BaseDaoImpl dao2_update = new BaseDaoImpl();
				String no_String = String.valueOf(table.getValueAt(selectedRow, 1));
				int no_int = Integer.parseInt(no_String);
				String sql1 = "update wastage set loss = ?, loss_describe = ? where no = ?";
				String sql2 = "update commodityinformation set measurement = measurement - ? where id = ?";
				Object[] params1 = {num_int, describe_String, no_int};
				num_int -= num_pre;
				Object[] params2 = {num_int, id_int};
				try {
					int result1 = dao1_update.update(sql1, params1);
					int result2 = dao2_update.update(sql2, params2);
					
					if (result1 > 0 && result2 > 0) {
						JOptionPane.showMessageDialog(null, "更新商品损耗成功!");
						paraentPanel.refreshTanlePanel();
						this.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null, "更新商品损耗失败!");
					}
					
				}catch (Exception e1) {
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
