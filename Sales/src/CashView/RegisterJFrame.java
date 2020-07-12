package CashView;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.Impl.BaseDaoImpl;
import jdk.nashorn.internal.scripts.JO;

public class RegisterJFrame  {

	public JPanel contentPane;
	public JPanel homeJPanel;
	private JTextField card;
	private JTextField name;
	
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public RegisterJFrame() {

		
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(235,235,235));
		
		card = new JTextField();
		card.setBounds(448, 211, 193, 32);
		contentPane.add(card);
		card.setColumns(10);
		
		name = new JTextField();
		name.setBounds(448, 277, 193, 32);
		contentPane.add(name);
		name.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u5361\u53F7");
		lblNewLabel.setBounds(379, 215, 40, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u59D3\u540D");
		lblNewLabel_1.setBounds(379, 281, 40, 25);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("\u6CE8\u518C");
		btnNewButton.addActionListener(new ActionListener() {
			//???
			public void actionPerformed(ActionEvent arg0) {
			    	String sql = "insert into vip(vip_id, vip_name) values(?, ?)";
			    	String cardnum = card.getText();
			    	String nameString  = name.getText();
					try {
						BaseDaoImpl dao = new BaseDaoImpl();
						dao.insert(sql, new String[] {cardnum,nameString});
						JOptionPane.showMessageDialog(null, "注册会员成功");
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		});
		btnNewButton.setBounds(334, 383, 98, 27);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u4FEE\u6539");
		btnNewButton_1.addActionListener(new ActionListener() {
			//???
			public void actionPerformed(ActionEvent arg0) {
				  String sql ="update vip set vip_name=? where vip_id = ? ";
                  String cardnum = card.getText();
				  String nameString = name.getText();
				  BaseDaoImpl dao = new BaseDaoImpl();
					  try {
						int result = dao.update(sql, new String[] {nameString,cardnum});
						if (result > 0) {
							JOptionPane.showMessageDialog(null, "修改会员信息成功");							
						} else {
							JOptionPane.showMessageDialog(null, "找不到会员卡号");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  
			}
		});
		btnNewButton_1.setBounds(487, 383, 98, 27);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\u6CE8\u9500");
		btnNewButton_2.addActionListener(new ActionListener() {
			//???
			public void actionPerformed(ActionEvent arg0) {
				BaseDaoImpl dao = new BaseDaoImpl();
				  String  sql ="delete from vip where vip_id=?";
				  String cardnum = card.getText();
				  try {
					int result = dao.delete(sql, new String[] {cardnum});
					if (result > 0) {
						JOptionPane.showMessageDialog(null, "删除会员信息成功");
					} else {
						JOptionPane.showMessageDialog(null, "找不到会员卡号");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
			}
		});
		btnNewButton_2.setBounds(645, 383, 98, 27);
		contentPane.add(btnNewButton_2);
		
	}
}
