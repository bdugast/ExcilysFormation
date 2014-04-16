package com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.exception.CustomException;
import com.jolbox.bonecp.BoneCPDataSource;

@Component
public class ConnectionManager {
	final Logger LOG = LoggerFactory.getLogger(ConnectionManager.class);
	
	@Autowired
	BoneCPDataSource boneCP;
	
	ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	protected Connection initialValue(){
		
		return null;
	}
	
	public  Connection getConnection() {
		try {
			if(tl.get()==null){
				tl.set(boneCP.getConnection());
				LOG.debug("ouverture connexion");
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
				LOG.trace("rs close");
			}
			if(stmt != null){
				stmt.close();
				LOG.trace("stmt close");
			}
		} catch (SQLException e) {
			LOG.error(e.toString());
		}
	}
	
	public void closeConnection() {
		try {
			tl.get().close();
			LOG.debug("fermeture success");
		} catch (SQLException e) {
			LOG.error("fermeture error");
			LOG.error(e.toString());
		}
		tl.remove();
	}

	public void startTransaction() {
		Connection conn = getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new CustomException("erreur init transaction", e.getMessage());
		}
	}
	
	public void commitTransaction() {
		try {
			getConnection().commit();
		} catch (SQLException e) {
			throw new CustomException("erreur commit transaction", e.getMessage());
		}
	}
	
	public void rollbackTransaction() {
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			throw new CustomException("erreur rollback transaction", e.getMessage());
		}
	}
}
