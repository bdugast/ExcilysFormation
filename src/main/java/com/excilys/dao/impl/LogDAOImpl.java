package main.java.com.excilys.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.dao.LogDAO;

public enum LogDAOImpl implements LogDAO {
	INSTANCE;
	static final Logger LOG = LoggerFactory.getLogger(LogDAOImpl.class);
	
	@Override
	public void insertMessageLog(String message, int id, Connection conn) {
		LOG.trace("Start createComputer");
		PreparedStatement stmt = null;

		try {
			String req = "insert into log values(null, ?, ?, null)";
			LOG.debug("requete SQL : " + req);
			stmt = conn.prepareStatement(req);
			stmt.setString(1, message);
			stmt.setInt(2, id);
			LOG.debug("requete stmt : " + stmt);
			stmt.execute();
		} catch (SQLException e) {
			LOG.error(e.toString());
		} finally {
			LOG.trace("Finally getAllComputer ListComputer");
			DaoFactory.INSTANCE.closeAll(null, null, stmt);
		}
	}

}
