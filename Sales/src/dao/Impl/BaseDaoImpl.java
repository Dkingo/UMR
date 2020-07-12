package dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import dao.BaseDao;
import framework.JdbcUtil;

public class BaseDaoImpl implements BaseDao{

	private Connection conn;
	private PreparedStatement pst;
	private ResultSet rs;

	// 构造方法获取数据库连接
	public BaseDaoImpl() {
		conn = new JdbcUtil().getJdbcUtil().getConnection(); 
	}

	// 重写查询数据方法
	@Override
	public List select(String sql, int columnNum, Object[] paraArray) throws SQLException { 
		List list = new ArrayList();
		pst = conn.prepareStatement(sql);
		
		//  paraArray 是一开始就存在于JComboBox的元素，（例如：商品种类，一开始里面就有文具这个值，则需要传进去）
		if (paraArray != null) {
			for (int i = 0, length = paraArray.length; i < length; i++) {
				pst.setObject(i + 1, paraArray[i]);  //  使用给定对象（paraArray[i]）设置指定参数(index,索引值)的值。就是标记
			}
		}
		ResultSet rs = pst.executeQuery();   
		while (rs.next()) {
			Object[] array = new Object[columnNum];
			for (int i = 0; i < columnNum; i++) {
				array[i] = rs.getObject(i + 1);  // 根据上面的标记来获取内容
			}
			list.add(array);
		}
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return list;
	}

	// 重写插入数据方法
	@Override
	public int insert(String sql, Object[] paraArray) throws SQLException {
		pst = conn.prepareStatement(sql);
		for (int i = 0, length = paraArray.length; i < length; i++) {
			pst.setObject(i + 1, paraArray[i]);
		}
		int i = pst.executeUpdate();
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return i;
	}

	// 重写更新数据方法
	@Override
	public int update(String sql, Object[] paraArray) throws SQLException {
		pst = conn.prepareStatement(sql);
		for (int i = 0, length = paraArray.length; i < length; i++) {
			pst.setObject(i + 1, paraArray[i]);
		}
		int i = pst.executeUpdate();
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return i;
	}

	// 重写删除数据方法
	@Override
	public int delete(String sql, Object[] paraArray) throws SQLException {
		pst = conn.prepareStatement(sql);
		for (int i = 0, length = paraArray.length; i < length; i++) {
			pst.setObject(i + 1, paraArray[i]);
		}
		int i = pst.executeUpdate();
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return i;
	}
	
	//根据id查找商品，并返回商品的库存数
	public int selectById(int id_is) throws SQLException {
		int number = 0;
		String sql1  = "select measurement from commodityinformation where id = ?";
		pst = conn.prepareStatement(sql1);
		pst.setInt(1, id_is);
		rs = pst.executeQuery();
		while (rs.next()) {
			number = rs.getInt("measurement");
		}
		new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
		return number;
	}
	
	//根据商品id，名称，生产厂商和商品种类进行查询
	public Vector<Vector> selectByCondition(Object[] paraArray) throws Exception {
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		Vector<Vector> rows = new Vector<Vector>();
		String name = paraArray[2].toString().trim();
		String id = paraArray[3].toString().trim();
		StringBuilder sqlBuilder = new StringBuilder("select * from commodityinformation");
	
		if (!id.isEmpty()) {
			sqlBuilder.append(" where id like '%" + paraArray[3] + "%' ");
			flag1 = true;
		}
		
		if (!name.isEmpty() && flag1) {
			sqlBuilder.append(" and name like '%" + paraArray[2] + "%' ");
		}
		
		if (!name.isEmpty() && !flag1) {
			sqlBuilder.append(" where name like '%" + paraArray[2] + "%' ");
			flag2 = true;
		}
		
		if (!"全部".equals(paraArray[0]) && !flag1 && !flag2) {
			sqlBuilder.append(" where category = '" + paraArray[0] +"'");
			flag3 = true;
		} 
		
		if (!"全部".equals(paraArray[0]) && (flag1 || flag2)) {
			sqlBuilder.append(" and category = '" + paraArray[0] +"'");
		} 
		
		if (!"全部".equals(paraArray[1]) && (flag1 || flag2 || flag3)) {
			sqlBuilder.append(" and manufacturer = '" + paraArray[1] + "'");
		}
		if (!"全部".equals(paraArray[1]) && !flag1 && !flag2 && !flag3) {
			sqlBuilder.append(" where manufacturer = '" + paraArray[1] + "'");
		}
		
		String sql = sqlBuilder.toString();
		List<Object[]> list = select(sql, 8, null);
		if (!list.isEmpty()) {
			for (Object[] object : list) {
				Vector temp = new Vector<String>();
				for (int i = 0; i < object.length; i++) {
					temp.add(object[i]);
				}
				rows.add(temp);
			}
		}
		return rows;
	} 
	
