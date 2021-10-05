package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/equipo_de_programacion";
	
	private static Connection instance = null;
	
	public DBConnection () {
		
	}
	
	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "facultad123");
			instance = DriverManager.getConnection(JDBC_URL, props);
		}
		return instance;
	}

}
