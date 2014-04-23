package com.excilys.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.dao.LogDao;

@Repository
public class LogDaoImpl implements LogDao {
	static final Logger LOG = LoggerFactory.getLogger(LogDaoImpl.class);
	@Autowired
	JdbcTemplate jdbcTemp;
	
	
	/**
	 * insert a row in the log database with the id of the computer, the message and the current date.
	 */
	@Override
	public void insertMessageLog(String message, int id) {
		LOG.trace("Start logMessage");

		String req = "insert into log values(null, ?, ?, null)";
		jdbcTemp.update(req, new Object[] { message, id });
	}

}
