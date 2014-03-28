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

import com.jolbox.bonecp.BoneCPDataSource;

public enum DaoFactory {
	INSTANCE;
	final Logger LOG = LoggerFactory.getLogger(DaoFactory.class);
	BoneCPDataSource boneCP = new BoneCPDataSource();
	ThreadLocal<Connection> tl = new ThreadLocal<>();	
	{
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/compdb");
			boneCP.setDatasourceBean(ds);
		} catch (NamingException e) {
			LOG.error(e.toString());
		}
	}

	public ComputerDAOImpl getComputerDao(){
		return ComputerDAOImpl.INSTANCE;
	}
	
	public CompanyDAOImpl getCompanyDao(){
		return CompanyDAOImpl.INSTANCE;
	}
	
	public LogDAOImpl getLogDao(){
		return LogDAOImpl.INSTANCE;
	}
	
	public  Connection getConnection() {
		LOG.debug("ouverture");
		try {
			if(tl.get()==null){
				tl.set(boneCP.getConnection());
				LOG.debug("ouverture set");
			}
		} catch (SQLException e) {
			LOG.error("ouverture fail");
			LOG.error(e.toString());
		}		
		return tl.get();
	}
	
	public void closeDAO(ResultSet rs, Statement stmt) {
		try {
			if(rs != null){
				rs.close();
				LOG.debug("rs close");
			}
			if(stmt != null){
				stmt.close();
				LOG.debug("stmt close");
			}
		} catch (SQLException e) {
			LOG.error(e.toString());
		}
	}
	
	public void closeConnection() {
		LOG.debug("fermeture");
		try {
			tl.get().close();
			LOG.debug("fermeture success");
		} catch (SQLException e) {
			LOG.error("fermeture error");
			LOG.error(e.toString());
		}
		tl.remove();
	}
}
