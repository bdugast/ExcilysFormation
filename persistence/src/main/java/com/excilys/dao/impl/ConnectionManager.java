package com.excilys.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConnectionManager {
	
	/**
	 * Method to close result set and statement in all request.
	 * @param rs
	 * 		rs of the dao
	 * @param stmt
	 * 		stmt of the dao
	 */
	public static void closeDAO(ResultSet rs, Statement stmt) {
		Logger LOG = LoggerFactory.getLogger(ConnectionManager.class);
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
}
