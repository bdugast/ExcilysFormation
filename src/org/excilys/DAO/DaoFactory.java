package org.excilys.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoFactory {

	private static DaoFactory DF = null;
	private static ComputerDAO computerDao = null;
	private static CompanyDAO companyDao = null;

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
	
	public static ComputerDAO getComputerDao(){
		if(computerDao == null)	computerDao = new ComputerDAO();
		return computerDao;
	}
	
	public static CompanyDAO getCompanyDao(){
		if(companyDao == null)	companyDao = new CompanyDAO();
		return companyDao;
	}
	
	public static void closeAll(Connection conn, Statement stmt) {
		try {
			if(conn != null) conn.close();
			if(stmt != null) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
