package framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;


public class SQLservice {

	private Connection conn;
	private PreparedStatement pst ;
	private ResultSet rs;
	public SQLservice() {
		conn = new JdbcUtil().getJdbcUtil().getConnection(); 
	}
	
	public Vector<Vector> select(String selectsql, int rows){
		
	
		Vector vector = new Vector();
		Vector vector1 = new Vector<Vector>();
		 try {
		 pst = conn.prepareStatement(selectsql);
		 rs = pst.executeQuery();
		 while(rs.next()) {
			 for( int i = 0 ; i < rows ; i++) {
				vector.add(i,rs.getObject(i+1));
			 }
			vector1.add(vector);
		 }
		 new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		 return vector1;
		 }catch(SQLException e) {
			 JOptionPane.showMessageDialog(null, "链接数据库异常");
		 }
		  return null;
	 }
	
	public int insert( String insertsql , int result) {
		 try {
			 pst = conn.prepareStatement(insertsql);
			 result = pst.executeUpdate();
			 new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
			 return result;
			 }catch(SQLException e) {
				 JOptionPane.showMessageDialog(null, "链接数据库（插入）异常");
				 e.printStackTrace();
			 }
		 return 0;
	}
	
	
	public int update(String updateSql) {
		try {
			
			pst = conn.prepareStatement(updateSql);		 
			int ps = pst.executeUpdate();
			conn.commit();
			new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
			 return ps;
			 }catch(SQLException e) {
				 JOptionPane.showMessageDialog(null, "链接(更新)数据库异常");
				 e.printStackTrace();
			 }
		 return 0;
	}
	
	public Vector<Object[]> selectOne(String name, double price , String facture ,int rows){

		Object[] object = new Object[rows];
		Vector vector = new Vector<Object>();
		 try {
		 String sql_contain = "select * from commodityinformation where name=? and buyingprice=? and manufacturer=?";
		 pst = conn.prepareStatement(sql_contain);
		 pst.setString(1, name);
		 pst.setDouble(2, price);
		 pst.setString(3, facture);
		 rs = pst.executeQuery();
		 while(rs.next()) {
			 for( int i = 0 ; i < rows ; i++) {
				 object[i] = rs.getObject(i+1);
			 }
			vector.add(object);
		 }
		 new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		 return vector;
		 }catch(SQLException e) {
			 JOptionPane.showMessageDialog(null, "链接数据库异常");
		 }
		  return null;
	 }
}
