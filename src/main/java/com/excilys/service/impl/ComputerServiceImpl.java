package com.excilys.service.impl;

import java.util.HashMap;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.impl.DaoFactory;
import com.excilys.domain.Computer;
import com.excilys.exception.CustomException;
import com.excilys.service.ComputerService;
import com.excilys.wrapper.PageWrapper;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;

	static final Logger LOG = LoggerFactory.getLogger(ComputerServiceImpl.class);

	public Computer getOneComputer(int id) {
		Computer comp = null;
		try {
			comp = DaoFactory.INSTANCE.getComputerDao().getOneComputer(id);
		} catch (CustomException e) {
			throw e;
		} finally {
			DaoFactory.INSTANCE.closeConnection();
		}
		return comp;
	}

	public void createComputer(Computer comp) {
		
		DaoFactory.INSTANCE.startTransaction();
		int idComp;
		try {
			idComp = DaoFactory.INSTANCE.getComputerDao().createComputer(comp);
			DaoFactory.INSTANCE.getLogDao().insertMessageLog("CREATE", idComp);
			DaoFactory.INSTANCE.commitTransaction();
		} catch (CustomException e) {
			try{
				DaoFactory.INSTANCE.rollbackTransaction();
				throw e;
			} catch (CustomException e1) {
				throw e1;
			}
		} finally {
			DaoFactory.INSTANCE.closeConnection();
		}
	}

	public void updateComputer(Computer comp) {
		DaoFactory.INSTANCE.startTransaction();
		try {
			DaoFactory.INSTANCE.getComputerDao().updateComputer(comp);
			DaoFactory.INSTANCE.getLogDao().insertMessageLog("UPDATE", comp.getId());
			DaoFactory.INSTANCE.commitTransaction();
		} catch (CustomException e) {
			try {
				DaoFactory.INSTANCE.rollbackTransaction();
				throw e;
			} catch (CustomException e1) {
				throw e1;
			}
		}  finally {
			DaoFactory.INSTANCE.closeConnection();
		}
	}

	public void deleteComputer(int id) {
		DaoFactory.INSTANCE.startTransaction();
		try {
			DaoFactory.INSTANCE.getComputerDao().deleteComputer(id);
			DaoFactory.INSTANCE.getLogDao().insertMessageLog("DELETE", id);
			DaoFactory.INSTANCE.commitTransaction();
		} catch (CustomException e) {
			try {
				DaoFactory.INSTANCE.rollbackTransaction();
				throw e;
			} catch (CustomException e1) {
				throw e1;
			}
		} finally {
			DaoFactory.INSTANCE.closeConnection();
		}
	}

	public int getCountComputerSearch(String search) {
		int count = 0;
		try {
			count = DaoFactory.INSTANCE.getComputerDao().getCountComputerSearch(search);
		} catch (CustomException e) {
			throw e;
		} finally {
			DaoFactory.INSTANCE.closeConnection();
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
			 compList = DaoFactory.INSTANCE.getComputerDao().getRangeSearchOrderComputers(
							((wrap.getCurrentPage() - 1) * wrap.NB_COMPUTER_BY_PAGE),
							wrap.NB_COMPUTER_BY_PAGE, wrap.getSearch(),
							orderby.toString());
		} catch (CustomException e) {
			throw e;
		} finally {
			DaoFactory.INSTANCE.closeConnection();
		}
		return compList;
	}

	public HashMap<String, String> checkForm(String name, String introduced,
			String discontinued, String companyId) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		HashMap<String, String> errorHash = new HashMap<>();

		if (name.length() < 2)
			errorHash.put("nameError",
					"Name lenght must be more than 2 characters");
		LOG.debug("introduced " + introduced.toString());
		if (!introduced.equals("")) {
			try {
				fmt.parseDateTime(introduced);
			} catch (Exception e) {
				errorHash.put("introducedError", "Format incorrect yyyy-mm-dd");
			}
		}
		LOG.debug("discontinued " + discontinued.toString());
		if (!discontinued.equals("")) {
			try {
				fmt.parseDateTime(discontinued);
			} catch (Exception e) {
				errorHash.put("discontinuedError",
						"Format incorrect yyyy-mm-dd");
			}
		}
		LOG.debug("company " + companyId.toString());
		if (!companyId.equals("")) {
			int id = 0;
			try {
				id = Integer.valueOf(companyId);
			} catch (NumberFormatException e) {
				errorHash.put("companyError", "Company does not exist");
			}
			try {
				if (id != 0	&& DaoFactory.INSTANCE.getCompanyDao().getOneCompany(id) == null)
					errorHash.put("companyError", "Company does not exist");
			} catch (CustomException e) {
				throw e;
			}
		}
		return errorHash;
	}

}
