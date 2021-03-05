package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import common.Constants;

public abstract class AbstractDAO {

	protected Connection con;
	protected Statement stm;
	protected ResultSet rs;

	public AbstractDAO() {
		connectDB();
	}

	/**
	 * Conecta con la base de datos
	 */
	private void connectDB() {
		try {
			// Generar objeto config
			Properties properties = new Properties();
			try {
				properties.load(new FileReader(Constants.CONFIG_FILE_PATH));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Class.forName(properties.getProperty("controller"));
			try {
				this.con = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
						properties.getProperty("password"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ejecuta un rollback y vuelve a activar el autocommit
	 */
	protected void conectionRollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Cierra el resultSet, statment y conexión
	 */
	protected void closeConexion() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stm != null) {
				stm.close();
			}
			if (con != null) {
				con.close();
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
