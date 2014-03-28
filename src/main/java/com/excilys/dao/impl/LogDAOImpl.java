package main.java.com.excilys.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.java.com.excilys.dao.LogDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LogDAOImpl implements LogDAO {
	INSTANCE;
	static final Logger LOG = LoggerFactory.getLogger(LogDAOImpl.class);
	
	@Override
	public void insertMessageLog(String message, int id) {
		LOG.trace("Start createComputer");
		PreparedStatement stmt = null;

		try {
			String req = "insert into log values(null, ?, ?, null)";
			LOG.debug("requete SQL : " + req);
			stmt = DaoFactory.INSTANCE.getConnection().prepareStatement(req);
			stmt.setString(1, message);
			stmt.setInt(2, id);
			LOG.debug("requete stmt : " + stmt);
			stmt.execute();
		} catch (SQLException e) {
			LOG.error(e.toString());
		} finally {
			LOG.trace("Finally getAllComputer ListComputer");
			DaoFactory.INSTANCE.closeDAO(null, stmt);
		}
	}

}
