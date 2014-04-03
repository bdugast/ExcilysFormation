package com.excilys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dao.impl.CompanyDaoImpl;
import com.excilys.dao.impl.ComputerDaoImpl;
import com.excilys.dao.impl.ConnectionManager;
import com.excilys.dao.impl.LogDaoImpl;
import com.excilys.domain.Computer;
import com.excilys.exception.CustomException;
import com.excilys.service.ComputerService;
import com.excilys.wrapper.PageWrapper;

@Service
public class ComputerServiceImpl implements ComputerService {
	static final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);
	
	@Autowired
	public ComputerDaoImpl computerDao;
	@Autowired
	public CompanyDaoImpl companyDao;
	@Autowired
	public LogDaoImpl logDao;
	@Autowired
	private ConnectionManager connectionManager;
	
	public Computer getOneComputer(int id) {
		Computer comp = null;
		try {
			comp = computerDao.getOneComputer(id);
		} catch (CustomException e) {
			throw e;
		} finally {
			connectionManager.closeConnection();
		}
		return comp;
	}

	public void createComputer(Computer comp) {
		
		connectionManager.startTransaction();
		int idComp;
		try {
			idComp = computerDao.createComputer(comp);
			logDao.insertMessageLog("CREATE", idComp);
			connectionManager.commitTransaction();
		} catch (CustomException e) {
			try{
				connectionManager.rollbackTransaction();
				throw e;
			} catch (CustomException e1) {
				throw e1;
			}
		} finally {
			connectionManager.closeConnection();
		}
	}

	public void updateComputer(Computer comp) {
		connectionManager.startTransaction();
		try {
			computerDao.updateComputer(comp);
			logDao.insertMessageLog("UPDATE", comp.getId());
			connectionManager.commitTransaction();
		} catch (CustomException e) {
			try {
				connectionManager.rollbackTransaction();
				throw e;
			} catch (CustomException e1) {
				throw e1;
			}
		}  finally {
			connectionManager.closeConnection();
		}
	}

	public void deleteComputer(int id) {
		connectionManager.startTransaction();
		try {
			computerDao.deleteComputer(id);
			logDao.insertMessageLog("DELETE", id);
			connectionManager.commitTransaction();
		} catch (CustomException e) {
			try {
				connectionManager.rollbackTransaction();
				throw e;
			} catch (CustomException e1) {
				throw e1;
			}
		} finally {
			connectionManager.closeConnection();
		}
	}

	public int getCountComputerSearch(String search) {
		int count = 0;
		try {
			count = computerDao.getCountComputerSearch(search);
		} catch (CustomException e) {
			throw e;
		} finally {
			connectionManager.closeConnection();
		}
		return count;
	}

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
		} finally {
			connectionManager.closeConnection();
		}
		return compList;
	}
}
