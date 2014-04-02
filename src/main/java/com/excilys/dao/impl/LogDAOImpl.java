package com.excilys.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.dao.LogDAO;
import com.excilys.exception.CustomException;

@Repository
public class LogDAOImpl implements LogDAO {
	static final Logger LOG = LoggerFactory.getLogger(LogDAOImpl.class);
	
	@Autowired
	private ConnectionManager connectionManager;
	
	@Override
	public void insertMessageLog(String message, int id) {
		LOG.trace("Start createComputer");
		PreparedStatement stmt = null;

		try {
			String req = "insert into log values(null, ?, ?, null)";
			LOG.debug("requete SQL : " + req);
			stmt = connectionManager.getConnection().prepareStatement(req);
			stmt.setString(1, message);
			stmt.setInt(2, id);
			LOG.debug("requete stmt : " + stmt);
			stmt.execute();
		} catch (SQLException e) {
			throw new CustomException("erreur SQL", e.getMessage());
		} finally {
			LOG.trace("Finally getAllComputer ListComputer");
			connectionManager.closeDAO(null, stmt);
		}
	}

}
