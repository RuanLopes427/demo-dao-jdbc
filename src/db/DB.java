package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null;
	private static Properties props = new Properties();

	static {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			props.load(fs);
			String url = props.getProperty("dburl");
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static Connection getConnection() {
			try {
				if (conn == null || conn.isClosed()){
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
				}
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}

		return conn;
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			finally{
				conn = null;
			}
		}
	}
}
