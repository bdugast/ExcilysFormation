package com.excilys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ComputerDao;
import com.excilys.dao.impl.LogDaoImpl;
import com.excilys.domain.Computer;
import com.excilys.domain.Log;
import com.excilys.exception.CustomException;
import com.excilys.service.ComputerService;
import com.excilys.wrapper.PageWrapper;

@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {
	static final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

	@Autowired
	private ComputerDao computerDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private LogDaoImpl logDao;
	@Autowired
	private LocalSessionFactoryBean sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public Computer getOneComputer(int id) {
		Computer comp = null;
		try {
			comp = computerDao.getOneComputer(id);
		} catch (CustomException e) {
			throw e;
		}
		return comp;
	}

	@Override
	@Transactional(readOnly = false)
	public void createComputer(Computer comp) {
		try {
			computerDao.createComputer(comp);
			Log log = new Log("CREATE", comp.getId());
			logDao.insertMessageLog(log);
		} catch (CustomException e) {
			throw e;
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateComputer(Computer comp) {
		try {
			LOG.debug("computer = " + comp);
			computerDao.updateComputer(comp);
			Log log = new Log("UPDATE", comp.getId());
			logDao.insertMessageLog(log);
		} catch (CustomException e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteComputer(Computer comp) {
		try {
			computerDao.deleteComputer(comp);
			Log log = new Log("DELETE", comp.getId());
			logDao.insertMessageLog(log);
		} catch (CustomException e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public int getCountComputers(String search) {
		int count = 0;
		try {
			count = computerDao.getCountComputers(search);
		} catch (CustomException e) {
			throw e;
		}
		return count;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Computer> getRangeComputers(PageWrapper wrap) {

		List<Computer> compList = null;

		StringBuilder orderby = new StringBuilder();
		String orderb;
		switch (wrap.getOrderField()) {
		case "COMPUTER":
			orderb = "cu.name";
			break;
		case "COMPANY":
			orderb = "ca.name";
			break;
		case "INTRODUCED":
			orderb = "cu.introduced";
			break;
		case "DISCONTINUED":
			orderb = "cu.discontinued";
			break;
		default:
			orderb = "cu.id";
			break;
		}
		orderby.append(orderb);
		if (wrap.getOrder())
			orderby.append(" ASC");
		else
			orderby.append(" DESC");

		try {
			compList = computerDao.getRangeComputers(wrap, orderby.toString());
		} catch (CustomException e) {
			throw e;
		}
		return compList;
	}
}
