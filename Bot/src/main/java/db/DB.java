package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
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
	
	public static void getPacientes() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			
			st = conn.createStatement();
		
			rs = st.executeQuery("select * from sulamerica.pacientes");
			
			while (rs.next()) {
				System.out.println(rs.getString("nome_paciente"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
 
	}
	
	public static void addPaciente() {
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"INSERT INTO pacientes"
					+ "(cod_paciente, nome_paciente, cod_transtorno)"
					+ "VALUES"
					+"(?, ?, ?)");
			
			st.setString(1, "00000000000000000000");
			st.setString(2, "TESTE");
			st.setString(3, "F41");
			
			int rows = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
	
	public static void updatePaciente() {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"UPDATE pacientes "
					+ "SET nome_paciente = ? "
					+ "WHERE "
					+ "(cod_paciente = ?)"
					);
			
			st.setString(1, "TESTE TESTE23");
			st.setString(2, "00000000000000000000");
			
			int rows = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rows);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
	
	public static void deletePaciente() {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"DELETE FROM pacientes "
					+ "WHERE "
					+ "nome_paciente = ?"
					);
			
			st.setString(1, "TESTE TESTE2");
			
			int rows = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rows);
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
