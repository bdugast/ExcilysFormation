package com.excilys.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.dao.LogDao;
import com.excilys.domain.Log;

@Repository
public class LogDaoImpl implements LogDao {
	static final Logger LOG = LoggerFactory.getLogger(LogDaoImpl.class);	

	@Autowired
	SessionFactory sf;
	/**
	 * insert a row in the log database with the id of the computer, the message and the current date.
	 */
	@Override
	public void insertMessageLog(Log log) {
		Session session = sf.getCurrentSession();
		session.save(log);
	}

}
