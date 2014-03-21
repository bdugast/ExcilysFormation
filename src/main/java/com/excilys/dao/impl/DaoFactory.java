package main.java.com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoFactory {

	private static DaoFactory DF = null;
	private static ComputerDAOImpl computerDao = null;
	private static CompanyDAOImpl companyDao = null;

	private DaoFactory() {
	}

	/** Point d'accès pour l'instance unique du singleton */
	public static DaoFactory getInstance() {
		if (DF == null) {
			DF = new DaoFactory();
		}
		return DF;
	}

	public static Connection getConnection() {
		Context ctx;
		DataSource ds;
		Connection conn = null;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/compdb");
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static ComputerDAOImpl getComputerDao(){
		if(computerDao == null)	computerDao = new ComputerDAOImpl();
		return computerDao;
	}
	
	public static CompanyDAOImpl getCompanyDao(){
		if(companyDao == null)	companyDao = new CompanyDAOImpl();
		return companyDao;
	}
	
	public static void closeAll(Connection conn, ResultSet rs, Statement stmt) {
		try {
			if(conn != null) conn.close();
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
