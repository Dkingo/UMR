package framework;

/**
*
* ˵��:JDBC������ �������ݿ������Լ����ݿ���Դ�ͷ�
* 
* @author LS
* 
*/

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class JdbcUtil {

	private static JdbcUtil jdbcUtil;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static String driver = null;
	private static Properties props = new Properties();

	static {

		try {
			// ��ȡ���ݿ������ļ�
			props.load(JdbcUtil.class.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			System.out.println("����jdbc.properties�����ļ��쳣");
			e.printStackTrace();
		}

		url = (props.getProperty("jdbc.url"));
		username = (props.getProperty("jdbc.username"));
		password = (props.getProperty("jdbc.password"));
		driver = (props.getProperty("jdbc.driver"));

		// �������ݿ�����
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("�������ݿ������쳣");
			e.printStackTrace();
		}

	}

	// ����ģʽ
	public JdbcUtil getJdbcUtil() {

		if (jdbcUtil == null) {
			synchronized (JdbcUtil.class) {
				if (jdbcUtil == null) {
					jdbcUtil = new JdbcUtil();
				}
			}
		}
		return jdbcUtil;
	}

	//����һ�����ݿ�����
	public Connection getConnection() {
		Connection conn = null;
		// �������ݿ�����
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("�������ݿ����ӷ����쳣");
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * �ͷ����ݿ���Դ
	 */
	public void release(Object o) {
		if (o == null) {
			return;
		}
		if (o instanceof ResultSet) {
			try {
				((ResultSet) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (o instanceof PreparedStatement) {
			try {
				((PreparedStatement) o).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (o instanceof Connection) {
			Connection c = (Connection) o;
			try {
				if (!c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	//�ͷ����ݿ���Դ��������
	public void release(ResultSet rs, PreparedStatement pst, Connection conn) {
		release(rs);
		release(pst);
		release(conn);
	}

}