	//根据商品名称进行查询
	public Vector<Vector> selectByName(String pname, String tablename, int column) throws Exception {
		Vector<Vector> rows = new Vector<Vector>();
		String name = pname.trim();
		StringBuilder sqlBuilder = new StringBuilder("select * from " + tablename);
		if (!name.isEmpty()) {
			sqlBuilder.append(" where name like '%" + name + "%'");
		}
		String sql = sqlBuilder.toString();
		List<Object[]> list = select(sql, column, null);
		if (!list.isEmpty()) {
			for (Object[] object : list) {
				Vector temp = new Vector<String>();
				for (int i = 0; i < object.length; i++) {
					temp.add(object[i]);
				}
				rows.add(temp);
			}
		}
		return rows;
	}
	
	//根据商品名称和交易号进行查询
	public Vector<Vector> selectByNaEx(String pname, String pexchange, String tablename, int column) throws Exception {
		Vector<Vector> rows = new Vector<Vector>();
		String name = pname.trim();
		String exchange = pexchange.trim();
		boolean flag = false;
		StringBuilder sqlBuilder = new StringBuilder("select * from " + tablename);
		if (!name.isEmpty()) {
			sqlBuilder.append(" where name like '%" + name + "%'");
			flag = true;
		}
		
		if (!exchange.isEmpty() && flag) {
			sqlBuilder.append(" and exchange like '%" + exchange + "%'");
		}
		
		if (!exchange.isEmpty() && !flag) {
			sqlBuilder.append(" where exchange like '%" + exchange + "%'");
		}
		String sql = sqlBuilder.toString();
		List<Object[]> list = select(sql, column, null);
		if (!list.isEmpty()) {
			for (Object[] object : list) {
				Vector temp = new Vector<String>();
				for (int i = 0; i < object.length; i++) {
					temp.add(object[i]);
				}
				rows.add(temp);
			}
		}
		return rows;
	}
	
	public List selectBuying(String sql, int columnNum, Object[] paraArray) throws SQLException {
		List list = new ArrayList();
		pst = conn.prepareStatement(sql);
		
		//  paraArray 是一开始就存在于JComboBox的元素，（例如：商品种类，一开始里面就有文具这个值，则需要传进去）
		if (paraArray != null) {
			for (int i = 0, length = paraArray.length; i < length; i++) {
				pst.setObject(i + 1, paraArray[i]);  //  使用给定对象（paraArray[i]）设置指定参数(index,索引值)的值。就是标记
			}
		}
		ResultSet rs = pst.executeQuery();   
		while (rs.next()) {
			Object[] array = new Object[columnNum];
			for (int i = 0; i < columnNum; i++) {
				array[i] = rs.getObject(i + 1);  // 根据上面的标记来获取内容
			}
			list.add(array);
		}
		return list;
	}
	
	public int insert( String insertsql , int result) {
		 try {
			 pst = conn.prepareStatement(insertsql);
			 result = pst.executeUpdate();
			 new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
			 return result;
			 }catch(SQLException e) {
				 JOptionPane.showConfirmDialog(null, "链接数据库（插入）异常");
				 e.printStackTrace();
			 }
		 return 0;
	}
	
	public int update(String updateSql) {
		try {
			
			pst = conn.prepareStatement(updateSql);		 
			int ps = pst.executeUpdate();
			 new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
			 return ps;
			 }catch(SQLException e) {
				 JOptionPane.showConfirmDialog(null, "链接(更新)数据库异常");
				 e.printStackTrace();
			 }
		 return 0;
	}
	
	public void refreshCar() {
		try {
		String sql = "TRUNCATE TABLE buying;";
		pst = conn.prepareStatement(sql);
		 pst.executeUpdate();
		 new JdbcUtil().getJdbcUtil().release(rs, pst, conn);
	
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("刷新购物车数据库");
			e.printStackTrace();
		}
	}
	
	public Vector<Vector> selectCash(String pname, String tablename, int column) throws Exception {
		  Vector<Vector> rows = new Vector<Vector>();
		  String name = pname.trim();
		  StringBuilder sqlBuilder = new StringBuilder("select id,name,saleprice from " + tablename);
		  if (!name.isEmpty()) {
		   sqlBuilder.append(" where name like '%" + name + "%'");
		  }
		  String sql = sqlBuilder.toString();
		  List<Object[]> list = select(sql, column, null);
		  if (!list.isEmpty()) {
		   for (Object[] object : list) {
		    Vector temp = new Vector<String>();
		    for (int i = 0; i < object.length; i++) {
		     temp.add(object[i]);
		    }
		    rows.add(temp);
		   }
		  }
		  return rows;
		 }
}
