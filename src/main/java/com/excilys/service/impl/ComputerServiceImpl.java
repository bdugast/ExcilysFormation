package com.excilys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.dao.CompanyDao;
import com.excilys.dao.ComputerDao;
import com.excilys.dao.impl.LogDaoImpl;
import com.excilys.domain.Computer;
import com.excilys.exception.CustomException;
import com.excilys.service.ComputerService;
import com.excilys.wrapper.PageWrapper;

@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {
	static final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

	@Autowired
	public ComputerDao computerDao;
	@Autowired
	public CompanyDao companyDao;
	@Autowired
	public LogDaoImpl logDao;

	public Computer getOneComputer(int id) {
		Computer comp = null;
		try {
			comp = computerDao.getOneComputer(id);
		} catch (CustomException e) {
			throw e;
		}
		return comp;
	}

	@Transactional(readOnly = false)
	public void createComputer(Computer comp) {
		try {
			int idComp;
			idComp = computerDao.createComputer(comp);
			logDao.insertMessageLog("CREATE", idComp);
		} catch (CustomException e) {
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public void updateComputer(Computer comp) {
		try {
			LOG.debug("computer = " + comp);
			computerDao.updateComputer(comp);
			logDao.insertMessageLog("UPDATE", comp.getId());
		} catch (CustomException e) {
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public void deleteComputer(int id) {
		try {
			computerDao.deleteComputer(id);
			logDao.insertMessageLog("DELETE", id);
		} catch (CustomException e) {
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public int getCountComputerSearch(String search) {
		int count = 0;
		try {
			count = computerDao.getCountComputerSearch(search);
		} catch (CustomException e) {
			throw e;
		}
		return count;
	}

	@Transactional(readOnly = false)
	public List<Computer> getRangeSearchOrderComputers(PageWrapper wrap) {

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
		orderby.append(" ");
		if (wrap.getOrder())
			orderby.append("ASC");
		else
			orderby.append("DESC");

		try {
			compList = computerDao.getRangeSearchOrderComputers(
					((wrap.getCurrentPage() - 1) * wrap.NB_COMPUTER_BY_PAGE),
					wrap.NB_COMPUTER_BY_PAGE, wrap.getSearch(),
					orderby.toString());
		} catch (CustomException e) {
			throw e;
		}
		return compList;
	}
}
