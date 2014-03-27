package main.java.com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DaoFactory {
	INSTANCE;
	final Logger LOG = LoggerFactory.getLogger(DaoFactory.class);
	
	public ComputerDAOImpl getComputerDao(){
		return ComputerDAOImpl.INSTANCE;
	}
	
	public CompanyDAOImpl getCompanyDao(){
		return CompanyDAOImpl.INSTANCE;
	}
	
	public  Connection getConnection() {
		Context ctx;
		DataSource ds;
		Connection conn = null;
			try {
				ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/compdb");
				conn = ds.getConnection();
			} catch (SQLException | NamingException e) {
				LOG.error(e.toString());
			}
		
		return conn;
	}
	
	public void closeAll(Connection conn, ResultSet rs, Statement stmt) {
		try {
			if(conn != null) conn.close();
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		} catch (SQLException e) {
			LOG.error(e.toString());
		}
	}
}
