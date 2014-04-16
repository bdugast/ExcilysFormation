package com.excilys.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.dao.LogDao;
import com.excilys.exception.CustomException;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class LogDaoImpl implements LogDao {
	static final Logger LOG = LoggerFactory.getLogger(LogDaoImpl.class);
	
	@Autowired
	BoneCPDataSource boneCP;
	
	@Override
	public void insertMessageLog(String message, int id) {
		LOG.trace("Start createComputer");
		PreparedStatement stmt = null;

		try {
			String req = "insert into log values(null, ?, ?, null)";
			LOG.debug("requete SQL : " + req);
			stmt =  DataSourceUtils.getConnection(boneCP).prepareStatement(req);
			stmt.setString(1, message);
			stmt.setInt(2, id);
			LOG.debug("requete stmt : " + stmt);
			stmt.execute();
		} catch (SQLException e) {
			throw new CustomException("erreur SQL", e.getMessage());
		} finally {
			LOG.trace("Finally getAllComputer ListComputer");
			ConnectionManager.closeDAO(null, stmt);
		}
	}

}
